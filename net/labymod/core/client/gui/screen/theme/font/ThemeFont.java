// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.font;

import java.util.ArrayList;
import org.jetbrains.annotations.ApiStatus;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ThemeFont
{
    @SerializedName("default")
    private Font defaultFont;
    @ApiStatus.Experimental
    private List<Font> others;
    
    public ThemeFont() {
        this.others = new ArrayList<Font>();
    }
    
    public Font defaultFont() {
        return this.defaultFont;
    }
    
    @ApiStatus.Experimental
    public List<Font> others() {
        return this.others;
    }
    
    public static class Font
    {
        private String name;
        private String font;
        
        public Font() {
        }
        
        public Font(final String name, final String font) {
            this.name = name;
            this.font = font;
        }
        
        public String name() {
            return this.name;
        }
        
        public void name(final String name) {
            this.name = name;
        }
        
        public String font() {
            return this.font;
        }
        
        public void font(final String font) {
            this.font = font;
        }
    }
}
