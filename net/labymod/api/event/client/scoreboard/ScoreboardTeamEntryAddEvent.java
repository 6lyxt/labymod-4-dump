// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

public class ScoreboardTeamEntryAddEvent extends ScoreboardTeamUpdateEvent
{
    private final String entry;
    
    public ScoreboardTeamEntryAddEvent(@NotNull final ScoreboardTeam team, @NotNull final String entry) {
        super(team);
        this.entry = entry;
    }
    
    @NotNull
    public String getEntry() {
        return this.entry;
    }
}
