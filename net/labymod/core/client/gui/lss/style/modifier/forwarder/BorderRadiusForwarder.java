// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.core.client.gui.lss.style.modifier.forwarder.exception.ForwardException;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class BorderRadiusForwarder implements Forwarder
{
    private static final String LOWER_EDGE_SOFTNESS = "lowerEdgeSoftness";
    private static final String UPPER_EDGE_SOFTNESS = "upperEdgeSoftness";
    private static final String BORDER_SOFTNESS = "borderSoftness";
    private static final String BORDER_RADIUS = "borderRadius";
    private static final String BORDER_TOP_LEFT_RADIUS = "borderTopLeftRadius";
    private static final String BORDER_TOP_RIGHT_RADIUS = "borderTopRightRadius";
    private static final String BORDER_BOTTOM_LEFT_RADIUS = "borderBottomLeftRadius";
    private static final String BORDER_BOTTOM_RIGHT_RADIUS = "borderBottomRightRadius";
    private static final String BORDER_THICKNESS = "borderThickness";
    private static final String BORDER_BACKGROUND = "borderBackground";
    
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("borderRadius") || key.equals("borderTopLeftRadius") || key.equals("borderTopRightRadius") || key.equals("borderBottomLeftRadius") || key.equals("borderBottomRightRadius") || key.equals("lowerEdgeSoftness") || key.equals("upperEdgeSoftness") || key.equals("borderSoftness") || key.equals("borderThickness") || key.equals("borderBackground");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
        if (abstractWidget.getBorderRadius() == null) {
            abstractWidget.setBorderRadius(new BorderRadius());
        }
        final Object value = object.value();
        if (value instanceof final String str) {
            this.handleRadius(abstractWidget, str);
            return;
        }
        if (value instanceof final Float f) {
            if (key.equals("borderTopLeftRadius") || key.equals("borderTopRightRadius") || key.equals("borderBottomRightRadius") || key.equals("borderBottomLeftRadius")) {
                this.handleRadius(abstractWidget, key, f);
            }
            else {
                this.handleBorderProperties(abstractWidget, key, f);
            }
        }
        if (value instanceof final Integer i) {
            abstractWidget.getBorderRadius().setBorderBackground(i);
        }
    }
    
    private void handleBorderProperties(final AbstractWidget<?> widget, final String key, final float value) {
        final BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
            return;
        }
        switch (key) {
            case "lowerEdgeSoftness": {
                borderRadius.setLowerEdgeSoftness(value);
                break;
            }
            case "upperEdgeSoftness": {
                borderRadius.setUpperEdgeSoftness(value);
                break;
            }
            case "borderThickness": {
                borderRadius.setThickness(value);
                break;
            }
            case "borderSoftness": {
                borderRadius.setBorderSoftness(value);
                break;
            }
        }
    }
    
    private void handleRadius(final AbstractWidget<?> widget, final String key, final float value) {
        BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
            borderRadius = new BorderRadius();
            widget.setBorderRadius(borderRadius);
        }
        switch (key) {
            case "borderTopLeftRadius": {
                borderRadius.setLeftTop(value);
                break;
            }
            case "borderTopRightRadius": {
                borderRadius.setRightTop(value);
                break;
            }
            case "borderBottomRightRadius": {
                borderRadius.setRightBottom(value);
                break;
            }
            case "borderBottomLeftRadius": {
                borderRadius.setLeftBottom(value);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + key);
            }
        }
    }
    
    private void handleRadius(final AbstractWidget<?> widget, final String value) throws ForwardException {
        final String[] values = value.split(" ");
        if (values.length > 4) {
            throw new ForwardException(String.format(Locale.ROOT, "Invalid \"%s\" property. Too many values. (Max: 4, Current: %s)", "border-radius", values.length));
        }
        float leftTop;
        float rightTop;
        float rightBottom;
        float leftBottom;
        if (values.length == 4) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[2]);
            leftBottom = Float.parseFloat(values[3]);
        }
        else if (values.length == 3) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            leftBottom = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[2]);
        }
        else if (values.length == 2) {
            leftTop = Float.parseFloat(values[0]);
            rightTop = Float.parseFloat(values[1]);
            leftBottom = Float.parseFloat(values[1]);
            rightBottom = Float.parseFloat(values[0]);
        }
        else {
            leftBottom = (rightBottom = (rightTop = (leftTop = Float.parseFloat(values[0]))));
        }
        final BorderRadius borderRadius = widget.getBorderRadius();
        if (borderRadius == null) {
            widget.setBorderRadius(new BorderRadius(leftTop, rightTop, leftBottom, rightBottom));
        }
        else {
            borderRadius.setRadius(leftTop, rightTop, leftBottom, rightBottom);
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            final BorderRadius borderRadius = abstractWidget.getBorderRadius();
            if (borderRadius != null) {
                switch (key) {
                    case "borderSoftness": {
                        borderRadius.setBorderSoftness(0.0f);
                        break;
                    }
                    case "borderRadius": {
                        borderRadius.setRadius(0.0f);
                        break;
                    }
                    case "borderTopLeftRadius": {
                        borderRadius.setLeftTop(0.0f);
                        break;
                    }
                    case "borderTopRightRadius": {
                        borderRadius.setRightTop(0.0f);
                        break;
                    }
                    case "borderBottomRightRadius": {
                        borderRadius.setRightBottom(0.0f);
                        break;
                    }
                    case "borderBottomLeftRadius": {
                        borderRadius.setLeftBottom(0.0f);
                        break;
                    }
                    case "borderBackground": {
                        borderRadius.setBorderBackground(0);
                        break;
                    }
                    case "borderThickness": {
                        borderRadius.setThickness(0.0f);
                        break;
                    }
                    case "upperEdgeSoftness": {
                        borderRadius.setUpperEdgeSoftness(0.0f);
                        break;
                    }
                    case "lowerEdgeSoftness": {
                        borderRadius.setLowerEdgeSoftness(0.0f);
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        if (key.equals("borderRadius")) {
            return String.class;
        }
        return Float.class;
    }
    
    @Override
    public int getPriority() {
        return 0;
    }
}
