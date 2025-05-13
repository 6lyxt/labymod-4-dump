// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

public interface ToggleInputMapping extends MinecraftInputMapping
{
    boolean needsToggle();
    
    void forcePress();
    
    void forceUnpress();
    
    boolean toggle();
}
