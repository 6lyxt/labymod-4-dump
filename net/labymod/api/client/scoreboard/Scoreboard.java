// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

public interface Scoreboard
{
    @NotNull
    Collection<ScoreboardTeam> getTeams();
    
    @Nullable
    ScoreboardTeam teamByEntry(@NotNull final String p0);
    
    @Deprecated
    @Nullable
    default ScoreboardObjective objective(@NotNull final DisplaySlot slot) {
        return this.getObjective(slot);
    }
    
    @Nullable
    ScoreboardObjective getObjective(@NotNull final DisplaySlot p0);
    
    @Nullable
    ScoreboardObjective getObjective(@NotNull final String p0);
    
    @NotNull
    Collection<ScoreboardScore> getScores(final ScoreboardObjective p0);
    
    @NotNull
    Collection<String> getEntries(final ScoreboardObjective p0);
}
