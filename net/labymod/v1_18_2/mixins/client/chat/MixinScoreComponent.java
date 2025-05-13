// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.ScoreComponent;

@Mixin({ qs.class })
@Implements({ @Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreComponent extends MixinBaseComponent<ScoreComponent> implements ScoreComponent
{
    @Shadow
    @Final
    @Mutable
    private String e;
    @Shadow
    @Final
    @Mutable
    private String g;
    @Shadow
    @Final
    @Nullable
    private fp f;
    
    @Shadow
    public abstract qs shadow$k();
    
    @Intrinsic
    public ScoreComponent scoreComponent$plainCopy() {
        return (ScoreComponent)this.shadow$k();
    }
    
    @Intrinsic
    public String scoreComponent$getName() {
        return this.e;
    }
    
    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.g;
    }
    
    @Override
    public ScoreComponent name(final String name) {
        this.e = name;
        return this;
    }
    
    @Override
    public ScoreComponent objective(final String objective) {
        this.g = objective;
        return this;
    }
    
    @Override
    public ScoreboardObjective getScoreboardObjective() {
        final Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        return (scoreboard != null) ? scoreboard.getObjective(this.g) : null;
    }
    
    @Override
    public Component value() {
        final ems level = dyr.D().r;
        if (level != null) {
            final dqm scoreboard = level.J();
            final dqj objective = scoreboard.d(this.g);
            if (objective != null && scoreboard.b(this.e, objective)) {
                final dql score = scoreboard.c(this.e, objective);
                return Component.text(score.b());
            }
        }
        return Component.empty();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.c(), this.a, this.e, this.g);
    }
}
