// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.scoreboard;

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

@Mixin({ edz.class })
public class MixinObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private eec a;
    @Final
    @Shadow
    private String b;
    @Shadow
    private eef.a f;
    @Shadow
    private tj d;
    
    @NotNull
    @Override
    public String getName() {
        return this.b;
    }
    
    @NotNull
    @Override
    public Component getTitle() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(this.d);
    }
    
    @NotNull
    @Override
    public ObjectiveRenderType getRenderType() {
        return switch (this.f) {
            default -> throw new MatchException(null, null);
            case b -> ObjectiveRenderType.HEARTS;
            case a -> ObjectiveRenderType.INTEGER;
        };
    }
    
    @NotNull
    @Override
    public ScoreboardScore getOrCreateScore(@NotNull final String entry) {
        return (ScoreboardScore)this.a.c(entry, (edz)this);
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final Map<edz, eeb> scores = this.a.e(entry);
        return (scores != null) ? ((ScoreboardScore)scores.get(this)) : null;
    }
}
