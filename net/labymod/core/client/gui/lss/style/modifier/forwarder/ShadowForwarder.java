// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder;

import net.labymod.api.client.gui.screen.widget.attributes.Shadow;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class ShadowForwarder implements Forwarder
{
    private final WidgetModifier widgetModifier;
    
    public ShadowForwarder(final WidgetModifier widgetModifier) {
        this.widgetModifier = widgetModifier;
    }
    
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        return key.equals("boxShadow");
    }
    
    @Override
    public void forward(final Widget widget, final String key, final ProcessedObject object) throws Exception {
        if (widget instanceof AbstractWidget) {
            final AbstractWidget<?> abstractWidget = (AbstractWidget<?>)widget;
            final Object value = object.value();
            if (value instanceof final String string) {
                final String lowerCase = string.toLowerCase(Locale.ROOT);
                Shadow shadow;
                if (lowerCase.startsWith("classic ") || lowerCase.startsWith("classic-background")) {
                    boolean left = false;
                    boolean top = false;
                    boolean right = false;
                    boolean bottom = false;
                    final boolean classicIsBackground = lowerCase.startsWith("classic-background");
                    for (final String argument : string.split(" ")) {
                        final String lowerCase2 = argument.toLowerCase(Locale.ROOT);
                        switch (lowerCase2) {
                            case "left": {
                                left = true;
                                break;
                            }
                            case "top": {
                                top = true;
                                break;
                            }
                            case "right": {
                                right = true;
                                break;
                            }
                            case "bottom": {
                                bottom = true;
                                break;
                            }
                        }
                    }
                    shadow = new Shadow(true, left, top, right, bottom, classicIsBackground);
                }
                else if (string.equals("none")) {
                    shadow = null;
                }
                else {
                    final String[] values = string.split(" ");
                    int index = 0;
                    final boolean inset = values[index].equals("inset");
                    if (inset) {
                        ++index;
                    }
                    final float offsetX = Float.parseFloat(values[index]);
                    final float offsetY = Float.parseFloat(values[index + 1]);
                    final float blur = Float.parseFloat(values[index + 2]);
                    final float spread = Float.parseFloat(values[index + 3]);
                    final StringBuilder colorString = new StringBuilder();
                    for (int i = index + 4; i < values.length; ++i) {
                        colorString.append(values[i]);
                    }
                    final ProcessedObject[] processValue = this.widgetModifier.processValue(widget, Integer.TYPE, key, colorString.toString());
                    final int color = (int)processValue[0].value();
                    shadow = new Shadow(inset, offsetX, offsetY, spread, blur, color);
                }
                abstractWidget.shadow = shadow;
            }
        }
    }
    
    @Override
    public void reset(final Widget widget, final String key) {
        if (!(widget instanceof AbstractWidget)) {
            return;
        }
        final AbstractWidget abstractWidget = (AbstractWidget)widget;
        abstractWidget.shadow = null;
    }
    
    @Override
    public Class<?> getType(final Widget widget, final String key, final String value) {
        return String.class;
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
