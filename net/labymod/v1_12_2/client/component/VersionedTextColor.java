// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.component;

import java.util.Locale;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.Color;
import net.labymod.api.client.component.format.TextColor;

public class VersionedTextColor implements TextColor
{
    private final a formatting;
    private final Color color;
    private final boolean named;
    private String hex;
    
    private VersionedTextColor(final a formatting, final Color color, final String hex, final boolean named) {
        this.named = named;
        this.hex = hex;
        this.color = color;
        if (formatting == null) {
            final TextColor closestDefaultTextColor = ColorUtil.getClosestDefaultTextColor(color);
            if (closestDefaultTextColor instanceof VersionedTextColor) {
                this.formatting = ((VersionedTextColor)closestDefaultTextColor).formatting;
            }
            else {
                this.formatting = a.p;
            }
        }
        else {
            this.formatting = formatting;
        }
    }
    
    protected VersionedTextColor(final a formatting, final int color) {
        this(formatting, Color.of(color), null, true);
    }
    
    public VersionedTextColor(final Color color) {
        this(null, color, null, false);
    }
    
    public VersionedTextColor(final String hex) {
        this(null, Color.of(hex), hex, false);
    }
    
    @Override
    public int getValue() {
        return this.color.get();
    }
    
    @Override
    public String serialize() {
        return (this.formatting == null) ? this.hex : this.formatting.e();
    }
    
    @Override
    public Color color() {
        return this.color;
    }
    
    public a getFormatting() {
        return this.formatting;
    }
    
    public String getHex() {
        if (this.hex == null) {
            this.hex = String.format(Locale.ROOT, "#%06X", this.color.get());
        }
        return this.hex;
    }
    
    public boolean isNamed() {
        return this.named;
    }
    
    @Override
    public String toString() {
        return this.named ? this.formatting.e() : this.hex;
    }
}
