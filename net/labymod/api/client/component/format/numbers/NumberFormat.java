// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format.numbers;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.Component;

public interface NumberFormat
{
    default BlankFormat blank() {
        return BlankFormat.INSTANCE;
    }
    
    default FixedFormat fixed(final Component value) {
        return new FixedFormat(value);
    }
    
    default StyledFormat noStyle() {
        return StyledFormat.NO_STYLE;
    }
    
    default StyledFormat sidebarDefault() {
        return StyledFormat.SIDEBAR_DEFAULT;
    }
    
    default StyledFormat playerListDefault() {
        return StyledFormat.PLAYER_LIST_DEFAULT;
    }
    
    default StyledFormat styled(final Style style) {
        return new StyledFormat(style);
    }
    
    Component format(final int p0);
}
