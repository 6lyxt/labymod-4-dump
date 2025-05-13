// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.util.ide.IdeUtil;
import java.util.Locale;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.SubscribeMethodRegisterEvent;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.EventInterruptException;
import net.labymod.api.event.Event;
import java.util.HashMap;
import net.labymod.api.event.method.SubscribeMethodList;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.event.method.ListenerRegistry;

public class DefaultListenerRegistry implements ListenerRegistry
{
    private static final DefaultAddonService ADDON_SERVICE;
    private static final Logging LOGGER;
    private final Map<Class<?>, SubscribeMethodList> listeners;
    
    public DefaultListenerRegistry() {
        this.listeners = new HashMap<Class<?>, SubscribeMethodList>();
    }
    
    @Override
    public Map<Class<?>, SubscribeMethodList> getListeners() {
        return this.listeners;
    }
    
    @Override
    public boolean hasListeners(final Class<?> eventClass) {
        final SubscribeMethodList methods = this.listeners.get(eventClass);
        return methods != null && !methods.isEmpty();
    }
    
    @Override
    public boolean invokeListeners(final ClassLoader classLoader, final Class<?> eventClass, final Event event) {
        if (this.listeners.isEmpty()) {
            return true;
        }
        final SubscribeMethodList list = this.listeners.get(eventClass);
        if (list == null || list.isEmpty()) {
            return true;
        }
        for (final SubscribeMethod method : list.getSubscribeMethods()) {
            if (classLoader == null || method.isInClassLoader(classLoader)) {
                final LabyEvent labyEvent = method.getLabyEvent();
                if (classLoader != null || labyEvent == null || !labyEvent.classLoaderExclusive()) {
                    try {
                        this.invokeMethod(method, eventClass, event);
                    }
                    catch (final EventInterruptException ignored) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public void invokeMethod(final SubscribeMethod method, final Class<?> eventClass, final Event event) {
        this.invokeMethod(method.getLabyEvent(), method, eventClass, event);
    }
    
    @Override
    public void register(final SubscribeMethod method) {
        this.registerInternal(method);
        Laby.fireEvent(new SubscribeMethodRegisterEvent(this, method));
    }
    
    public void registerInternal(final SubscribeMethod method) {
        final SubscribeMethodList methods = this.getMethods(method.getEventType());
        methods.add(method);
        methods.sort();
    }
    
    @Override
    public void unregister(final SubscribeMethod method) {
        final SubscribeMethodList methods = this.listeners.get(method.getEventType());
        if (methods == null) {
            return;
        }
        methods.remove(method);
    }
    
    @Override
    public void merge(final ListenerRegistry registry) {
        synchronized (this.listeners) {
            for (final Map.Entry<Class<?>, SubscribeMethodList> entry : registry.getListeners().entrySet()) {
                final Class<?> eventClass = entry.getKey();
                final SubscribeMethodList methods = this.getMethods(eventClass);
                methods.mergeSort(entry.getValue());
                for (final SubscribeMethod method : methods.getSubscribeMethods()) {
                    Laby.fireEvent(new SubscribeMethodRegisterEvent(this, method));
                }
            }
        }
    }
    
    private SubscribeMethodList getMethods(final Class<?> eventClass) {
        return this.listeners.computeIfAbsent(eventClass, list -> new DefaultSubscribeMethodList());
    }
    
    private void invokeMethod(final LabyEvent labyEvent, final SubscribeMethod method, final Class<?> eventClass, final Event event) {
        final InstalledAddonInfo addon = method.getAddon();
        if (addon != null && (labyEvent == null || !labyEvent.background()) && !DefaultListenerRegistry.ADDON_SERVICE.isEnabled(addon, true)) {
            return;
        }
        if (method.getEventType().equals(eventClass)) {
            try {
                method.invoke(event);
            }
            catch (final EventInterruptException exception) {
                throw exception;
            }
            catch (final Throwable throwable) {
                if (labyEvent != null) {
                    if (labyEvent.allowAllExceptions()) {
                        throw new RuntimeException(throwable);
                    }
                    for (final Class<? extends RuntimeException> allowedException : labyEvent.allowExceptions()) {
                        if (allowedException.isInstance(throwable)) {
                            throw (RuntimeException)throwable;
                        }
                    }
                }
                final LabyModLoader loader = Laby.labyAPI().labyModLoader();
                final String listenerName = (method.getListener() != null) ? method.getListener().getClass().getName() : "<no listener>";
                final String eventName = event.getClass().getName();
                if (loader.isLabyModDevelopmentEnvironment()) {
                    if (!loader.isAddonDevelopmentEnvironment() && addon == null) {
                        IdeUtil.doPauseOrCrashGame(String.format(Locale.ROOT, "Failed to handle LabyMod listener %s for event %s", listenerName, eventName), throwable);
                        return;
                    }
                    if (loader.isAddonDevelopmentEnvironment() && addon != null) {
                        final LoadedAddon loadedAddon = Laby.labyAPI().addonService().getAddon(addon.getNamespace()).orElse(null);
                        if (loadedAddon != null && loadedAddon.isClasspath()) {
                            IdeUtil.doPauseOrCrashGame(String.format(Locale.ROOT, "Failed to handle listener %s for event %s from addon %s", listenerName, eventName, addon.getDisplayName()), throwable);
                            return;
                        }
                    }
                }
                if (addon != null) {
                    DefaultListenerRegistry.LOGGER.error("Failed to handle listener {} for event {} from addon {}", method.getListener().getClass().getName(), event.getClass().getName(), addon.getDisplayName(), throwable);
                }
                else {
                    DefaultListenerRegistry.LOGGER.error("Failed to handle LabyMod listener {} for event {}", method.getListener().getClass().getName(), event.getClass().getName(), throwable);
                }
            }
        }
    }
    
    static {
        ADDON_SERVICE = DefaultAddonService.getInstance();
        LOGGER = DefaultLoggingFactory.createLogger(DefaultListenerRegistry.class);
    }
}
