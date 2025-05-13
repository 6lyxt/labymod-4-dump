// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.util.ColorUtil;
import java.util.HashMap;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.component.format.TextColor;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class ColorPostProcessor implements PostProcessor
{
    private static final Map<String, TextColor> COLORS;
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        if (element instanceof final Function function) {
            final String name = function.getName();
            return name.equals("rgb") || name.equals("rgba");
        }
        final String rawValue = element.getRawValue();
        return rawValue.startsWith("#") || ColorPostProcessor.COLORS.get(rawValue) != null;
    }
    
    public static int parse(final Function function, final ProcessedObject[] values) {
        final boolean hasAlpha = function.getName().equals("rgba");
        if (hasAlpha && values.length == 2) {
            final int value = (int)values[0].value();
            final float alpha = (float)values[1].value();
            return ColorFormat.ARGB32.pack(value, (int)alpha);
        }
        final int red = (int)values[0].value();
        final int green = (int)values[1].value();
        final int blue = (int)values[2].value();
        final float alpha2 = (float)(hasAlpha ? values[3].value() : 255.0f);
        final boolean isAlphaFloat = hasAlpha && values[3].rawValue().contains(".");
        return ColorFormat.ARGB32.pack(red, green, blue, (int)(isAlphaFloat ? (alpha2 * 255.0f) : alpha2));
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        if (element instanceof FixedElement) {
            final String raw = element.getRawValue();
            if (raw.startsWith("#")) {
                return Integer.parseInt(raw.substring(1), 16) | 0xFF000000;
            }
            final TextColor textColor = ColorPostProcessor.COLORS.get(raw);
            if (textColor != null) {
                return textColor.getValue() | 0xFF000000;
            }
            throw new IllegalArgumentException("Unknown color: " + raw);
        }
        else {
            if (element instanceof final Function function) {
                return parse(function, function.firstValues(widget, key));
            }
            return null;
        }
    }
    
    static {
        COLORS = new HashMap<String, TextColor>();
        for (final TextColor color : ColorUtil.DEFAULT_COLORS) {
            ColorPostProcessor.COLORS.put(color.toString(), color);
        }
    }
}
