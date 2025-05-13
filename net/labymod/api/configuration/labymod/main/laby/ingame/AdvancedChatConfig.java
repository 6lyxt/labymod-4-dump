// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface AdvancedChatConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Boolean> draggable();
    
    ConfigProperty<Boolean> resizeable();
    
    ConfigProperty<Boolean> showMenuButton();
    
    ConfigProperty<Boolean> legacyScrollbar();
    
    ConfigProperty<Boolean> fadeInMessages();
    
    ConfigProperty<Boolean> disableCrosshair();
    
    ConfigProperty<Boolean> showPlayerHeads();
    
    ConfigProperty<Boolean> showChatOnHiddenGui();
    
    ConfigProperty<Key> chatPeekKey();
    
    ConfigProperty<Boolean> legacyMessageOffset();
    
    ConfigProperty<Integer> globalChatLimit();
    
    ConfigProperty<Boolean> globalCombineChatMessages();
    
    ConfigProperty<Boolean> globalAntiChatClear();
    
    ConfigProperty<Boolean> globalChatTrust();
    
    ConfigProperty<Boolean> globalShadow();
    
    ConfigProperty<Boolean> globalBackground();
    
    @Deprecated(forRemoval = true, since = "4.2.8")
    default ConfigProperty<Boolean> shadow() {
        return this.globalShadow();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.8")
    default ConfigProperty<Boolean> background() {
        return this.globalBackground();
    }
}
