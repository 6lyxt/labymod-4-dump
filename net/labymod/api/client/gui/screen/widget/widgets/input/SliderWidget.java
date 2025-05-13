// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.util.PrimitiveHelper;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.Parent;
import java.math.BigDecimal;
import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.action.SliderInteraction;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@SettingWidget
public class SliderWidget extends SimpleWidget
{
    public static final int STATE_WIDTH = 8;
    private static final boolean SUPPORTS_SCROLLING;
    private final SliderInteraction interaction;
    private float steps;
    private int decimals;
    private float min;
    private float max;
    private float value;
    private ComponentWidget sliderText;
    private Formatter<Component> formatter;
    
    public SliderWidget() {
        this(SliderInteraction.NOOP);
    }
    
    public SliderWidget(final SliderInteraction interaction) {
        this(1.0f, interaction);
    }
    
    public SliderWidget(final float steps, final SliderInteraction interaction) {
        this.steps = 1.0f;
        this.min = 0.0f;
        this.max = 100.0f;
        this.formatter = (Formatter<Component>)(value -> Component.text(String.format(Locale.ROOT, "%." + this.decimals, value)));
        this.steps(steps);
        this.decimals = ((steps < 1.0f || steps != (int)steps) ? new BigDecimal(String.valueOf(steps)).scale() : 0);
        this.interaction = interaction;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.sliderText = ComponentWidget.component(this.component())).addId("slider-text");
        super.addChild(this.sliderText);
        this.setStencil(SliderWidget.SUPPORTS_SCROLLING);
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Slider";
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    public SliderWidget range(final float min, final float max) {
        this.min = min;
        this.max = max;
        return this;
    }
    
    public SliderWidget steps(final float steps) {
        if (steps < 0.0f) {
            throw new RuntimeException("Steps value of slider cannot be less than zero! (" + steps);
        }
        this.steps = steps;
        return this;
    }
    
    public SliderWidget withFormatter(final Supplier<Component> formatter) {
        return this.withFormatter(value -> formatter.get());
    }
    
    public SliderWidget withFormatter(final Formatter<Component> formatter) {
        this.formatter = formatter;
        return this;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.11")
    public SliderWidget formatter(final Function<Float, RenderableComponent> formatter) {
        return this.withFormatter(value -> {
            final RenderableComponent apply = formatter.apply(value);
            return apply.toComponent();
        });
    }
    
    public float getPercentage() {
        final float max = this.max - this.min;
        final float value = this.value - this.min;
        return 1.0f / max * value;
    }
    
    public void setPercentage(final float percentage) {
        this.setValue(this.min + (this.max - this.min) * percentage);
    }
    
    public float getValue() {
        return this.value;
    }
    
    public Component component() {
        return this.formatter.format(this.value);
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void setValue(final double value) {
        this.setValue(value, true);
    }
    
    public void setValue(final double value, final boolean notify) {
        float newValue = (float)Math.min(this.max, Math.max(this.min, value));
        if (this.steps > 0.0f) {
            newValue = Math.round(newValue / this.steps) * this.steps;
        }
        if (Math.abs(newValue - this.value) >= this.steps - 0.001f) {
            this.value = newValue;
            if (notify) {
                this.interaction.updateValue(this.value);
            }
        }
        if (this.sliderText != null) {
            this.sliderText.setComponent(this.component());
        }
    }
    
    public Formatter<Component> formatter() {
        return this.formatter;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.11")
    public Function<Float, RenderableComponent> getFormatter() {
        return (Function<Float, RenderableComponent>)(value -> {
            final Component component = this.formatter.format(value);
            return RenderableComponent.of(component, false);
        });
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.isDragging()) {
            final Bounds bounds = this.bounds();
            final float left = bounds.getX(BoundsType.MIDDLE);
            final float offsetX = (float)context.mouse().getXDouble() - left - 4.0f;
            final float percentage = 1.0f / (bounds.getWidth(BoundsType.MIDDLE) - 8.0f) * offsetX;
            this.setPercentage(percentage);
        }
        if (SliderWidget.SUPPORTS_SCROLLING) {
            this.updateScrollingString();
        }
        super.renderWidget(context);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.isHovered() || super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return 50.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return 20.0f;
    }
    
    private void updateScrollingString() {
        if (this.sliderText == null) {
            return;
        }
        final RenderableComponent renderable = this.sliderText.renderable();
        if (renderable == null) {
            return;
        }
        final float fontSize = this.sliderText.fontSize().get().getFontSize();
        final float componentWidth = renderable.getWidth() * fontSize;
        final float buttonWidth = this.bounds().getWidth(BoundsType.MIDDLE) - 4.0f;
        final float offset = ButtonWidget.getTextScrollingOffset(componentWidth, buttonWidth);
        this.sliderText.translateX().set(offset);
    }
    
    static {
        SUPPORTS_SCROLLING = MinecraftVersions.V1_19_4.orNewer();
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<SliderSetting, SliderWidget>
    {
        @Override
        public SliderWidget[] create(final Setting setting, final SliderSetting annotation, final SettingAccessor accessor) {
            final Class<?> type = accessor.getType();
            final SliderWidget widget = new SliderWidget(annotation.steps(), value -> {
                if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                    accessor.set((int)value);
                }
                else if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                    accessor.set(value);
                }
                else if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                    accessor.set((double)value);
                }
                else if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                    accessor.set((short)value);
                }
                else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                    accessor.set((byte)value);
                }
                else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                    accessor.set((long)value);
                }
                return;
            });
            widget.range(annotation.min(), annotation.max());
            if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                widget.setValue(accessor.get());
            }
            else if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                widget.setValue(accessor.get());
            }
            else if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                widget.setValue(accessor.get());
            }
            else if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                widget.setValue(accessor.get());
            }
            else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                widget.setValue(accessor.get());
            }
            else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                widget.setValue(accessor.get());
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> {
                if (Reflection.isType(type, PrimitiveHelper.INTEGER)) {
                    widget.setValue((int)newValue);
                }
                else if (Reflection.isType(type, PrimitiveHelper.FLOAT)) {
                    widget.setValue((float)newValue);
                }
                else if (Reflection.isType(type, PrimitiveHelper.DOUBLE)) {
                    widget.setValue((double)newValue);
                }
                else if (Reflection.isType(type, PrimitiveHelper.SHORT)) {
                    widget.setValue((short)newValue);
                }
                else if (Reflection.isType(type, PrimitiveHelper.BYTE)) {
                    widget.setValue((byte)newValue);
                }
                else if (Reflection.isType(type, PrimitiveHelper.LONG)) {
                    widget.setValue((double)(long)newValue);
                }
                return;
            });
            return new SliderWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return PrimitiveHelper.NUMBER_PRIMITIVES;
        }
    }
    
    @FunctionalInterface
    public interface Formatter<T>
    {
        T format(final float p0);
    }
    
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SliderSetting {
        float steps() default 1.0f;
        
        float min();
        
        float max();
    }
}
