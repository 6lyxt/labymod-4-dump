// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultHotkeysConfig extends Config implements HotkeysConfig
{
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @SpriteSlot(page = 1)
    private final ConfigProperty<Key> emoteWheelKey;
    @IntroducedIn("4.2.0")
    @SpriteSlot(x = 0, y = 6, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> sprayWheelKey;
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @SpriteSlot(x = 1, page = 1)
    private final ConfigProperty<Key> interactionMenuKey;
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @SpriteSlot(x = 4, y = 2, page = 1)
    private final ConfigProperty<Key> widgetEditorKey;
    @KeybindWidget.KeyBindSetting
    @SpriteSlot(x = 3, y = 1, page = 1)
    @IntroducedIn("4.1.4")
    private final ConfigProperty<Key> toggleHitBox;
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @SpriteSlot(x = 2, y = 5, page = 1)
    private final ConfigProperty<Key> friendsKey;
    @SpriteSlot(x = 5, y = 2, page = 1)
    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    @IntroducedIn("4.1.0")
    private final ConfigProperty<Key> markerKey;
    @SettingSection("advanced")
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.8.9<1.12.2")
    @SpriteSlot(x = 6, y = 4, page = 1)
    private final ConfigProperty<Boolean> rawMouseInput;
    @VersionCompatibility("1.8.9")
    @SearchTag({ "focus", "movement", "modern", "keyboard", "keybinding" })
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 5)
    @IntroducedIn("4.1.11")
    private final ConfigProperty<Boolean> modernKeybinding;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.12<*")
    @SpriteSlot(x = 5, y = 4, page = 1)
    private final ConfigProperty<Boolean> disableNarratorHotkey;
    
    public DefaultHotkeysConfig() {
        this.emoteWheelKey = new ConfigProperty<Key>(MouseButton.X);
        this.sprayWheelKey = new ConfigProperty<Key>(Key.G);
        this.interactionMenuKey = new ConfigProperty<Key>(MouseButton.MIDDLE);
        this.widgetEditorKey = new ConfigProperty<Key>(Key.R_SHIFT);
        this.toggleHitBox = new ConfigProperty<Key>(Key.NONE);
        this.friendsKey = new ConfigProperty<Key>(Key.NONE);
        this.markerKey = new ConfigProperty<Key>(Key.NONE);
        this.rawMouseInput = new ConfigProperty<Boolean>(true);
        this.modernKeybinding = new ConfigProperty<Boolean>(true);
        this.disableNarratorHotkey = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Key> emoteWheelKey() {
        return this.emoteWheelKey;
    }
    
    @Override
    public ConfigProperty<Key> interactionMenuKey() {
        return this.interactionMenuKey;
    }
    
    @Override
    public ConfigProperty<Key> sprayWheelKey() {
        return this.sprayWheelKey;
    }
    
    @Override
    public ConfigProperty<Key> toggleHitBox() {
        return this.toggleHitBox;
    }
    
    @Override
    public ConfigProperty<Key> widgetEditorKey() {
        return this.widgetEditorKey;
    }
    
    @Override
    public ConfigProperty<Key> friendsKey() {
        return this.friendsKey;
    }
    
    @Override
    public ConfigProperty<Key> markerKey() {
        return this.markerKey;
    }
    
    @Override
    public ConfigProperty<Boolean> modernKeybinding() {
        return this.modernKeybinding;
    }
    
    @Override
    public ConfigProperty<Boolean> rawMouseInput() {
        return this.rawMouseInput;
    }
    
    @Override
    public ConfigProperty<Boolean> disableNarratorHotkey() {
        return this.disableNarratorHotkey;
    }
}
