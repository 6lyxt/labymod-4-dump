// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.integration;

public interface AddonIntegration
{
    void load();
    
    default void onIntegratedAddonEnable() {
    }
    
    default void onIntegratedAddonDisable() {
    }
}
