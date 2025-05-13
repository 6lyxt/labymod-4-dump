// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.scoreboard;

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

@Mixin({ ddk.class })
public class MixinObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private ddn a;
    @Final
    @Shadow
    private String b;
    @Shadow
    private ddq.a f;
    @Shadow
    private nr d;
    
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
        switch (this.f) {
            case a: {
                return ObjectiveRenderType.INTEGER;
            }
            case b: {
                return ObjectiveRenderType.HEARTS;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.f));
            }
        }
    }
    
    @NotNull
    @Override
    public ScoreboardScore getOrCreateScore(@NotNull final String entry) {
        return (ScoreboardScore)this.a.c(entry, (ddk)this);
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final Map<ddk, ddm> scores = this.a.e(entry);
        return (scores != null) ? ((ScoreboardScore)scores.get(this)) : null;
    }
}
