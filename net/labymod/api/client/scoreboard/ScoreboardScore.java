// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.component.Component;

public interface ScoreboardScore
{
    default boolean isHidden() {
        final String name = this.getName();
        return name == null || name.startsWith("#");
    }
    
    String getName();
    
    int getValue();
    
    default Component getOwnerName() {
        return Component.text(this.getName());
    }
    
    default Component formatValue(final NumberFormat format) {
        return format.format(this.getValue());
    }
    
    default Component formatValue(final ScoreboardScore score, final NumberFormat format) {
        return (score == null) ? format.format(0) : score.formatValue(format);
    }
}
