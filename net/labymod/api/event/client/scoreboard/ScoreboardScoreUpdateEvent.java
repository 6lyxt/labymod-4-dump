// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.event.Event;

public class ScoreboardScoreUpdateEvent implements Event
{
    private final ScoreboardScore score;
    private final ScoreboardObjective objective;
    
    public ScoreboardScoreUpdateEvent(@NotNull final ScoreboardScore score, @NotNull final ScoreboardObjective objective) {
        this.score = score;
        this.objective = objective;
    }
    
    @NotNull
    public ScoreboardScore score() {
        return this.score;
    }
    
    @NotNull
    public ScoreboardObjective objective() {
        return this.objective;
    }
}
