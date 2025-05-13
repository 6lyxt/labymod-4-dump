// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Arrays;
import java.util.Objects;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.component.Component;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
@SettingWidget
public class MultiKeybindWidget extends TextFieldWidget
{
    private final Selectable<Key[]> selectable;
    private final Set<Key> keys;
    private boolean listening;
    
    public MultiKeybindWidget(final Selectable<Key[]> selectable) {
        this.keys = new HashSet<Key>();
        this.listening = false;
        this.selectable = selectable;
        this.placeholder = Component.translatable("labymod.ui.multiKeybind.pressKeys", new Component[0]);
    }
    
    @Override
    public void tick() {
        if (this.listening != this.isFocused()) {
            this.listening = this.isFocused();
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered() && mouseButton == MouseButton.LEFT) {
            this.setFocused(true);
            this.keys.clear();
        }
        super.mouseClicked(mouse, mouseButton);
        return true;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (!this.listening) {
            return false;
        }
        if (key == Key.ESCAPE) {
            this.setFocused(false);
            this.listening = false;
            this.keys.clear();
            this.selectable.select(new Key[0]);
            return true;
        }
        CollectionHelper.removeIf(this.keys, k -> {
            if (k == Key.L_SHIFT || k == Key.R_SHIFT || k == Key.L_CONTROL || k == Key.R_CONTROL) {
                return false;
            }
            else {
                return key.isAction() == k.isAction() && key.getId() == k.getId();
            }
        });
        this.keys.add(key);
        this.selectable.select(this.keys.toArray(new Key[0]));
        return true;
    }
    
    @Override
    public boolean isHovered() {
        return super.isHovered() || this.listening;
    }
    
    public Set<Key> getKeys() {
        return this.keys;
    }
    
    public void setKeys(final Set<Key> keys) {
        this.keys.clear();
        this.keys.addAll(keys);
        this.selectable.select(this.keys.toArray(new Key[0]));
    }
    
    @Override
    public boolean shouldDisplayPlaceHolder() {
        return this.listening && this.keys.isEmpty();
    }
    
    public boolean isListening() {
        return this.listening;
    }
    
    @Override
    public boolean isCursorVisible() {
        return false;
    }
    
    @Override
    public String getVisibleText() {
        if (this.keys.isEmpty()) {
            return I18n.translate("labymod.ui.keybind.none", new Object[0]);
        }
        return Key.concat(this.keys);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return this.listening;
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<MultiKeyBindSetting, MultiKeybindWidget>
    {
        @Override
        public MultiKeybindWidget[] create(final Setting setting, final MultiKeyBindSetting annotation, final SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            final MultiKeybindWidget widget = new MultiKeybindWidget(accessor::set);
            final Key[] keys = accessor.get();
            widget.setKeys((keys != null) ? new HashSet<Key>(Arrays.asList(keys)) : Collections.emptySet());
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                Set<Key> emptySet = null;
                if (newValue instanceof Key[]) {
                    new(java.util.HashSet.class)();
                    new HashSet(Arrays.asList((Key[])newValue));
                }
                else {
                    emptySet = Collections.emptySet();
                }
                final Set<Key> newKeys = emptySet;
                widget.keys.clear();
                widget.keys.addAll(newKeys);
                return;
            });
            return new MultiKeybindWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { Key[].class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MultiKeyBindSetting {
    }
}
