// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format.numbers;

import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;

record StyledFormat(Style style) implements NumberFormat {
    public static final StyledFormat NO_STYLE;
    public static final StyledFormat SIDEBAR_DEFAULT;
    public static final StyledFormat PLAYER_LIST_DEFAULT;
    
    @Override
    public Component format(final int number) {
        return Component.text(Integer.toString(number), this.style);
    }
    
    static {
        NO_STYLE = new StyledFormat(Style.empty());
        SIDEBAR_DEFAULT = new StyledFormat(((StyleableBuilder<T, Style.Builder>)Style.builder()).color(NamedTextColor.RED).build());
        PLAYER_LIST_DEFAULT = new StyledFormat(((StyleableBuilder<T, Style.Builder>)Style.builder()).color(NamedTextColor.YELLOW).build());
    }
}
