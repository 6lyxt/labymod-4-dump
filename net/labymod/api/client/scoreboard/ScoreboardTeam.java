// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.scoreboard;

import net.labymod.api.client.component.Component;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public interface ScoreboardTeam
{
    @NotNull
    String getTeamName();
    
    @NotNull
    Collection<String> getEntries();
    
    boolean hasEntry(@NotNull final String p0);
    
    @NotNull
    Component getPrefix();
    
    @NotNull
    Component getSuffix();
    
    @NotNull
    Component formatDisplayName(@NotNull final Component p0);
}
