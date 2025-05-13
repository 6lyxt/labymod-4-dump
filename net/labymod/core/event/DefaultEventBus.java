// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event;

import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.api.event.method.SubscribeMethod;
import java.util.Iterator;
import net.labymod.api.event.method.SubscribeMethodList;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.Laby;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.Event;
import javax.inject.Inject;
import net.labymod.core.event.method.DefaultListenerRegistry;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.event.method.SubscribeMethodResolver;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.event.EventBus;

@Singleton
@Implements(EventBus.class)
public class DefaultEventBus implements EventBus
{
    private final SubscribeMethodResolver methodResolver;
    private final ListenerRegistry listenerRegistry;
    
    @Inject
    public DefaultEventBus(final SubscribeMethodResolver methodResolver) {
        this.methodResolver = methodResolver;
        this.listenerRegistry = new DefaultListenerRegistry();
    }
    
    @Override
    public <T extends Event> void fire(final T event) {
        this.fire(null, event);
    }
    
    @Override
    public <T extends Event> void fire(final ClassLoader classLoader, final T event) {
        if (event == null) {
            throw new NullPointerException("The event which should be fired is null!");
        }
        final Class<?> eventClass = event.getClass();
        if (!this.listenerRegistry.invokeListeners(classLoader, eventClass, event)) {
            return;
        }
        this.invokeActivityListeners(classLoader, eventClass, event);
    }
    
    private <T extends Event> void invokeActivityListeners(final ClassLoader classLoader, final Class<?> eventClass, final T event) {
        if (ThreadSafe.isRenderThread()) {
            this._invokeActivityListeners(classLoader, eventClass, (Event)event);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this._invokeActivityListeners(classLoader, eventClass, event));
        }
    }
    
    private <T extends Event> void _invokeActivityListeners(final ClassLoader classLoader, final Class<?> eventClass, final T event) {
        for (final Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            if (openActivity.listenForEvents()) {
                if (!openActivity.listenerRegistry().invokeListeners(classLoader, eventClass, event)) {
                    return;
                }
            }
        }
    }
    
    @Override
    public void registerListener(final Object listener) {
        if (listener == null) {
            throw new IllegalStateException("The listener cannot be registered because it is null");
        }
        this.listenerRegistry.merge(this.methodResolver.resolve(listener));
    }
    
    @Override
    public boolean isListenerRegistered(final Object listener) {
        for (final SubscribeMethodList entry : this.listenerRegistry.getListeners().values()) {
            for (final SubscribeMethod method : entry.getSubscribeMethods()) {
                if (method.getListener() == listener) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean hasListeners(final Class<? extends Event> eventClass) {
        if (this.listenerRegistry.hasListeners(eventClass)) {
            return true;
        }
        for (final Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            if (openActivity.listenForEvents() && openActivity.listenerRegistry().hasListeners(eventClass)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void unregisterListener(final Object listener) {
        final AddonClassLoader classLoader = this.getClassLoader(listener);
        for (final SubscribeMethodList list : this.listenerRegistry.getListeners().values()) {
            list.removeIf(subscribeMethod -> {
                if (listener != subscribeMethod.getListener()) {
                    return false;
                }
                else if (classLoader == null || subscribeMethod.getClassLoader() == null) {
                    return classLoader == subscribeMethod.getClassLoader();
                }
                else {
                    final InstalledAddonInfo methodAddonInfo = ((AddonClassLoader)subscribeMethod.getClassLoader()).getAddonInfo();
                    final InstalledAddonInfo addonInfo = classLoader.getAddonInfo();
                    return addonInfo.getNamespace().equals(methodAddonInfo.getNamespace()) && listener == subscribeMethod.getListener();
                }
            });
        }
    }
    
    @Override
    public void unregisterListeners(final Object addon) {
        final AddonClassLoader addonClassLoader = this.getClassLoader(addon);
        if (addonClassLoader == null) {
            throw new IllegalStateException(String.format(Locale.ROOT, "The class \"%s\" was not loaded by any AddonClassLoader and therefore the listener cannot be registered.", addon.getClass().getName()));
        }
        for (final SubscribeMethodList list : this.listenerRegistry.getListeners().values()) {
            list.removeIf(subscribeMethod -> {
                if (!(subscribeMethod.getClassLoader() instanceof AddonClassLoader)) {
                    return false;
                }
                else {
                    final InstalledAddonInfo methodAddonInfo = ((AddonClassLoader)subscribeMethod.getClassLoader()).getAddonInfo();
                    final InstalledAddonInfo addonInfo = addonClassLoader.getAddonInfo();
                    return methodAddonInfo.getNamespace().equals(addonInfo.getNamespace());
                }
            });
        }
    }
    
    @Nullable
    private AddonClassLoader getClassLoader(@Nullable final Object addon) {
        if (addon == null) {
            throw new IllegalStateException("No AddonClassLoader was found because the Addon is null");
        }
        final ClassLoader classLoader = addon.getClass().getClassLoader();
        if (!(classLoader instanceof AddonClassLoader)) {
            return null;
        }
        return (AddonClassLoader)classLoader;
    }
    
    @Override
    public ListenerRegistry registry() {
        return this.listenerRegistry;
    }
}
