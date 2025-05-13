// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.scoreboard;

import net.labymod.api.event.client.scoreboard.ScoreboardScoreUpdateEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.scoreboard.ScoreboardObjectiveUpdateEvent;
import net.labymod.api.volt.callback.InsertInfo;
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
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.Scoreboard;

@Mixin({ dny.class })
@Implements({ @Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreboard implements Scoreboard
{
    private final Map<String, List<String>> labyMod$entriesByObjective;
    @Final
    @Shadow
    private Map<String, dnw> m;
    @Final
    @Shadow
    private Map<String, dnw> l;
    @Shadow
    @Final
    private Map<String, dnv> h;
    @Shadow
    @Final
    private Map<String, Map<dnv, dnx>> j;
    
    public MixinScoreboard() {
        this.labyMod$entriesByObjective = (Map<String, List<String>>)new Object2ObjectOpenHashMap();
    }
    
    @Shadow
    public abstract dnv a(final int p0);
    
    @Shadow
    public abstract Collection<dnx> i(final dnv p0);
    
    @Shadow
    public abstract dnv shadow$d(@Nullable final String p0);
    
    @Inject(method = { "addPlayerToTeam" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void addPlayerToTeam(final String entry, final dnw team, final CallbackInfoReturnable<Boolean> callback) {
        dvp.C().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam)team, entry)));
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false) }, cancellable = true)
    private void fixServerSideExceptionSpam(final String $$0, final dnw $$1, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void removePlayerFromTeam(final String entry, final dnw team, final CallbackInfo callback) {
        dvp.C().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam)team, entry)));
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast(this.l.values());
    }
    
    @Override
    public ScoreboardTeam teamByEntry(@NotNull final String entry) {
        return (ScoreboardTeam)this.m.get(entry);
    }
    
    @Nullable
    @Override
    public ScoreboardObjective getObjective(@NotNull final DisplaySlot slot) {
        final int rawSlot = switch (slot) {
            default -> throw new MatchException(null, null);
            case PLAYER_LIST -> 0;
            case SIDEBAR -> 1;
            case BELOW_NAME -> 2;
            case OTHER -> -1;
        };
        return (ScoreboardObjective)this.a(rawSlot);
    }
    
    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull final String objective) {
        return (ScoreboardObjective)this.shadow$d(objective);
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries(final ScoreboardObjective scoreboardObjective) {
        final dnv objective = this.h.get(scoreboardObjective.getName());
        if (objective == null) {
            return (Collection<String>)List.of();
        }
        final List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> new ArrayList());
        entries.clear();
        for (final Map.Entry<String, Map<dnv, dnx>> entry : this.j.entrySet()) {
            final Map<dnv, dnx> value = entry.getValue();
            if (value.containsKey(objective)) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardScore> getScores(final ScoreboardObjective objective) {
        return CastUtil.cast(this.i((dnv)objective));
    }
    
    @Insert(method = { "onObjectiveChanged" }, at = @At("TAIL"))
    private void onObjectiveChanged(final dnv objective, final InsertInfo callback) {
        dvp.C().execute(() -> Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective)objective)));
    }
    
    @Insert(method = { "onScoreChanged" }, at = @At("TAIL"))
    private void onScoreChanged(final dnx score, final InsertInfo callback) {
        dvp.C().execute(() -> Laby.fireEvent(new ScoreboardScoreUpdateEvent((ScoreboardScore)score, (ScoreboardObjective)score.d())));
    }
    
    @Insert(method = { "onObjectiveRemoved" }, at = @At("TAIL"))
    private void onObjectiveRemoved(final dnv objective, final InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
