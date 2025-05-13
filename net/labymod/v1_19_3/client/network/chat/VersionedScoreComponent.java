// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.network.chat;

import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.component.Component;
import net.labymod.v1_19_3.client.network.chat.contents.ScoreContentsAccessor;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;

public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, st> implements ScoreComponent
{
    public VersionedScoreComponent(final tf holder) {
        super(holder);
    }
    
    @Override
    public String getName() {
        final st contents = ((VersionedBaseComponent<T, st>)this).getContents();
        if (contents instanceof final tz score) {
            return score.a();
        }
        return contents.toString();
    }
    
    @Override
    public String getObjective() {
        final st contents = ((VersionedBaseComponent<T, st>)this).getContents();
        if (contents instanceof final tz score) {
            return score.c();
        }
        return contents.toString();
    }
    
    @Override
    public ScoreComponent plainCopy() {
        return ComponentService.scoreComponent(this.getName(), this.getObjective());
    }
    
    @Override
    public ScoreComponent name(final String name) {
        final ScoreContentsAccessor contents = ((VersionedBaseComponent<T, ScoreContentsAccessor>)this).getContents();
        if (contents instanceof ScoreContentsAccessor) {
            final ScoreContentsAccessor score = contents;
            score.setName(name);
        }
        return this;
    }
    
    @Override
    public ScoreComponent objective(final String objective) {
        final ScoreContentsAccessor contents = ((VersionedBaseComponent<T, ScoreContentsAccessor>)this).getContents();
        if (contents instanceof ScoreContentsAccessor) {
            final ScoreContentsAccessor score = contents;
            score.setObjective(objective);
        }
        return this;
    }
    
    @Override
    public Component value() {
        final eyz level = ejf.N().s;
        if (level != null) {
            final ebc scoreboard = level.I();
            final eaz objective = scoreboard.d(this.getObjective());
            if (objective != null) {
                final String name = this.getName();
                if (scoreboard.b(name, objective)) {
                    final ebb score = scoreboard.c(name, objective);
                    return Component.text(score.b());
                }
            }
        }
        return Component.empty();
    }
    
    @Override
    public ScoreboardObjective getScoreboardObjective() {
        final Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        return (scoreboard != null) ? scoreboard.getObjective(this.getObjective()) : null;
    }
}
