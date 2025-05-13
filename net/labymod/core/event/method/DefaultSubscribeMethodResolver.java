// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.core.event.method.invoker.ReflectSubscribeMethodInvoker;
import java.util.List;
import net.labymod.api.util.ide.IdeUtil;
import java.lang.annotation.Annotation;
import net.labymod.api.event.Subscribe;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.event.method.SubscribeMethodList;
import java.lang.reflect.Method;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.Event;
import java.util.function.Consumer;
import net.labymod.api.event.exception.ListenerResolveException;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.event.LabyEvent;
import net.labymod.core.event.method.invoker.SubscribeMethodInvokerFactory;
import net.labymod.core.addon.AddonClassLoader;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.event.method.SubscribeMethodResolver;

@Singleton
@Implements(SubscribeMethodResolver.class)
public class DefaultSubscribeMethodResolver implements SubscribeMethodResolver
{
    private static final int REPEAT_COUNT = 100;
    private static final Logging LOGGER;
    private final Map<AddonClassLoader, SubscribeMethodInvokerFactory> addonInvokerFactories;
    private final Map<Class<?>, LabyEvent> labyEventCache;
    private final SubscribeMethodInvokerFactory invokerFactory;
    private final LabyAPI labyAPI;
    private final Map<Class<?>, ListenerRegistry> cachedListeners;
    
    @Inject
    public DefaultSubscribeMethodResolver(final LabyAPI labyAPI) {
        this.cachedListeners = new HashMap<Class<?>, ListenerRegistry>();
        this.labyAPI = labyAPI;
        this.addonInvokerFactories = new HashMap<AddonClassLoader, SubscribeMethodInvokerFactory>();
        this.labyEventCache = new HashMap<Class<?>, LabyEvent>();
        this.invokerFactory = new SubscribeMethodInvokerFactory();
    }
    
    @Override
    public ListenerRegistry resolve(@NotNull final Object listener) {
        final Class<?> listenerClass = listener.getClass();
        final ListenerRegistry cachedRegistry = this.cachedListeners.get(listenerClass);
        if (cachedRegistry != null) {
            return this.copyRegistry(cachedRegistry, listener, false);
        }
        final ClassLoader classLoader = listenerClass.getClassLoader();
        ListenerRegistry registry;
        try {
            registry = this.resolve((classLoader instanceof AddonClassLoader) ? ((AddonClassLoader)classLoader) : null, listener);
        }
        catch (final Throwable throwable) {
            throw new ListenerResolveException("Listener " + listenerClass.getName() + " from ClassLoader " + classLoader.getClass().getName() + " could not be resolved", throwable);
        }
        this.cachedListeners.put(listenerClass, this.copyRegistry(registry, null, true));
        return registry;
    }
    
    @Override
    public SubscribeMethod createCustom(final byte priority, final Class<?> eventType, @NotNull final Consumer<Event> listener) {
        final LoadedAddon addon = this.labyAPI.addonService().getLastCallerAddon();
        return this.createCustom((addon != null) ? addon.getClassLoader() : null, priority, eventType, listener);
    }
    
    @Override
    public SubscribeMethod createCustom(final ClassLoader classLoader, final byte priority, final Class<?> eventType, @NotNull final Consumer<Event> listener) {
        return new DefaultSubscribeMethod((classLoader instanceof AddonClassLoader) ? ((AddonClassLoader)classLoader) : null, null, priority, null, eventType, (l, event) -> listener.accept(event), null);
    }
    
    private ListenerRegistry copyRegistry(final ListenerRegistry registry, final Object newListener, final boolean internal) {
        final DefaultListenerRegistry copy = new DefaultListenerRegistry();
        for (final SubscribeMethodList list : registry.getListeners().values()) {
            for (final SubscribeMethod method : list.getSubscribeMethods()) {
                final SubscribeMethod methodCopy = method.copy(newListener);
                if (internal) {
                    copy.registerInternal(methodCopy);
                }
                else {
                    copy.register(methodCopy);
                }
            }
        }
        return copy;
    }
    
