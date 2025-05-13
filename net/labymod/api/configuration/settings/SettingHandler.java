// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.client.gui.screen.activity.Activity;

public interface SettingHandler
{
    void created(final Setting p0);
    
    void initialized(final Setting p0);
    
    default void reset(final Setting setting) {
    }
    
    default boolean isEnabled(final Setting setting) {
        return true;
    }
    
    default boolean opensActivity(final Setting setting) {
        return false;
    }
    
    default Activity activity(final Setting setting) {
        return null;
    }
}
