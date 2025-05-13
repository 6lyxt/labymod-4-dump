// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import java.lang.reflect.Method;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.api.event.method.SubscribeMethod;

public class DefaultSubscribeMethod implements SubscribeMethod
{
    @Nullable
    private final AddonClassLoader classLoader;
    @Nullable
    private final Object listener;
    @Nullable
    private final ClassLoader listenerClassLoader;
    private final byte priority;
    @Nullable
    private final Method method;
    private final Class<?> eventType;
    private final SubscribeMethodInvoker methodInvoker;
    @Nullable
    private final LabyEvent labyEvent;
    
    public DefaultSubscribeMethod(@Nullable final AddonClassLoader classLoader, @Nullable final Object listener, final byte priority, @Nullable final Method method, final Class<?> eventType, final SubscribeMethodInvoker methodInvoker, @Nullable final LabyEvent labyEvent) {
        this.classLoader = classLoader;
        this.listener = listener;
        this.priority = priority;
        this.method = method;
        this.eventType = eventType;
        this.methodInvoker = methodInvoker;
        this.labyEvent = labyEvent;
        this.listenerClassLoader = ((this.listener == null) ? null : this.listener.getClass().getClassLoader());
    }
    
    @Override
    public void invoke(final Event event) throws Throwable {
        this.methodInvoker.invoke(this.listener, event);
    }
    
    @Nullable
    @Override
    public AddonClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    @Nullable
    @Override
    public InstalledAddonInfo getAddon() {
        return (this.classLoader != null) ? this.classLoader.getAddonInfo() : null;
    }
    
    @Nullable
    @Override
    public Object getListener() {
        return this.listener;
    }
    
    @Override
    public byte getPriority() {
        return this.priority;
    }
    
    @Nullable
    @Override
    public Method getMethod() {
        return this.method;
    }
    
    @NotNull
    @Override
    public Class<?> getEventType() {
        return this.eventType;
    }
    
    @Nullable
    @Override
    public LabyEvent getLabyEvent() {
        return this.labyEvent;
    }
    
    @Override
    public boolean isInClassLoader(final ClassLoader other) {
        return this.listener != null && Objects.equals(this.listenerClassLoader, other);
    }
    
    @Override
    public SubscribeMethod copy(final Object newListener) {
        return new DefaultSubscribeMethod(this.classLoader, newListener, this.priority, this.method, this.eventType, this.methodInvoker, this.labyEvent);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final DefaultSubscribeMethod that = (DefaultSubscribeMethod)obj;
        return Objects.equals(this.listener, that.listener) && this.priority == that.priority && Objects.equals(this.method, that.method) && Objects.equals(this.eventType, that.eventType);
    }
}
