// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.scoreboard;

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

@Mixin({ ewx.class })
@Implements({ @Interface(iface = Scoreboard.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinScoreboard implements Scoreboard
{
    private final Map<String, List<String>> labyMod$entriesByObjective;
    @Final
    @Shadow
    private Object2ObjectMap<String, ews> h;
    @Final
    @Shadow
    private Object2ObjectMap<String, ews> g;
    @Shadow
    @Final
    private Map<String, ewr> e;
    @Shadow
    @Final
    private Object2ObjectMap<String, ewp> c;
    
    public MixinScoreboard() {
        this.labyMod$entriesByObjective = (Map<String, List<String>>)new Object2ObjectOpenHashMap();
    }
    
    @Inject(method = { "addPlayerToTeam" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void addPlayerToTeam(final String entry, final ews team, final CallbackInfoReturnable<Boolean> callback) {
        ffh.Q().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryAddEvent((ScoreboardTeam)team, entry)));
    }
    
    @Shadow
    public abstract ewp shadow$a(@Nullable final String p0);
    
    @Shadow
    public abstract ewp a(final ewo p0);
    
    @Shadow
    public abstract Object2IntMap<ewp> c(final eww p0);
    
    @Shadow
    public abstract Collection<ewq> i(final ewp p0);
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", shift = At.Shift.BEFORE, remap = false) }, cancellable = true)
    private void fixServerSideExceptionSpam(final String $$0, final ews $$1, final CallbackInfo ci) {
        ci.cancel();
    }
    
    @Inject(method = { "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V" }, at = { @At(value = "INVOKE", target = "Ljava/util/Collection;remove(Ljava/lang/Object;)Z", shift = At.Shift.AFTER) })
    private void removePlayerFromTeam(final String entry, final ews team, final CallbackInfo callback) {
        ffh.Q().execute(() -> Laby.fireEvent(new ScoreboardTeamEntryRemoveEvent((ScoreboardTeam)team, entry)));
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
        final ewo rawSlot = switch (slot) {
            default -> throw new MatchException(null, null);
            case PLAYER_LIST -> ewo.a;
            case SIDEBAR -> ewo.b;
            case BELOW_NAME -> ewo.c;
            case OTHER -> ewo.s;
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
        final ewp objective = (ewp)this.c.get((Object)scoreboardObjective.getName());
        if (objective == null) {
            return (Collection<String>)List.of();
        }
        final List<String> entries = this.labyMod$entriesByObjective.computeIfAbsent(scoreboardObjective.getName(), obj -> new ArrayList());
        entries.clear();
        for (final Map.Entry<String, ewr> entry : this.e.entrySet()) {
            final ewr value = entry.getValue();
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
        for (final Map.Entry<String, ewr> entry : this.e.entrySet()) {
            final ewr playerScores = entry.getValue();
            final ewu score = playerScores.a((ewp)objective);
            if (score == null) {
                continue;
            }
            final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            scores.add(new DefaultScoreboardScore(entry.getKey(), score.a(), Laby.references().componentMapper().fromMinecraftComponent(score.d()), (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(score.c())));
        }
        return scores;
    }
    
    @Insert(method = { "onObjectiveChanged" }, at = @At("TAIL"))
    private void onObjectiveChanged(final ewp objective, final InsertInfo callback) {
        ffh.Q().execute(() -> Laby.fireEvent(new ScoreboardObjectiveUpdateEvent((ScoreboardObjective)objective)));
    }
    
    @Insert(method = { "onScoreChanged" }, at = @At("TAIL"))
    private void onScoreChanged(final eww holder, final ewp objective, final ewu score, final InsertInfo callback) {
        ffh.Q().execute(() -> {
            final NumberFormatMapper numberFormatMapper = Laby.references().getNumberFormatMapper();
            final ComponentMapper componentMapper = Laby.references().componentMapper();
            final Component mappedComponent = componentMapper.fromMinecraftComponent(holder.he());
            new DefaultScoreboardScore(holder.cB(), score.a(), mappedComponent, (numberFormatMapper == null) ? null : numberFormatMapper.fromMinecraft(score.c()));
            final DefaultScoreboardScore defaultScoreboardScore;
            final ScoreboardScore scoreboardScore = defaultScoreboardScore;
            Laby.fireEvent(new ScoreboardScoreUpdateEvent(scoreboardScore, (ScoreboardObjective)objective));
        });
    }
    
    @Insert(method = { "onObjectiveRemoved" }, at = @At("TAIL"))
    private void onObjectiveRemoved(final ewp objective, final InsertInfo callback) {
        this.labyMod$entriesByObjective.remove(objective.b());
    }
}
