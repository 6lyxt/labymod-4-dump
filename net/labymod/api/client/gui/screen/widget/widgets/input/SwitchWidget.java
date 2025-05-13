// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.switchable.BooleanSwitchableHandler;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.property.Property;
import java.lang.annotation.Annotation;
import net.labymod.api.util.PrimitiveHelper;
import java.util.Objects;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@SettingWidget
public class SwitchWidget extends SimpleWidget
{
    private static final String DEFAULT_ENABLED = "labymod.ui.switch.enabled";
    private static final String DEFAULT_DISABLED = "labymod.ui.switch.disabled";
    private final Switchable switchable;
    private boolean value;
    private String enabledText;
    private String disabledText;
    private String enabledTranslatableKey;
    private String disabledTranslatableKey;
    private final LssProperty<Integer> textHoverColor;
    
    protected SwitchWidget(final Switchable switchable) {
        this.enabledText = "";
        this.disabledText = "";
        this.enabledTranslatableKey = null;
        this.disabledTranslatableKey = null;
        this.textHoverColor = new LssProperty<Integer>(NamedTextColor.YELLOW.getValue());
        this.switchable = switchable;
    }
    
    public static SwitchWidget create(final Switchable switchable) {
        return translatable("labymod.ui.switch.enabled", "labymod.ui.switch.disabled", switchable);
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Switch";
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public void setValue(final boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean onPress() {
        this.value = !this.value;
        if (this.switchable != null) {
            this.switchable.switchValue(this.value);
        }
        Laby.references().soundService().play(this.value ? SoundType.SWITCH_TOGGLE_ON : SoundType.SWITCH_TOGGLE_OFF);
        return true;
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return true;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered() && mouseButton == MouseButton.LEFT) {
            this.onPress();
            this.callActionListeners();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return true;
    }
    
    public String getText() {
        return this.value ? this.enabledText : this.disabledText;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return 50.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return 20.0f;
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.textHoverColor.get() != null && (this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered());
    }
    
    public static SwitchWidget translatable(final String enabledTranslatableKey, final String disabledTranslatableKey, final Switchable switchable) {
        final SwitchWidget widget = new SwitchWidget(switchable);
        widget.enabledTranslatableKey = enabledTranslatableKey;
        widget.disabledTranslatableKey = disabledTranslatableKey;
        return widget;
    }
    
    public static SwitchWidget text(final String enabledText, final String disabledText, final Switchable switchable) {
        final SwitchWidget widget = new SwitchWidget(switchable);
        widget.enabledText = enabledText;
        widget.disabledText = disabledText;
        return widget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.enabledTranslatableKey != null) {
            final String enabledTranslation = I18n.getTranslation(this.enabledTranslatableKey, new Object[0]);
            if (enabledTranslation == null) {
                this.enabledText = I18n.translate("labymod.ui.switch.enabled", new Object[0]);
            }
            else {
                this.enabledText = enabledTranslation;
            }
        }
        if (this.disabledTranslatableKey != null) {
            final String disabledTranslation = I18n.getTranslation(this.disabledTranslatableKey, new Object[0]);
            if (disabledTranslation == null) {
                this.disabledText = I18n.translate("labymod.ui.switch.disabled", new Object[0]);
            }
            else {
                this.disabledText = disabledTranslation;
            }
        }
    }
    
    public LssProperty<Integer> textHoverColor() {
        return this.textHoverColor;
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<SwitchSetting, Widget>
    {
        @Override
        public Widget[] create(final Setting setting, final SwitchSetting annotation, final SettingAccessor accessor) {
            if (annotation.hotkey()) {
                final String metaKey = setting.getId() + ".hotkey";
                final SwitchWidget switchWidget = this.createSwitch(setting, accessor);
                Key key = null;
                final KeybindWidget keybindWidget = new KeybindWidget(key -> accessor.config().configMeta().put(metaKey, key.getActualName()));
                if (accessor.config().hasConfigMeta(metaKey)) {
                    keybindWidget.setKeyUpdater(() -> KeyMapper.getKey(accessor.config().configMeta().get(metaKey)));
                    key = KeyMapper.getKey(accessor.config().configMeta().get(metaKey));
                    if (key != null) {
                        keybindWidget.key(key);
                    }
                }
                return new Widget[] { switchWidget, keybindWidget };
            }
            final SwitchWidget switchWidget2 = this.createSwitch(setting, accessor);
            return new SwitchWidget[] { switchWidget2 };
        }
        
        private SwitchWidget createSwitch(final Setting setting, final SettingAccessor accessor) {
            Objects.requireNonNull(accessor);
            final SwitchWidget widget = new SwitchWidget(accessor::set);
            widget.enabledTranslatableKey = setting.getTranslationKey() + ".enabled";
            widget.disabledTranslatableKey = setting.getTranslationKey() + ".disabled";
            widget.setValue(accessor.get());
            accessor.property().addChangeListener((type, oldValue, newValue) -> widget.setValue(newValue instanceof Boolean && (boolean)newValue));
            return widget;
        }
        
        @Override
        public Class<?>[] types() {
            return PrimitiveHelper.BOOLEAN;
        }
    }
    
    @SettingElement(switchable = BooleanSwitchableHandler.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SwitchSetting {
        boolean hotkey() default false;
    }
}
