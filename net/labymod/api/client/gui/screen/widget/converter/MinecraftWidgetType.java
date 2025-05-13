// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

public enum MinecraftWidgetType
{
    BUTTON("Button"), 
    SLIDER("Slider"), 
    TEXT_FIELD("TextField"), 
    TAB_NAVIGATION("TabLayout"), 
    STRING("String");
    
    private final String name;
    
    private MinecraftWidgetType(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
