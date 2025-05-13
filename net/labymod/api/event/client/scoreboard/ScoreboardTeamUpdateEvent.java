// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.Event;

public class ScoreboardTeamUpdateEvent implements Event
{
    private final ScoreboardTeam team;
    
    public ScoreboardTeamUpdateEvent(@NotNull final ScoreboardTeam team) {
        this.team = team;
    }
    
    @NotNull
    public ScoreboardTeam team() {
        return this.team;
    }
}
