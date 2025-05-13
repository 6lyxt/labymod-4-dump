// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;

public class LssMath
{
    public static float min(final Widget widget, final String key, final ProcessedObject[] values) {
        float min = 2.1474836E9f;
        for (final ProcessedObject value : values) {
            min = Math.min(min, parseInt(widget, key, value.value()));
        }
        return min;
    }
    
    public static float max(final Widget widget, final String key, final ProcessedObject[] values) {
        float max = -2.1474836E9f;
        for (final ProcessedObject value : values) {
            max = Math.max(max, parseInt(widget, key, value.value()));
        }
        return max;
    }
    
    public static float parseInt(final Widget widget, final String key, final Object value) {
        if (value instanceof final Number n) {
            return n.floatValue();
        }
        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Invalid numeric value: '" + String.valueOf(value) + "' (" + value.getClass().getSimpleName());
        }
        final String s = (String)value;
        if (s.endsWith("%")) {
            return getPercentageValue(widget, key, Float.parseFloat(s.substring(0, s.length() - 1)));
        }
        return Float.parseFloat(s);
    }
    
    public static float calc(final Widget widget, final String key, final String text) {
        Float value = null;
        Float lastNumber = null;
        Character operator = null;
        final StringBuilder number = new StringBuilder();
        final char[] chars = text.replace(" ", "").toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            final char c = chars[i];
            final boolean lastRun = i == chars.length - 1;
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
                if (!lastRun) {
                    continue;
                }
            }
            if (number.isEmpty()) {
                throw new IllegalArgumentException("Invalid calc expression: \"" + text);
            }
            boolean unitChar = false;
            float num;
            if (!lastRun && c == 'v' && (chars[i + 1] == 'h' || chars[i + 1] == 'w')) {
                num = getPercentageValue(widget.getRoot(), key, Float.parseFloat(number.toString()));
                ++i;
                unitChar = true;
            }
            else if (c == '%') {
                num = getPercentageValue(widget.getParent(), key, Float.parseFloat(number.toString()));
                unitChar = true;
            }
            else {
                num = Float.parseFloat(number.toString());
            }
            if (operator != null || lastRun) {
                value = applyOperator(text, operator, (value == null) ? lastNumber : value, num);
                lastNumber = num;
            }
            else if (chars[i - 1] != '%' && chars[i - 1] != 'h' && chars[i - 1] != 'w') {
                lastNumber = num;
            }
            if (!unitChar) {
                operator = c;
                number.setLength();
            }
        }
        if (value == null) {
            throw new IllegalArgumentException("Invalid calc expression: \"" + text);
        }
        return value;
    }
    
    public static float getPercentageValue(final Parent parent, final String key, final float value) {
        if (parent == null || value == 0.0f) {
            return 0.0f;
        }
        boolean vertical = false;
        final int heightIndex = key.indexOf("eight");
        if (heightIndex > 0) {
            final char c = key.charAt(heightIndex - 1);
            if (Character.toLowerCase(c) == 'h') {
                vertical = true;
            }
        }
        boolean horizontal = false;
        final int widthIndex = key.indexOf("idth");
        if (widthIndex > 0) {
            final char c2 = key.charAt(widthIndex - 1);
            if (Character.toLowerCase(c2) == 'w') {
                horizontal = true;
            }
        }
        vertical |= (key.equals("top") || key.equals("bottom") || key.equals("maxHeight") || key.equals("minHeight"));
        horizontal |= (key.equals("left") || key.equals("right") || key.equals("maxWidth") || key.equals("minWidth") || key.equals("innerRadius"));
        final Bounds parentBounds = parent.bounds();
        float max;
        if (vertical) {
            Float size;
            if (parent instanceof final Widget widget) {
                size = widget.getSize(SizeType.MAX, WidgetSide.HEIGHT);
            }
            else {
                size = null;
            }
            final Float maxHeight = size;
            if (maxHeight != null && ((Widget)parent).hasSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)) {
                max = maxHeight - parentBounds.getVerticalOffset(BoundsType.OUTER);
            }
            else {
                max = parentBounds.getHeight(BoundsType.INNER);
            }
        }
        else {
            if (!horizontal) {
                throw new IllegalArgumentException("Percentage cannot be calculated for key: '" + key + "' (requires a width or height defining key)");
            }
            Float size2;
            if (parent instanceof final Widget widget) {
                size2 = widget.getSize(SizeType.MAX, WidgetSide.WIDTH);
            }
            else {
                size2 = null;
            }
            final Float maxWidth = size2;
            if (maxWidth != null && ((Widget)parent).hasSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) {
                max = maxWidth - parentBounds.getHorizontalOffset(BoundsType.OUTER);
            }
            else {
                max = parentBounds.getWidth(BoundsType.INNER);
            }
        }
        return max * value / 100.0f;
    }
    
    private static float applyOperator(final String text, final char operator, final float n1, final float n2) {
        return switch (operator) {
            case '+' -> n1 + n2;
            case '-' -> n1 - n2;
            case '*' -> n1 * n2;
            case '/' -> n1 / n2;
            default -> throw new IllegalArgumentException("Invalid calc expression: Didn't expect " + operator + " (\"" + text + "\")");
        };
    }
}
