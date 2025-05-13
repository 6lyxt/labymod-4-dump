// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class BorderPostProcessor implements PostProcessor
{
    private static final Border NO_BORDER;
    private final WidgetModifier modifier;
    
    public BorderPostProcessor(final WidgetModifier modifier) {
        this.modifier = modifier;
    }
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return key.equals("border");
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        final String rawValue = element.getRawValue();
        if (rawValue.equals("none")) {
            return BorderPostProcessor.NO_BORDER;
        }
        final int rgbIndex = rawValue.indexOf("rgb");
        String[] sizes;
        String color;
        if (rgbIndex != -1) {
            sizes = rawValue.substring(0, rgbIndex - 1).split(" ");
            color = rawValue.substring(rgbIndex);
        }
        else {
            sizes = rawValue.split(" ");
            color = sizes[sizes.length - 1];
        }
        final ProcessedObject[] objects = this.modifier.processValue(widget, Integer.TYPE, "color", color);
        final int colorValue = (int)objects[0].value();
        final int length = sizes.length;
        if (length <= 2) {
            return new Border(Integer.parseInt(sizes[0]), colorValue);
        }
        return new Border(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]), Integer.parseInt(sizes[2]), Integer.parseInt(sizes[3]), colorValue);
    }
    
    static {
        NO_BORDER = new Border(0, 0);
    }
    
    public static class Border
    {
        public final int leftWidth;
        public final int topWidth;
        public final int rightWidth;
        public final int bottomWidth;
        public final int color;
        
        public Border(final int size, final int color) {
            this.leftWidth = size;
            this.topWidth = size;
            this.rightWidth = size;
            this.bottomWidth = size;
            this.color = color;
        }
        
        public Border(final int leftWidth, final int topWidth, final int rightWidth, final int bottomWidth, final int color) {
            this.leftWidth = leftWidth;
            this.topWidth = topWidth;
            this.rightWidth = rightWidth;
            this.bottomWidth = bottomWidth;
            this.color = color;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Border border = (Border)o;
            return this.leftWidth == border.leftWidth && this.topWidth == border.topWidth && this.rightWidth == border.rightWidth && this.bottomWidth == border.bottomWidth && this.color == border.color;
        }
        
        @Override
        public int hashCode() {
            int result = this.leftWidth;
            result = 31 * result + this.topWidth;
            result = 31 * result + this.rightWidth;
            result = 31 * result + this.bottomWidth;
            result = 31 * result + this.color;
            return result;
        }
    }
}
