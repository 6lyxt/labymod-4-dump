// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.scoreboard;

import java.util.Objects;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.scoreboard.ScoreboardScore;

record DefaultScoreboardScore(String owner, int value, @Nullable Component displayName, @Nullable NumberFormat format) implements ScoreboardScore {
    @Override
    public String getName() {
        return this.owner;
    }
    
    @Override
    public int getValue() {
        return this.value;
    }
    
    @Override
    public Component getOwnerName() {
        return (this.displayName == null) ? Component.text(this.owner) : this.displayName;
    }
    
    @Override
    public Component formatValue(final NumberFormat format) {
        return Objects.requireNonNullElse(this.format, format).format(this.value);
    }
    
    @Nullable
    public Component displayName() {
        return this.displayName;
    }
    
    @Nullable
    public NumberFormat format() {
        return this.format;
    }
}
