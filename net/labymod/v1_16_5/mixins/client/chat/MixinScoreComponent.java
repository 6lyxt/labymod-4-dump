// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

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

@Mixin({ nz.class })
@Implements({ @Interface(iface = ScoreComponent.class, prefix = "scoreComponent$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreComponent extends MixinBaseComponent<ScoreComponent> implements ScoreComponent
{
    @Shadow
    @Final
    @Mutable
    private String d;
    @Shadow
    @Final
    @Mutable
    private String f;
    @Shadow
    @Final
    @Nullable
    private fc e;
    
    @Shadow
    public abstract nz shadow$k();
    
    @Intrinsic
    public ScoreComponent scoreComponent$plainCopy() {
        return (ScoreComponent)this.shadow$k();
    }
    
    @Intrinsic
    public String scoreComponent$getName() {
        return this.d;
    }
    
    @Intrinsic
    public String scoreComponent$getObjective() {
        return this.f;
    }
    
    @Override
    public ScoreComponent name(final String name) {
        this.d = name;
        return this;
    }
    
    @Override
    public ScoreComponent objective(final String objective) {
        this.f = objective;
        return this;
    }
    
    @Override
    public ScoreboardObjective getScoreboardObjective() {
        final Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        return (scoreboard != null) ? scoreboard.getObjective(this.f) : null;
    }
    
    @Override
    public Component value() {
        final dwt level = djz.C().r;
        if (level != null) {
            final ddn scoreboard = level.G();
            final ddk objective = scoreboard.d(this.f);
            if (objective != null && scoreboard.b(this.d, objective)) {
                final ddm score = scoreboard.c(this.d, objective);
                return Component.text(score.b());
            }
        }
        return Component.empty();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.c(), this.a, this.d, this.f);
    }
}
