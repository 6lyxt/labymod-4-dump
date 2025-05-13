// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.network.chat;

import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.component.Component;
import net.labymod.v1_20_5.client.network.chat.contents.ScoreContentsAccessor;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ScoreComponent;

public class VersionedScoreComponent extends VersionedBaseComponent<ScoreComponent, xq> implements ScoreComponent
{
    public VersionedScoreComponent(final yd holder) {
        super(holder);
    }
    
    @Override
    public String getName() {
        final xq contents = ((VersionedBaseComponent<T, xq>)this).getContents();
        if (contents instanceof final yx score) {
            return score.b();
        }
        return contents.toString();
    }
    
    @Override
    public String getObjective() {
        final xq contents = ((VersionedBaseComponent<T, xq>)this).getContents();
        if (contents instanceof final yx score) {
            return score.d();
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
        final fxw level = ffg.Q().r;
        if (level != null) {
            final eww scoreboard = level.M();
            final ewo objective = scoreboard.a(this.getObjective());
            if (objective != null) {
                final String name = this.getName();
                final ewv scoreHolder = ewv.c(name);
                final ews scoreInfo = scoreboard.d(scoreHolder, objective);
                if (scoreInfo != null) {
                    return (Component)scoreInfo.a(objective.a((zf)zi.b));
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
