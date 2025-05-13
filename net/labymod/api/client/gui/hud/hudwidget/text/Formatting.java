// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.text;

import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.Component;

public enum Formatting
{
    NONE("{key} {value}", "{value} {key}"), 
    VALUE_ONLY("{value}", "{value}"), 
    COLON("{key}: {value}", "{value} :{key}"), 
    BRACKETS("{key}> {value}", "{value} <{key}"), 
    SQUARE_BRACKETS("[{key}] {value}", "{value} [{key}]"), 
    ROUND_BRACKETS("({key}) {value}", "{value} ({key})"), 
    HYPHEN("{key} - {value}", "{value} - {key}"), 
    SUFFIX("{value} {key}", "{value} {key}"), 
    IN_SQUARE_BRACKETS("[{key} {value}]", "[{value} {key}]"), 
    GUILLEMET("{key} » {value}", "{value} « {key}");
    
    private final String leftFormat;
    private final String rightFormat;
    
    private Formatting(final String leftFormat, final String rightFormat) {
        this.leftFormat = leftFormat;
        this.rightFormat = rightFormat;
    }
    
    public String getFormat(final boolean leftAlignment) {
        return leftAlignment ? this.leftFormat : this.rightFormat;
    }
    
    public boolean isEnclosed() {
        final String format = this.getFormat(true);
        return !format.startsWith("{");
    }
    
    @Override
    public String toString() {
        return this.getFormat(true).replace("{key}", "K").replace("{value}", "V");
    }
    
    public Component build(final Component key, final Component value, final boolean leftAlignment, final int color) {
        return this.build(key, value, leftAlignment, TextColor.color(color));
    }
    
    public Component build(final Component key, final Component value, final boolean leftAlignment, final TextColor color) {
        final String format = this.getFormat(leftAlignment);
        Component component = Component.empty();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < format.length(); ++i) {
            final char character = format.charAt(i);
            if (character == '{') {
                if (buffer.length() > 0) {
                    component = component.append(Component.text(buffer.toString(), color));
                }
                buffer.setLength();
            }
            else if (character == '}') {
                final String string = buffer.toString();
                switch (string) {
                    case "key": {
                        component = component.append(key);
                        break;
                    }
                    case "value": {
                        component = component.append(value);
                        break;
                    }
                }
                buffer.setLength();
            }
            else {
                buffer.append(character);
            }
        }
        if (buffer.length() > 0) {
            component = component.append(Component.text(buffer.toString(), color));
        }
        return component;
    }
}
