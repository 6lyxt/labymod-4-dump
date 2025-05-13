// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Textures;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
@SettingWidget
public class ButtonWidget extends HorizontalListWidget
{
    public static final boolean SUPPORTS_SCROLLING;
    private final LssProperty<Boolean> scaleToFit;
    private final LssProperty<Integer> disabledColor;
    protected Component component;
    protected ComponentWidget componentWidget;
    private final LssProperty<Icon> icon;
    private final LssProperty<Component> text;
    private final LssProperty<HorizontalAlignment> alignment;
    private Icon tempIcon;
    
    public ButtonWidget() {
        this.scaleToFit = new LssProperty<Boolean>(true);
        this.disabledColor = new LssProperty<Integer>(0);
        this.icon = new LssProperty<Icon>(null);
        this.text = new LssProperty<Component>(null);
        this.alignment = new LssProperty<HorizontalAlignment>(HorizontalAlignment.CENTER);
        this.setEnabled(true);
        ((AbstractWidget<Widget>)this).addId("button", "primary-button");
    }
    
    protected ButtonWidget(final Component component, final Icon icon) {
        this.scaleToFit = new LssProperty<Boolean>(true);
        this.disabledColor = new LssProperty<Integer>(0);
        this.icon = new LssProperty<Icon>(null);
        this.text = new LssProperty<Component>(null);
        this.alignment = new LssProperty<HorizontalAlignment>(HorizontalAlignment.CENTER);
        this.component = component;
        this.icon.set(icon);
        this.setEnabled(true);
        ((AbstractWidget<Widget>)this).addId("button", "primary-button");
    }
    
    protected ButtonWidget(final Icon icon) {
        this.scaleToFit = new LssProperty<Boolean>(true);
        this.disabledColor = new LssProperty<Integer>(0);
        this.icon = new LssProperty<Icon>(null);
        this.text = new LssProperty<Component>(null);
        this.alignment = new LssProperty<HorizontalAlignment>(HorizontalAlignment.CENTER);
        this.icon.set(icon);
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Button";
    }
    
    public static ButtonWidget icon(final Icon icon, final Pressable pressable) {
        return component(null, icon, pressable);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.isAttributeStateEnabled(AttributeState.ENABLED) && super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean onPress() {
        this.callActionListeners();
        return super.onPress();
    }
    
    public void bindToBranch(final String branchName) {
        final boolean isBranch = Laby.labyAPI().getBranchName().equals(branchName);
        if (!isBranch) {
            this.setHoverComponent(Component.translatable("labymod.misc.wip", new Component[0]));
        }
        this.setEnabled(isBranch);
    }
    
    @Override
    public boolean hasTabFocus() {
        return true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.tempIcon != null) {
            this.icon.set(this.tempIcon);
            this.tempIcon = null;
        }
        if (this.icon.get() != null) {
            final HorizontalListEntry icon = this.addEntry(new IconWidget(this.icon.get()));
            icon.alignment().set(HorizontalAlignment.CENTER);
            icon.addId("button-icon");
        }
        if (this.component != null) {
            if (this.findFirstChildIf(widget -> widget.hasId("button-text")) != null) {
                return;
            }
            final ComponentWidget component = ComponentWidget.component(this.component);
            this.componentWidget = component;
            final HorizontalListEntry text = this.addEntry(component);
            text.alignment().set(HorizontalAlignment.CENTER);
            text.addId("button-text");
            text.setActive(this.isAttributeStateEnabled(AttributeState.ENABLED));
            this.setStencil(ButtonWidget.SUPPORTS_SCROLLING);
        }
    }
    
    @Override
    protected boolean shouldFocusOnClick() {
        return false;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (ButtonWidget.SUPPORTS_SCROLLING) {
            this.updateScrollingString();
        }
        super.renderWidget(context);
    }
    
    private void updateScrollingString() {
        if (this.componentWidget == null) {
            return;
        }
        final RenderableComponent renderable = this.componentWidget.renderable();
        if (renderable == null) {
            return;
        }
        final float fontSize = this.componentWidget.fontSize().get().getFontSize();
        final float componentWidth = renderable.getWidth() * fontSize;
        final float buttonWidth = this.bounds().getWidth(BoundsType.MIDDLE) - 4.0f;
        final float offset = getTextScrollingOffset(componentWidth, buttonWidth);
        this.componentWidget.translateX().set(offset);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (!this.text.isDefaultValue()) {
            this.updateComponent(this.text.get());
        }
        if (this.componentWidget != null && this.componentWidget.renderable().isClipped() && !this.hasHoverComponent()) {
            this.setHoverComponent(this.component);
        }
    }
    
    public static ButtonWidget advancedSettings() {
        return icon(Textures.SpriteCommon.SETTINGS);
    }
    
    public static ButtonWidget deleteButton() {
        return icon(Textures.SpriteCommon.TRASH);
    }
    
    public static ButtonWidget icon(final Icon icon) {
        return component(null, icon, null);
    }
    
    public ButtonWidget updateComponent(final Component component) {
        if (this.component != null && this.component.equals(component)) {
            return this;
        }
        if ((this.component = component) == null) {
            return this;
        }
        if (this.componentWidget == null) {
            if (this.initialized) {
                final ComponentWidget component2 = ComponentWidget.component(component);
                this.componentWidget = component2;
                final HorizontalListEntry text = this.addEntryInitialized(component2);
                text.alignment().set(HorizontalAlignment.CENTER);
                text.addId("button-text");
                text.setActive(this.isAttributeStateEnabled(AttributeState.ENABLED));
                this.setStencil(ButtonWidget.SUPPORTS_SCROLLING);
            }
        }
        else {
            this.componentWidget.setComponent(component);
            this.updateContentBounds();
        }
        return this;
    }
    
