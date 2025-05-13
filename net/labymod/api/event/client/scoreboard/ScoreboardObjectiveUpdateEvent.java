// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.event.Event;

public class ScoreboardObjectiveUpdateEvent implements Event
{
    private final ScoreboardObjective objective;
    
    public ScoreboardObjectiveUpdateEvent(@NotNull final ScoreboardObjective objective) {
        this.objective = objective;
    }
    
    @NotNull
    public ScoreboardObjective objective() {
        return this.objective;
    }
}
