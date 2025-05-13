// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network;

import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import net.labymod.api.client.entity.player.GameMode;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.PlayerListUser;
import java.util.Comparator;
import net.labymod.api.client.network.NetworkPlayerInfo;

public abstract class DefaultNetworkPlayerInfo implements NetworkPlayerInfo
{
    private static final Comparator<NetworkPlayerInfo> PLAYER_COMPARATOR;
    public static final Comparator<PlayerListUser> PLAYER_LIST_COMPARATOR;
    
    @Override
    public int getHealth() {
        final Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        if (scoreboard == null) {
            return 0;
        }
        final ScoreboardObjective objective = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
        if (objective == null) {
            return 0;
        }
        return objective.getOrCreateScore(this.profile().getUsername()).getValue();
    }
    
    @Override
    public ScoreboardTeam getTeam() {
        final Scoreboard scoreboard = Laby.labyAPI().minecraft().getScoreboard();
        return (scoreboard != null) ? scoreboard.teamByEntry(this.profile().getUsername()) : null;
    }
    
    @Override
    public int compareTo(@NotNull final NetworkPlayerInfo p2) {
        return DefaultNetworkPlayerInfo.PLAYER_COMPARATOR.compare(this, p2);
    }
    
    private static <T> Comparator<T> createComparator(final Function<T, Integer> orderGetter, final Function<T, GameMode> gameModeGetter, final Function<T, ScoreboardTeam> teamGetter, final Function<T, String> usernameGetter, @Nullable final Function<T, UUID> uuidGetter) {
        Comparator<T> comparator = Comparator.comparingInt(value -> -orderGetter.apply(value)).thenComparing(value -> Integer.valueOf((gameModeGetter.apply(value) == GameMode.SPECTATOR) ? 1 : 0)).thenComparing(value -> {
            final ScoreboardTeam team = teamGetter.apply(value);
            return (team == null) ? "" : team.getTeamName();
        }).thenComparing((Function<? super T, ?>)usernameGetter, String::compareToIgnoreCase);
        if (uuidGetter != null) {
            comparator = comparator.thenComparing((Function<? super T, ?>)uuidGetter, UUID::compareTo);
        }
        return comparator;
    }
    
    static {
        PLAYER_COMPARATOR = createComparator(NetworkPlayerInfo::getOrder, NetworkPlayerInfo::gameMode, NetworkPlayerInfo::getTeam, networkPlayerInfo -> networkPlayerInfo.profile().getUsername(), (Function<NetworkPlayerInfo, UUID>)null);
        PLAYER_LIST_COMPARATOR = createComparator(user -> user.playerInfo().getOrder(), user -> user.playerInfo().gameMode(), PlayerListUser::getTeam, PlayerListUser::getUserName, PlayerListUser::getUniqueId);
    }
}
