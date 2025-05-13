// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.method;

import net.labymod.api.event.LabyEvent;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Method;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.Event;

public interface SubscribeMethod
{
    void invoke(final Event p0) throws Throwable;
    
    @Nullable
    ClassLoader getClassLoader();
    
    @Nullable
    InstalledAddonInfo getAddon();
    
    @Nullable
    Object getListener();
    
    byte getPriority();
    
    @Nullable
    Method getMethod();
    
    @NotNull
    Class<?> getEventType();
    
    @Nullable
    LabyEvent getLabyEvent();
    
    boolean isInClassLoader(final ClassLoader p0);
    
    SubscribeMethod copy(final Object p0);
}