    public void setEnabled(final boolean enabled) {
        this.setAttributeState(AttributeState.ENABLED, enabled);
    }
    
    public void updateIcon(final Icon icon) {
        this.tempIcon = icon;
        this.reInitialize();
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    @Override
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }
    
    public LssProperty<Boolean> scaleToFit() {
        return this.scaleToFit;
    }
    
    public LssProperty<Integer> disabledColor() {
        return this.disabledColor;
    }
    
    public LssProperty<Icon> icon() {
        return this.icon;
    }
    
    public LssProperty<Component> text() {
        return this.text;
    }
    
    public LssProperty<HorizontalAlignment> alignment() {
        return this.alignment;
    }
    
    public static ButtonWidget i18n(final String key) {
        return i18n(key, null, null);
    }
    
    public static ButtonWidget i18n(final String key, final Pressable pressable) {
        return i18n(key, null, pressable);
    }
    
    public static ButtonWidget i18n(final String key, final Icon icon) {
        return i18n(key, icon, null);
    }
    
    public static ButtonWidget i18n(final String key, final Icon icon, final Pressable pressable) {
        return component(Component.translatable(key, new Component[0]), icon, pressable);
    }
    
    public static ButtonWidget i18nMinecraft(final String key) {
        return i18nMinecraft(key, null, null);
    }
    
    public static ButtonWidget i18nMinecraft(final String key, final Pressable pressable) {
        return i18nMinecraft(key, null, pressable);
    }
    
    public static ButtonWidget i18nMinecraft(final String key, final Icon icon) {
        return i18nMinecraft(key, icon, null);
    }
    
    public static ButtonWidget i18nMinecraft(final String key, final Icon icon, final Pressable pressable) {
        final String translation = Laby.labyAPI().minecraft().getTranslation(key);
        return component(Component.text(translation), icon, pressable);
    }
    
    public static ButtonWidget text(final String text) {
        return text(text, null, null);
    }
    
    public static ButtonWidget text(final String text, final Pressable pressable) {
        return text(text, null, pressable);
    }
    
    public static ButtonWidget text(final String text, final Icon icon) {
        return text(text, icon, null);
    }
    
    public static ButtonWidget text(final String text, final Icon icon, final Pressable pressable) {
        return component(Component.text(text), icon, pressable);
    }
    
    public static ButtonWidget component(final Component component) {
        return component(component, null, null);
    }
    
    public static ButtonWidget component(final Component component, final Pressable pressable) {
        return component(component, null, pressable);
    }
    
    public static ButtonWidget component(final Component component, final Icon icon) {
        return new ButtonWidget(component, icon);
    }
    
    public static ButtonWidget component(final Component component, final Icon icon, final Pressable pressable) {
        final ButtonWidget button = new ButtonWidget(component, icon);
        button.setPressable(pressable);
        return button;
    }
    
    public static float getTextScrollingOffset(final float componentWidth, final float buttonWidth) {
        if (componentWidth <= buttonWidth || !ButtonWidget.SUPPORTS_SCROLLING) {
            return 0.0f;
        }
        final int overflow = (int)componentWidth - (int)buttonWidth;
        final float time = TimeUtil.getMillis() / 1000.0f;
        final float speed = Math.max(overflow * 0.5f, 3.0f);
        final float rot = MathHelper.cos(6.283185307179586 * time / speed);
        final float sin = MathHelper.sin(1.5707963267948966 * rot) / 2.0f + 0.5f;
        return componentWidth / 2.0f - buttonWidth / 2.0f - overflow * sin;
    }
    
    static {
        SUPPORTS_SCROLLING = MinecraftVersions.V1_19_4.orNewer();
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<ButtonSetting, ButtonWidget>
    {
        @Override
        public ButtonWidget[] create(final Setting setting, final ButtonSetting annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
            if (!annotation.text().isEmpty()) {
                return new ButtonWidget[] { ButtonWidget.text(annotation.text(), this.invokeButtonPress(setting, info)) };
            }
            if (!annotation.translation().isEmpty()) {
                return new ButtonWidget[] { ButtonWidget.i18n(annotation.translation(), this.invokeButtonPress(setting, info)) };
            }
            return new ButtonWidget[] { ButtonWidget.i18n(setting.getTranslationKey() + ".text", this.invokeButtonPress(setting, info)) };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[0];
        }
        
        private Pressable invokeButtonPress(final Setting setting, final SettingInfo<?> settingInfo) {
            return () -> {
                try {
                    final Method method = settingInfo.getMember();
                    final Parameter[] parameters = method.getParameters();
                    if (parameters.length == 0) {
                        method.invoke(settingInfo.config(), new Object[0]);
                    }
                    else {
                        final Object[] arguments = new Object[parameters.length];
                        for (int i = 0; i < parameters.length; ++i) {
                            final Parameter parameter = parameters[i];
                            if (parameter.getType() == Setting.class) {
                                arguments[i] = setting;
                            }
                        }
                        method.invoke(settingInfo.config(), arguments);
                    }
                }
                catch (final IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                }
            };
        }
    }
    
    @SettingElement
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ButtonSetting {
        @Deprecated
        String text() default "";
        
        @Deprecated
        String translation() default "";
    }
}
