// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.overlay;

import net.labymod.api.client.gui.screen.activity.overlay.initializer.InstanceScreenInitializer;

public class RegisteredReplacement
{
    private final Class<?> clazz;
    private final InstanceScreenInitializer initializer;
    private boolean enabled;
    
    public RegisteredReplacement(final Class<?> clazz, final InstanceScreenInitializer initializer) {
        this.clazz = clazz;
        this.initializer = initializer;
        this.enabled = true;
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
    
    public InstanceScreenInitializer getInitializer() {
        return this.initializer;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
