// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.scoreboard;

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

@Mixin({ ewp.class })
public class MixinObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private ewx a;
    @Final
    @Shadow
    private String b;
    @Shadow
    private exa.a f;
    @Shadow
    private xp d;
    @Shadow
    private zf h;
    
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
        final ewv scoreAccess = this.a.c(eww.c(entry), (ewp)this);
        return new DefaultScoreboardScore(this.getName(), scoreAccess.a(), Laby.references().componentMapper().fromMinecraftComponent(scoreAccess.g()), NumberFormat.noStyle());
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final ewt scoreInfo = this.a.d(eww.c(entry), (ewp)this);
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
