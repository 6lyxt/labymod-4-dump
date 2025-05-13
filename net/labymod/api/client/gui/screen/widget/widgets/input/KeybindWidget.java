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
import java.util.Objects;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
@SettingWidget
public class KeybindWidget extends TextFieldWidget
{
    private final Selectable<Key> selectable;
    private Supplier<Key> keyUpdater;
    private Key key;
    private boolean listening;
    private boolean acceptMouseButtons;
    private String lastVisibleText;
    
    public KeybindWidget(final Selectable<Key> selectable) {
        this.key = null;
        this.listening = false;
        this.acceptMouseButtons = true;
        this.selectable = selectable;
        this.placeholder = Component.translatable("labymod.ui.keybind.pressKey", new Component[0]);
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (this.keyUpdater != null) {
            this.updateKey(this.keyUpdater.get());
        }
        super.initialize(parent);
    }
    
    @Override
    public void tick() {
        if (this.listening && !this.isFocused()) {
            this.listening = false;
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton button) {
        if (this.isListening() && this.acceptMouseButtons && this.isHovered()) {
            this.updateKey(button);
            return true;
        }
        if (button == MouseButton.LEFT) {
            this.listening = this.isHovered();
        }
        return super.mouseClicked(mouse, button);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (this.isListening() && key == Key.ESCAPE) {
            return this.updateKey(Key.NONE);
        }
        return this.isListening() && !key.isUnknown() && this.updateKey(key);
    }
    
    private boolean updateKey(final Key key) {
        this.key = key;
        this.selectable.select(this.key);
        this.setFocused(false);
        this.listening = false;
        return true;
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return this.isListening();
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return true;
    }
    
    @Override
    public boolean isHovered() {
        return super.isHovered();
    }
    
    @Override
    public boolean isCursorVisible() {
        return false;
    }
    
    public KeybindWidget acceptMouseButtons(final boolean acceptMouseButtons) {
        this.acceptMouseButtons = acceptMouseButtons;
        return this;
    }
    
    public KeybindWidget key(final Key key) {
        this.key = key;
        this.selectable.select(this.key);
        return this;
    }
    
    public Key key() {
        return this.key;
    }
    
    @Override
    public String getVisibleText() {
        final String visibleText = super.getVisibleText();
        if (this.lastVisibleText == null) {
            this.lastVisibleText = this.getFormattedText();
        }
        if (!this.lastVisibleText.equals(visibleText)) {
            final String formattedText = this.getFormattedText();
            if (visibleText.equals(formattedText)) {
                this.setHoverComponent(null);
            }
            else {
                this.setHoverComponent(Component.text(formattedText));
            }
        }
        return visibleText;
    }
    
    @Override
    protected String getFormattedText() {
        if (this.key == null || this.key == Key.NONE) {
            return I18n.translate("labymod.ui.keybind.none", new Object[0]);
        }
        if (this.key instanceof MouseButton) {
            return I18n.translate("labymod.ui.keybind.mouse", this.key.getName());
        }
        return I18n.translate("labymod.ui.keybind.keyboard", this.key.getName());
    }
    
    @Override
    public boolean shouldDisplayPlaceHolder() {
        return this.isListening();
    }
    
    public boolean isListening() {
        return this.listening;
    }
    
    public void setKeyUpdater(final Supplier<Key> keyUpdater) {
        this.keyUpdater = keyUpdater;
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<KeyBindSetting, KeybindWidget>
    {
        @Override
        public KeybindWidget[] create(final Setting setting, final KeyBindSetting annotation, final SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            final KeybindWidget widget = new KeybindWidget(accessor::set);
            widget.acceptMouseButtons(annotation.acceptMouseButtons());
            widget.key(accessor.get());
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            accessor.property().addChangeListener((t, oldValue, newValue) -> widget.updateKey((newValue instanceof Key) ? ((Key)newValue) : Key.NONE));
            return new KeybindWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { Key.class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface KeyBindSetting {
        boolean acceptMouseButtons() default false;
    }
}
