// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.hotkey;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Objects;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.settings.creator.MemberInspector;

public class SwitchSettingHotkeyFactory implements HotkeyFactory
{
    @Override
    public boolean hasSettingAnnotation(final MemberInspector inspector) {
        return inspector.isAnnotationPresent(SwitchWidget.SwitchSetting.class);
    }
    
    @Nullable
    @Override
    public Hotkey create(final MemberInspector inspector, final String displayName, final SettingEntry entry) {
        final SettingElement element = entry.setting();
        final SettingAccessor accessor = element.getAccessor();
        if (accessor == null) {
            return null;
        }
        final ConfigProperty<?> property = accessor.property();
        if (property.getType() != Boolean.class) {
            return null;
        }
        final SwitchWidget.SwitchSetting annotation = inspector.getAnnotation(SwitchWidget.SwitchSetting.class);
        if (!annotation.hotkey()) {
            return null;
        }
        final String translationKey = element.getTranslationKey() + ".name";
        final KeyAccessor accessor2 = new KeyAccessor(() -> {
            final String hotkey = accessor.config().configMeta().get(element.getId() + ".hotkey");
            return (hotkey == null) ? Key.NONE : KeyMapper.getKey(hotkey);
        }, () -> Key.NONE, newKey -> accessor.config().configMeta().put(element.getId() + ".hotkey", newKey.getActualName()));
        final SettingElement obj = element;
        Objects.requireNonNull(obj);
        return new Hotkey(displayName, translationKey, accessor2, obj::isVisible);
    }
}