    private ListenerRegistry resolve(@Nullable final AddonClassLoader classLoader, @NotNull final Object listener) {
        final ListenerRegistry registry = new DefaultListenerRegistry();
        final Class<?> listenerClass = listener.getClass();
        final List<Method> invalidMethods = new ArrayList<Method>();
        for (final Method method : listenerClass.getMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                final int parameterCount = method.getParameterCount();
                if (parameterCount != 1) {
                    invalidMethods.add(method);
                }
                else {
                    final Class<?> eventClass = method.getParameterTypes()[0];
                    if (Event.class.isAssignableFrom(eventClass)) {
                        SubscribeMethodInvoker methodInvoker;
                        if (classLoader == null) {
                            methodInvoker = this.getMethodInvoker(this.invokerFactory, method, eventClass);
                        }
                        else {
                            final SubscribeMethodInvokerFactory invokerFactory = this.addonInvokerFactories.computeIfAbsent(classLoader, factory -> new SubscribeMethodInvokerFactory(classLoader));
                            methodInvoker = this.getMethodInvoker(invokerFactory, method, eventClass);
                        }
                        final Subscribe subscribe = method.getAnnotation(Subscribe.class);
                        registry.getListeners().computeIfAbsent(eventClass, list -> new DefaultSubscribeMethodList()).add(new DefaultSubscribeMethod(classLoader, listener, subscribe.value(), method, eventClass, methodInvoker, this.getLabyEvent(eventClass)));
                    }
                }
            }
        }
        if (!invalidMethods.isEmpty() && IdeUtil.RUNNING_IN_IDE) {
            throw new IllegalStateException(this.buildInvalidMethodErrorMessage(listenerClass, invalidMethods));
        }
        if (registry.getListeners().isEmpty()) {
            DefaultSubscribeMethodResolver.LOGGER.debug("No listeners for events were found in class \"{}\"!", listenerClass.getSimpleName());
        }
        return registry;
    }
    
    private SubscribeMethodInvoker getMethodInvoker(final SubscribeMethodInvokerFactory invokerFactory, final Method method, final Class<?> eventClass) {
        SubscribeMethodInvoker methodInvoker = invokerFactory.create(method, eventClass);
        if (methodInvoker == null) {
            methodInvoker = new ReflectSubscribeMethodInvoker(method);
        }
        return methodInvoker;
    }
    
    private LabyEvent getLabyEvent(final Class<?> eventClass) {
        final LabyEvent event = this.labyEventCache.get(eventClass);
        if (event != null) {
            return event;
        }
        final LabyEvent annotation = eventClass.getAnnotation(LabyEvent.class);
        this.labyEventCache.put(eventClass, annotation);
        return annotation;
    }
    
    private String buildInvalidMethodErrorMessage(final Class<?> listenerClass, final List<Method> invalidMethods) {
        final String listenerClassName = listenerClass.getName();
        final StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("\n").append("#".repeat(100));
        bobTheBuilder.append("\nSome methods had no or more than one parameter: ");
        for (final Method invalidMethod : invalidMethods) {
            final int parameterCount = invalidMethod.getParameterCount();
            bobTheBuilder.append("\n\t - ").append(listenerClassName).append(".").append(invalidMethod.getName()).append("(");
            final String[] names = new String[parameterCount];
            for (int index = 0; index < parameterCount; ++index) {
                final Class<?> parameterType = invalidMethod.getParameterTypes()[index];
                names[index] = parameterType.getName();
            }
            bobTheBuilder.append(String.join(", ", (CharSequence[])names));
            bobTheBuilder.append(")");
        }
        bobTheBuilder.append("\n").append("#".repeat(100));
        return bobTheBuilder.toString();
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(DefaultSubscribeMethodResolver.class);
    }
}
