// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.scoreboard;

import org.jetbrains.annotations.Nullable;
import java.util.Map;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardObjective;

@Mixin({ dnv.class })
public class MixinObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private dny b;
    @Final
    @Shadow
    private String c;
    @Shadow
    private dob.a g;
    @Shadow
    private os e;
    
    @NotNull
    @Override
    public String getName() {
        return this.c;
    }
    
    @NotNull
    @Override
    public Component getTitle() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.e);
    }
    
    @NotNull
    @Override
    public ObjectiveRenderType getRenderType() {
        return switch (this.g) {
            default -> throw new MatchException(null, null);
            case b -> ObjectiveRenderType.HEARTS;
            case a -> ObjectiveRenderType.INTEGER;
        };
    }
    
    @NotNull
    @Override
    public ScoreboardScore getOrCreateScore(@NotNull final String entry) {
        return (ScoreboardScore)this.b.c(entry, (dnv)this);
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final Map<dnv, dnx> scores = this.b.e(entry);
        return (scores != null) ? ((ScoreboardScore)scores.get(this)) : null;
    }
}
