// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.scoreboard;

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
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.Scoreboard;

@Mixin({ eim.class })
@Implements({ @Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreboard implements Scoreboard
{
    private final Map<String, List<String>> labyMod$entriesByObjective;
    @Final
    @Shadow
    private Map<String, eik> g;
    @Final
    @Shadow
    private Map<String, eik> f;
    @Shadow
    @Final
    private Map<String, eij> b;
    @Shadow
    @Final
    private Map<String, Map<eij, eil>> d;
    
    public MixinScoreboard() {
        this.labyMod$entriesByObjective = (Map<String, List<String>>)new Object2ObjectOpenHashMap();
    }
    
    @Inject(method = { "addPlayerToTeam" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void addPlayerToTeam(final String entry, final eik team, final CallbackInfoReturnable<Boolean> callback) {
        eqv.O().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam)team, entry)));
    }
    
    @Shadow
    public abstract Collection<eil> i(final eij p0);
    
    @Shadow
    public abstract eij shadow$b(@Nullable final String p0);
    
    @Shadow
    public abstract eij a(final eii p0);
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false) }, cancellable = true)
    private void fixServerSideExceptionSpam(final String $$0, final eik $$1, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void removePlayerFromTeam(final String entry, final eik team, final CallbackInfo callback) {
        eqv.O().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam)team, entry)));
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast(this.f.values());
    }
    
    @Override
    public ScoreboardTeam teamByEntry(@NotNull final String entry) {
        return (ScoreboardTeam)this.g.get(entry);
    }
    
    @Nullable
    @Override
    public ScoreboardObjective getObjective(@NotNull final DisplaySlot slot) {
        final eii rawSlot = switch (slot) {
            default -> throw new MatchException(null, null);
            case PLAYER_LIST -> eii.a;
            case SIDEBAR -> eii.b;
            case BELOW_NAME -> eii.c;
            case OTHER -> eii.s;
        };
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
        final eij objective = this.b.get(scoreboardObjective.getName());
        if (objective == null) {
            return (Collection<String>)List.of();
        }
        final List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> new ArrayList());
        entries.clear();
        for (final Map.Entry<String, Map<eij, eil>> entry : this.d.entrySet()) {
            final Map<eij, eil> value = entry.getValue();
            if (value.containsKey(objective)) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardScore> getScores(final ScoreboardObjective objective) {
        return CastUtil.cast(this.i((eij)objective));
    }
    
    @Insert(method = { "onObjectiveChanged" }, at = @At("TAIL"))
    private void onObjectiveChanged(final eij objective, final InsertInfo callback) {
        eqv.O().execute(() -> Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective)objective)));
    }
    
    @Insert(method = { "onScoreChanged" }, at = @At("TAIL"))
    private void onScoreChanged(final eil score, final InsertInfo callback) {
        eqv.O().execute(() -> Laby.fireEvent(new ScoreboardScoreUpdateEvent((ScoreboardScore)score, (ScoreboardObjective)score.d())));
    }
    
    @Insert(method = { "onObjectiveRemoved" }, at = @At("TAIL"))
    private void onObjectiveRemoved(final eij objective, final InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
