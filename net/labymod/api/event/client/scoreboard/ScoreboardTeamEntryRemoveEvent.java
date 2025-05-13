// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.scoreboard;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

public class ScoreboardTeamEntryRemoveEvent extends ScoreboardTeamUpdateEvent
{
    private final String entry;
    
    public ScoreboardTeamEntryRemoveEvent(@NotNull final ScoreboardTeam team, @NotNull final String entry) {
        super(team);
        this.entry = entry;
    }
    
    @NotNull
    public String getEntry() {
        return this.entry;
    }
}
