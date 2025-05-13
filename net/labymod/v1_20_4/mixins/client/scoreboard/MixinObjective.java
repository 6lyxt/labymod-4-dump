// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.scoreboard;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.core.client.scoreboard.DefaultScoreboardScore;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardObjective;

@Mixin({ emp.class })
public class MixinObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private emx a;
    @Final
    @Shadow
    private String b;
    @Shadow
    private ena.a f;
    @Shadow
    private vf d;
    @Shadow
    private wv h;
    
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
        final emv scoreAccess = this.a.c(emw.d(entry), (emp)this);
        return new DefaultScoreboardScore(this.getName(), scoreAccess.a(), Laby.references().componentMapper().fromMinecraftComponent(scoreAccess.g()), NumberFormat.noStyle());
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final emt scoreInfo = this.a.d(emw.d(entry), (emp)this);
        if (scoreInfo == null) {
            return null;
        }
        final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
        return new DefaultScoreboardScore(this.getName(), scoreInfo.a(), null, (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(scoreInfo.c()));
    }
    
    @Nullable
    @Override
    public NumberFormat getNumberFormat() {
        if (this.h == null) {
            return null;
        }
        final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
        return (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(this.h);
    }
}
