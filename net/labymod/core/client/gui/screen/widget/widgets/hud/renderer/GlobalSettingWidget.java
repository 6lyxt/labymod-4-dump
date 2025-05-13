// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.renderer;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class GlobalSettingWidget extends HorizontalListWidget
{
    private final ConfigProperty<?> property;
    private final String i18nKey;
    
    public GlobalSettingWidget(final ConfigProperty<?> property, final String i18nKey) {
        this.property = property;
        this.i18nKey = i18nKey;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Object value = this.property.get();
        if (value instanceof Color) {
            final ColorPickerWidget colorPickerWidget = ColorPickerWidget.of((ConfigProperty<Color>)this.property);
            colorPickerWidget.addId("color-picker");
            this.addEntry(colorPickerWidget);
        }
        final ComponentWidget labelWidget = ComponentWidget.i18n(this.i18nKey);
        labelWidget.addId("label");
        this.addEntry(labelWidget);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean onPress() {
        for (final HorizontalListEntry child : this.children) {
            if (child.onPress()) {
                return true;
            }
        }
        return false;
    }
}
