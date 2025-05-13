// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.scoreboard;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.client.scoreboard.ScoreboardScoreUpdateEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.scoreboard.ScoreboardObjectiveUpdateEvent;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.core.client.scoreboard.DefaultScoreboardScore;
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
import java.util.Collection;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.scoreboard.Scoreboard;

@Mixin({ fcg.class })
@Implements({ @Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreboard implements Scoreboard
{
    private final Map<String, List<String>> labyMod$entriesByObjective;
    @Final
    @Shadow
    private Object2ObjectMap<String, fcb> h;
    @Final
    @Shadow
    private Object2ObjectMap<String, fcb> g;
    @Shadow
    @Final
    private Map<String, fca> e;
    @Shadow
    @Final
    private Object2ObjectMap<String, fby> c;
    
    public MixinScoreboard() {
        this.labyMod$entriesByObjective = (Map<String, List<String>>)new Object2ObjectOpenHashMap();
    }
    
    @Inject(method = { "addPlayerToTeam" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void addPlayerToTeam(final String entry, final fcb team, final CallbackInfoReturnable<Boolean> callback) {
        flk.Q().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam)team, entry)));
    }
    
    @Shadow
    public abstract fby shadow$a(@Nullable final String p0);
    
    @Shadow
    public abstract fby a(final fbx p0);
    
    @Shadow
    public abstract Object2IntMap<fby> c(final fcf p0);
    
    @Shadow
    public abstract Collection<fbz> i(final fby p0);
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false) }, cancellable = true)
    private void fixServerSideExceptionSpam(final String $$0, final fcb $$1, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void removePlayerFromTeam(final String entry, final fcb team, final CallbackInfo callback) {
        flk.Q().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam)team, entry)));
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardTeam> getTeams() {
        return CastUtil.cast((Collection<?>)this.g.values());
    }
    
    @Override
    public ScoreboardTeam teamByEntry(@NotNull final String entry) {
        return (ScoreboardTeam)this.h.get((Object)entry);
    }
    
    @Nullable
    @Override
    public ScoreboardObjective getObjective(@NotNull final DisplaySlot slot) {
        final fbx rawSlot = switch (slot) {
            default -> throw new MatchException(null, null);
            case PLAYER_LIST -> fbx.a;
            case SIDEBAR -> fbx.b;
            case BELOW_NAME -> fbx.c;
            case OTHER -> fbx.s;
        };
        return (ScoreboardObjective)this.a(rawSlot);
    }
    
    @Intrinsic
    @Nullable
    public ScoreboardObjective labyMod$getObjective(@NotNull final String objective) {
        return (ScoreboardObjective)this.shadow$a(objective);
    }
    
    @NotNull
    @Override
    public Collection<String> getEntries(final ScoreboardObjective scoreboardObjective) {
        final fby objective = (fby)this.c.get((Object)scoreboardObjective.getName());
        if (objective == null) {
            return (Collection<String>)List.of();
        }
        final List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> new ArrayList());
        entries.clear();
        for (final Map.Entry<String, fca> entry : this.e.entrySet()) {
            final fca value = entry.getValue();
            if (value.a(objective) != null) {
                entries.add(entry.getKey());
            }
        }
        return entries;
    }
    
    @NotNull
    @Override
    public Collection<ScoreboardScore> getScores(final ScoreboardObjective objective) {
        final List<ScoreboardScore> scores = new ArrayList<ScoreboardScore>();
        for (final Map.Entry<String, fca> entry : this.e.entrySet()) {
            final fca playerScores = entry.getValue();
            final fcd score = playerScores.a((fby)objective);
            if (score == null) {
                continue;
            }
            final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            scores.add(new DefaultScoreboardScore(entry.getKey(), score.a(), Laby.references().componentMapper().fromMinecraftComponent(score.d()), (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(score.c())));
        }
        return scores;
    }
    
    @Insert(method = { "onObjectiveChanged" }, at = @At("TAIL"))
    private void onObjectiveChanged(final fby objective, final InsertInfo callback) {
        flk.Q().execute(() -> Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective)objective)));
    }
    
    @Insert(method = { "onScoreChanged" }, at = @At("TAIL"))
    private void onScoreChanged(final fcf holder, final fby objective, final fcd score, final InsertInfo callback) {
        flk.Q().execute(() -> {
            final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            final ComponentMapper componentMapper = Laby.references().componentMapper();
            final Component mappedComponent = componentMapper.fromMinecraftComponent(holder.hg());
            new DefaultScoreboardScore(holder.cI(), score.a(), mappedComponent, (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(score.c()));
            final DefaultScoreboardScore defaultScoreboardScore;
            final ScoreboardScore scoreboardScore = defaultScoreboardScore;
            Laby.fireEvent(new ScoreboardScoreUpdateEvent(scoreboardScore, (ScoreboardObjective)objective));
        });
    }
    
    @Insert(method = { "onObjectiveRemoved" }, at = @At("TAIL"))
    private void onObjectiveRemoved(final fby objective, final InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
