// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.settings;

import net.labymod.api.client.gui.screen.key.Key;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.Laby;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.event.labymod.config.SettingCreateEvent;
import java.util.HashSet;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
public class HotkeySwitchSettingHandler
{
    private final Set<SettingElement> properties;
    
    public HotkeySwitchSettingHandler() {
        this.properties = new HashSet<SettingElement>();
    }
    
    @Subscribe
    public void registerHotkeySetting(final SettingCreateEvent event) {
        if (event.setting().isElement()) {
            final SettingElement element = event.setting().asElement();
            if (element == null || !(element.getAnnotation() instanceof SwitchWidget.SwitchSetting) || element.getAccessor() == null) {
                return;
            }
            final ConfigProperty<?> property = element.getAccessor().property();
            if (property.getType() == Boolean.class && ((SwitchWidget.SwitchSetting)element.getAnnotation()).hotkey()) {
                this.properties.add(element);
            }
        }
    }
    
    @Subscribe
    public void toggleHotkeySettings(final KeyEvent event) {
        if (Laby.labyAPI().minecraft().minecraftWindow().getCurrentVersionedScreen() != null) {
            return;
        }
        if (event.state() != KeyEvent.State.PRESS) {
            return;
        }
        for (SettingElement setting : this.properties) {
            final String hotkey = setting.getAccessor().config().configMeta().get(setting.getId() + ".hotkey");
            if (hotkey == null) {
                continue;
            }
            final Key key = KeyMapper.getKey(hotkey);
            if (key != event.key()) {
                continue;
            }
            final Boolean value = setting.getAccessor().get();
            setting.getAccessor().set(value == null || !value);
        }
    }
}
