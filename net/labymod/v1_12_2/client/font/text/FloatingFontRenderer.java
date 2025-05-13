// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.font.text;

import java.util.Arrays;
import java.util.List;
import net.labymod.api.util.math.MathHelper;

public abstract class FloatingFontRenderer extends bip
{
    public FloatingFontRenderer(final bid options, final nf location, final cdr textureManager, final boolean useUnicode) {
        super(options, location, textureManager, useUnicode);
    }
    
    public abstract float getCharWidthFloat(final char p0);
    
    public abstract float getStringWidthFloat(final String p0);
    
    public int a(final char c) {
        return MathHelper.ceil(this.getCharWidthFloat(c));
    }
    
    protected final int getOriginalCharWidth(final char c) {
        return super.a(c);
    }
    
    public int a(final String text) {
        if (text == null) {
            return 0;
        }
        return MathHelper.ceil(this.getStringWidthFloat(text));
    }
    
    protected final int getOriginalStringWidth(final String text) {
        if (text == null) {
            return 0;
        }
        return super.a(text);
    }
    
    public String a(final String s, final int maxWidth, final boolean reverse) {
        return this.trimStringToWidthFloat(s, (float)maxWidth, reverse);
    }
    
    public String trimStringToWidthFloat(final String s, final float maxWidth, final boolean reverse) {
        final StringBuilder var4 = new StringBuilder();
        float currentWidth = 0.0f;
        final int var5 = reverse ? (s.length() - 1) : 0;
        final int var6 = reverse ? -1 : 1;
        boolean var7 = false;
        boolean var8 = false;
        for (int var9 = var5; var9 >= 0 && var9 < s.length() && currentWidth < maxWidth; var9 += var6) {
            final char var10 = s.charAt(var9);
            final float var11 = this.getCharWidthFloat(var10);
            if (var7) {
                var7 = false;
                if (var10 != 'l' && var10 != 'L') {
                    if (var10 == 'r' || var10 == 'R') {
                        var8 = false;
                    }
                }
                else {
                    var8 = true;
                }
            }
            else if (var11 < 0.0f) {
                var7 = true;
            }
            else {
                currentWidth += var11;
                if (var8) {
                    ++currentWidth;
                }
            }
            if (currentWidth > maxWidth) {
                break;
            }
            if (reverse) {
                var4.insert(0, var10);
            }
            else {
                var4.append(var10);
            }
        }
        return var4.toString();
    }
    
    public List<String> c(final String s, final int maxWidth) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, (float)maxWidth).split("\n"));
    }
    
    String wrapFormattedStringToWidth(final String text, final float maxWidth) {
        if (maxWidth <= 0.0f || text.length() <= 1) {
            return text;
        }
        final int width = this.sizeStringToWidth(text, maxWidth);
        if (text.length() <= width) {
            return text;
        }
        final String textSection = text.substring(0, width);
        final char lastChar = text.charAt(width);
        final boolean spaceOrNewLine = lastChar == ' ' || lastChar == '\n';
        final String formattedString = b(textSection) + text.substring(width + (spaceOrNewLine ? 1 : 0));
        return textSection + "\n" + this.wrapFormattedStringToWidth(formattedString, maxWidth);
    }
    
    private int sizeStringToWidth(final String s, final float maxWidth) {
        final int length = s.length();
        float currentCharWidth = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < length) {
            final char c = s.charAt(var5);
            Label_0167: {
                switch (c) {
                    case '\n': {
                        --var5;
                        break Label_0167;
                    }
                    case ' ': {
                        var6 = var5;
                        break;
                    }
                    case '§': {
                        if (var5 >= length - 1) {
                            break Label_0167;
                        }
                        ++var5;
                        final char var8 = s.charAt(var5);
                        if (var8 == 'l' || var8 == 'L') {
                            var7 = true;
                            break Label_0167;
                        }
                        if (var8 == 'r' || var8 == 'R' || c(var8)) {
                            var7 = false;
                        }
                        break Label_0167;
                    }
                }
                currentCharWidth += this.getCharWidthFloat(c);
                if (var7) {
                    ++currentCharWidth;
                }
            }
            if (c == '\n') {
                var6 = ++var5;
                break;
            }
            if (currentCharWidth > maxWidth) {
                break;
            }
            ++var5;
        }
        return (var5 != length && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    private static boolean c(final char p_isFormatColor_0_) {
        return (p_isFormatColor_0_ >= '0' && p_isFormatColor_0_ <= '9') || (p_isFormatColor_0_ >= 'a' && p_isFormatColor_0_ <= 'f') || (p_isFormatColor_0_ >= 'A' && p_isFormatColor_0_ <= 'F');
    }
    
    private static boolean d(final char p_isFormatSpecial_0_) {
        return (p_isFormatSpecial_0_ >= 'k' && p_isFormatSpecial_0_ <= 'o') || (p_isFormatSpecial_0_ >= 'K' && p_isFormatSpecial_0_ <= 'O') || p_isFormatSpecial_0_ == 'r' || p_isFormatSpecial_0_ == 'R';
    }
}
