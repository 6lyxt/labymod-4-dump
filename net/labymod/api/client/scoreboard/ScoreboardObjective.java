// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import java.util.Objects;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

public interface ScoreboardObjective
{
    @NotNull
    String getName();
    
    @NotNull
    Component getTitle();
    
    @NotNull
    ObjectiveRenderType getRenderType();
    
    @NotNull
    ScoreboardScore getOrCreateScore(@NotNull final String p0);
    
    @Nullable
    ScoreboardScore getScore(@NotNull final String p0);
    
    @Nullable
    default NumberFormat getNumberFormat() {
        return NumberFormat.sidebarDefault();
    }
    
    default NumberFormat getNumberFormatOrDefault(final NumberFormat defaultFormat) {
        NumberFormat numberFormat;
        try {
            numberFormat = this.getNumberFormat();
        }
        catch (final IllegalStateException exception) {
            numberFormat = defaultFormat;
        }
        return Objects.requireNonNullElse(numberFormat, defaultFormat);
    }
}
