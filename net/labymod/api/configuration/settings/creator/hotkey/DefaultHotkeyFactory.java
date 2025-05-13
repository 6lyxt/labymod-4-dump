// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.hotkey;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import java.util.Objects;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.configuration.settings.creator.MemberInspector;

public class DefaultHotkeyFactory implements HotkeyFactory
{
    @Override
    public boolean hasSettingAnnotation(final MemberInspector inspector) {
        return inspector.isAnnotationPresent(KeybindWidget.KeyBindSetting.class);
    }
    
    @Nullable
    @Override
    public Hotkey create(final MemberInspector inspector, final String displayName, final SettingEntry entry) {
        final SettingElement element = entry.setting();
        final SettingAccessor accessor = element.getAccessor();
        if (accessor == null) {
            return null;
        }
        final ConfigProperty<Key> property = accessor.property();
        final String translationKey = element.getTranslationKey() + ".name";
        final ConfigProperty<Key> obj = property;
        Objects.requireNonNull(obj);
        final Supplier<Object> keyGetter = (Supplier<Object>)obj::get;
        final ConfigProperty<Key> obj2 = property;
        Objects.requireNonNull(obj2);
        final Supplier<Object> defaultKeyGetter = (Supplier<Object>)obj2::defaultValue;
        final ConfigProperty<Key> obj3 = property;
        Objects.requireNonNull(obj3);
        final KeyAccessor accessor2 = new KeyAccessor((Supplier<Key>)keyGetter, (Supplier<Key>)defaultKeyGetter, obj3::set);
        final SettingElement obj4 = element;
        Objects.requireNonNull(obj4);
        return new Hotkey(displayName, translationKey, accessor2, obj4::isVisible);
    }
}
