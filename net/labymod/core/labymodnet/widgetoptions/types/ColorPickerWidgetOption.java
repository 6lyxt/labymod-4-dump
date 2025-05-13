// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions.types;

import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;

public class ColorPickerWidgetOption extends WidgetOption
{
    private static final String DEBOUNCE_ID = "color-picker-debounce";
    private static final String UPDATE_LISTENER_ID = "color-picker-cosmetic-option";
    
    public ColorPickerWidgetOption(final String optionName, final int optionIndex) {
        super(optionName, optionIndex);
    }
    
    @Override
    protected void create(final String data, final Consumer<Widget> consumer) {
        Color currentColor;
        try {
            currentColor = Color.of("#" + data);
        }
        catch (final NumberFormatException e) {
            ColorPickerWidgetOption.LOGGER.info("CosmeticWidget.getOptionWidgets: invalid color: " + data, new Object[0]);
            currentColor = Color.BLUE;
        }
        final ColorPickerWidget colorPickerWidget = ColorPickerWidget.of(currentColor);
        colorPickerWidget.addUpdateListener("color-picker-cosmetic-option", color -> this.setData("color-picker-debounce", ColorUtil.rgbToHex(color.get()).substring(1)));
        consumer.accept(colorPickerWidget);
    }
}
