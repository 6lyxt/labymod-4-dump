// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.scoreboard;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.ScoreboardObjective;

@Mixin({ bhg.class })
public class MixinScoreObjective implements ScoreboardObjective
{
    @Final
    @Shadow
    private bhk a;
    @Final
    @Shadow
    private String b;
    @Shadow
    private String e;
    @Shadow
    private bhq.a d;
    private Component labyMod$title;
    
    @NotNull
    @Override
    public String getName() {
        return this.b;
    }
    
    @NotNull
    @Override
    public Component getTitle() {
        if (this.labyMod$title == null) {
            this.labyMod$title = LegacyComponentSerializer.legacySection().deserialize(this.e);
        }
        return this.labyMod$title;
    }
    
    @NotNull
    @Override
    public ObjectiveRenderType getRenderType() {
        if (this.d == null || this.d == bhq.a.a) {
            return ObjectiveRenderType.INTEGER;
        }
        return ObjectiveRenderType.HEARTS;
    }
    
    @NotNull
    @Override
    public ScoreboardScore getOrCreateScore(@NotNull final String entry) {
        return (ScoreboardScore)this.a.c(entry, (bhg)this);
    }
    
    @Nullable
    @Override
    public ScoreboardScore getScore(@NotNull final String entry) {
        final Map<bhg, bhi> scores = this.a.c(entry);
        return (scores != null) ? ((ScoreboardScore)scores.get(this)) : null;
    }
    
    @Inject(method = { "setDisplayName" }, at = { @At("HEAD") })
    public void labyMod$resetCachedTitle(final String p_setDisplayName_1_, final CallbackInfo ci) {
        this.labyMod$title = null;
    }
}
