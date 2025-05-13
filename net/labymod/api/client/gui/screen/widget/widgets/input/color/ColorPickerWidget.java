// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.property.Property;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.util.function.Consumer;
import net.labymod.api.util.Color;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.ColorPickerOverlayWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;

@AutoWidget
@SettingWidget
@Link("color-picker.lss")
public class ColorPickerWidget extends OverlayWidget
{
    private final ColorData colorData;
    private DivWidget colorPreview;
    
    private ColorPickerWidget(final ColorData colorData) {
        super(WidgetReference.ClickRemoveStrategy.OUTSIDE, WidgetReference.KeyPressRemoveStrategy.ESCAPE, true);
        this.colorData = colorData;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<DivWidget>)this).addChild(this.colorPreview = new DivWidget());
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.colorPreview != null) {
            this.colorPreview.backgroundColor().set(this.colorData.getActualColor().get());
        }
        super.renderWidget(context);
    }
    
    @NotNull
    @Override
    protected Parent content() {
        return new ColorPickerOverlayWidget(this.colorData);
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    public boolean enabledAlpha() {
        return this.colorData.enabledAlpha();
    }
    
    public boolean enabledChroma() {
        return this.colorData.enabledChroma();
    }
    
    public boolean enabledChromaSpeed() {
        return this.colorData.enabledChromaSpeed();
    }
    
    public ColorPickerWidget alpha(final boolean alpha) {
        this.colorData.alphaEnabled(alpha);
        return this;
    }
    
    public ColorPickerWidget chroma(final boolean chroma) {
        this.colorData.chromaEnabled(chroma);
        return this;
    }
    
    public ColorPickerWidget chromaSpeed(final boolean chromaSpeed) {
        this.colorData.chromaSpeedEnabled(chromaSpeed);
        return this;
    }
    
    public ColorPickerWidget set(final Color color) {
        this.colorData.set(color);
        return this;
    }
    
    public ColorPickerWidget addUpdateListener(final Object instance, final Consumer<Color> updateListener) {
        this.colorData.addUpdateListener(instance, () -> updateListener.accept(this.colorData.getActualColor()));
        return this;
    }
    
    public Color value() {
        return this.colorData.getActualColor();
    }
    
    public static ColorPickerWidget of(final Color color) {
        return new ColorPickerWidget(new ColorData(color));
    }
    
    public static ColorPickerWidget of(final ConfigProperty<Color> property) {
        final ColorPickerWidget widget = of(property.get());
        final ColorData colorData = widget.colorData;
        colorData.addUpdateListener(widget, () -> property.set(colorData.getActualColor()));
        property.addChangeListener((t, oldValue, newValue) -> colorData.set(newValue));
        return widget;
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<ColorPickerSetting, ColorPickerWidget>
    {
        @Override
        public ColorPickerWidget[] create(final Setting setting, final ColorPickerSetting annotation, final SettingAccessor accessor) {
            final Object accessorObject = accessor.get();
            ColorPickerWidget widget;
            if (accessorObject instanceof Color) {
                widget = ColorPickerWidget.of(accessor.property());
            }
            else {
                if (annotation.chroma()) {
                    throw new IllegalArgumentException("Chroma is only supported for Color objects");
                }
                widget = ColorPickerWidget.of(Color.of((int)accessorObject));
                final ColorData colorData = widget.colorData;
                colorData.addUpdateListener(this, () -> accessor.set(colorData.getColor().get()));
            }
            final ColorData colorData = widget.colorData;
            colorData.chromaEnabled(annotation.chroma());
            colorData.alphaEnabled(annotation.alpha());
            colorData.removeAlpha(annotation.removeAlpha());
            colorData.chromaSpeedEnabled(annotation.chromaSpeed());
            colorData.removeChromaSpeed(annotation.removeChromaSpeed());
            return new ColorPickerWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { Integer.class, Integer.TYPE, Color.class };
        }
    }
    
    @SettingElement
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ColorPickerSetting {
        boolean alpha() default false;
        
        boolean removeAlpha() default true;
        
        boolean chroma() default false;
        
        boolean chromaSpeed() default true;
        
        boolean removeChromaSpeed() default false;
    }
}
