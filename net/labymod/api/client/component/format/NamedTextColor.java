// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format;

import net.labymod.api.client.component.ComponentService;

public class NamedTextColor
{
    public static final TextColor BLACK;
    public static final TextColor DARK_BLUE;
    public static final TextColor DARK_GREEN;
    public static final TextColor DARK_AQUA;
    public static final TextColor DARK_RED;
    public static final TextColor DARK_PURPLE;
    public static final TextColor GOLD;
    public static final TextColor GRAY;
    public static final TextColor DARK_GRAY;
    public static final TextColor BLUE;
    public static final TextColor GREEN;
    public static final TextColor AQUA;
    public static final TextColor RED;
    public static final TextColor LIGHT_PURPLE;
    public static final TextColor YELLOW;
    public static final TextColor WHITE;
    
    private NamedTextColor() {
    }
    
    static {
        BLACK = ComponentService.parseTextColor("black");
        DARK_BLUE = ComponentService.parseTextColor("dark_blue");
        DARK_GREEN = ComponentService.parseTextColor("dark_green");
        DARK_AQUA = ComponentService.parseTextColor("dark_aqua");
        DARK_RED = ComponentService.parseTextColor("dark_red");
        DARK_PURPLE = ComponentService.parseTextColor("dark_purple");
        GOLD = ComponentService.parseTextColor("gold");
        GRAY = ComponentService.parseTextColor("gray");
        DARK_GRAY = ComponentService.parseTextColor("dark_gray");
        BLUE = ComponentService.parseTextColor("blue");
        GREEN = ComponentService.parseTextColor("green");
        AQUA = ComponentService.parseTextColor("aqua");
        RED = ComponentService.parseTextColor("red");
        LIGHT_PURPLE = ComponentService.parseTextColor("light_purple");
        YELLOW = ComponentService.parseTextColor("yellow");
        WHITE = ComponentService.parseTextColor("white");
    }
}
