// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface HotkeysConfig extends ConfigAccessor
{
    ConfigProperty<Key> emoteWheelKey();
    
    ConfigProperty<Key> interactionMenuKey();
    
    ConfigProperty<Key> sprayWheelKey();
    
    ConfigProperty<Key> toggleHitBox();
    
    ConfigProperty<Key> widgetEditorKey();
    
    ConfigProperty<Key> friendsKey();
    
    ConfigProperty<Key> markerKey();
    
    ConfigProperty<Boolean> modernKeybinding();
    
    ConfigProperty<Boolean> rawMouseInput();
    
    ConfigProperty<Boolean> disableNarratorHotkey();
}
