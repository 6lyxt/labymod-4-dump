// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.scoreboard;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.event.client.scoreboard.ScoreboardScoreUpdateEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardObjectiveUpdateEvent;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import java.util.Iterator;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.CastUtil;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryRemoveEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.Scoreboard;

@Mixin({ bhk.class })
@Implements({ @Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreboard implements Scoreboard
{
    private final Map<String, List<String>> labyMod$entriesByObjective;
    @Final
    @Shadow
    private Map<String, bhh> e;
    @Final
    @Shadow
    private Map<String, bhh> f;
    @Shadow
    @Final
    private Map<String, bhg> a;
    @Shadow
    @Final
    private Map<String, Map<bhg, bhi>> c;
    
    public MixinScoreboard() {
        this.labyMod$entriesByObjective = (Map<String, List<String>>)new Object2ObjectOpenHashMap();
    }
    
    @Shadow
    public abstract bhg a(final int p0);
    
    @Shadow
    public abstract bhg shadow$b(@Nullable final String p0);
    
    @Shadow
    public abstract bhh d(final String p0);
    
    @Shadow
    public abstract Collection<bhi> i(final bhg p0);
    
    @Inject(method = { "addPlayerToTeam(Ljava/lang/String;Ljava/lang/String;)Z" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER, remap = false) })
    private void addPlayerToTeam(final String entry, final String teamName, final CallbackInfoReturnable<Boolean> callback) {
        final bhm team = (bhm)this.d(teamName);
        if (team != null) {
            bib.z().a(() -> Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam)team, entry)));
        }
    }
    
    @Inject(method = { "removePlayerFromTeam" }, at = { @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false) }, cancellable = true)
    private void fixServerSideExceptionSpam(final String lvt_1_1_, final bhh lvt_2_1_, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/scoreboard/ScorePlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER, remap = false) })
    private void removePlayerFromTeam(final String entry, final bhh team, final CallbackInfo callback) {
        bib.z().a(() -> Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam)team, entry)));
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast(this.e.values());
    }
    
    @NotNull
    @Override
    public ScoreboardTeam teamByEntry(@NotNull final String entry) {
        return (ScoreboardTeam)this.f.get(entry);
    }
    
    @Nullable
    @Override
    public ScoreboardObjective getObjective(@NotNull final DisplaySlot slot) {
        int rawSlot = 0;
        switch (slot) {
            case PLAYER_LIST: {
                rawSlot = 0;
                break;
            }
            case SIDEBAR: {
                rawSlot = 1;
                break;
            }
            case BELOW_NAME: {
                rawSlot = 2;
                break;
            }
            case OTHER: {
                rawSlot = -1;
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(slot));
            }
        }
        return (ScoreboardObjective)this.a(rawSlot);
    }
    
    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull final String objective) {
        return (ScoreboardObjective)this.shadow$b(objective);
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries(final ScoreboardObjective scoreboardObjective) {
        final bhg objective = this.a.get(scoreboardObjective.getName());
        if (objective == null) {
            return (Collection<String>)List.of();
        }
        final List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> new ArrayList());
        entries.clear();
        for (final Map.Entry<String, Map<bhg, bhi>> entry : this.c.entrySet()) {
            final Map<bhg, bhi> value = entry.getValue();
            if (value.containsKey(objective)) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardScore> getScores(final ScoreboardObjective objective) {
        return CastUtil.cast(this.i((bhg)objective));
    }
    
    @Inject(method = { "onObjectiveDisplayNameChanged(Lnet/minecraft/scoreboard/ScoreObjective;)V" }, at = { @At("TAIL") })
    private void onObjectiveChanged(final bhg objective, final CallbackInfo callback) {
        bib.z().a(() -> Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective)objective)));
    }
    
    @Inject(method = { "onScoreUpdated(Lnet/minecraft/scoreboard/Score;)V" }, at = { @At("TAIL") })
    private void onScoreChanged(final bhi score, final CallbackInfo callback) {
        bib.z().a(() -> Laby.fireEvent(new ScoreboardScoreUpdateEvent((ScoreboardScore)score, (ScoreboardObjective)score.d())));
    }
    
    @Insert(method = { "onScoreObjectiveRemoved" }, at = @At("TAIL"))
    private void onObjectiveRemoved(final bhg objective, final InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
