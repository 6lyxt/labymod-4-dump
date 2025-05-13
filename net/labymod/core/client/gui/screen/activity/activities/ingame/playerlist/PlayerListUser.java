// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.network.ClientPacketListener;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.component.Component;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;

public class PlayerListUser extends CachedComponent
{
    private static final String ACTUAL_PLAYER_KEY = "actual_player_name";
    public final UUID uniqueId;
    private final String userName;
    private final NetworkPlayerInfo playerInfo;
    public int x;
    public int y;
    private String actualUserName;
    private UUID actualUniqueId;
    private NetworkPlayerInfo actualNetworkPlayerInfo;
    private ScoreboardTeam team;
    private String identifier;
    private GameUser gameUser;
    private boolean spectator;
    
    protected PlayerListUser(final NetworkPlayerInfo info) {
        this.playerInfo = info;
        this.team = info.getTeam();
        final GameProfile profile = info.profile();
        this.userName = profile.getUsername();
        this.uniqueId = profile.getUniqueId();
    }
    
    @Override
    public Component component() {
        if (this.component == null) {
            this.component = this.loadComponent();
        }
        return super.component();
    }
    
    public boolean updateTeam(final ScoreboardTeam team) {
        if (this.team != null && this.team.equals(team)) {
            return false;
        }
        this.team = team;
        this.identifier = null;
        this.invalidate();
        return true;
    }
    
    public void update(final float maxWidth) {
        if (this.isEmpty()) {
            this.update();
        }
    }
    
    public void refreshComponent(final float maxWidth) {
        if (this.isEmpty()) {
            return;
        }
        if (this.renderableComponent == null || this.renderableComponent.getWidth() > maxWidth) {
            this.invalidate();
        }
    }
    
    public Component loadComponent() {
        Component displayName = this.playerInfo.displayName();
        this.spectator = (this.playerInfo.gameMode() == GameMode.SPECTATOR);
        if (this.spectator) {
            displayName = displayName.copy().decorate(TextDecoration.ITALIC);
        }
        final PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(PlayerNameTagRenderEvent.Context.TAB_LIST, this.playerInfo, displayName, TagType.MAIN_TAG);
        Laby.fireEvent(event);
        if (!Objects.equals(displayName, event.nameTag())) {
            displayName = event.nameTag().append(PlayerNameTagRenderEvent.EDITED_COMPONENT);
        }
        return displayName;
    }
    
    public boolean isSpectator() {
        return this.spectator;
    }
    
    public String getIdentifier() {
        if (this.identifier == null) {
            if (this.team == null) {
                this.identifier = this.userName;
            }
            else {
                this.identifier = this.team.getTeamName() + this.userName;
            }
        }
        return this.identifier;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public NetworkPlayerInfo playerInfo() {
        this.refreshActualUser();
        if (this.actualNetworkPlayerInfo != null) {
            return this.actualNetworkPlayerInfo;
        }
        return this.playerInfo;
    }
    
    public ScoreboardTeam getTeam() {
        return this.team;
    }
    
    public GameUser gameUser() {
        if (this.gameUser == null) {
            this.gameUser = Laby.references().gameUserService().gameUser(this.getUniqueId());
        }
        return this.gameUser;
    }
    
    public UUID getUniqueId() {
        if (this.actualUniqueId != null) {
            return this.actualUniqueId;
        }
        return this.uniqueId;
    }
    
    private UUID refreshActualUser() {
        final String actualUserName = this.playerInfo.metadata().get("actual_player_name");
        if (actualUserName == null) {
            this.resetActualUser();
            return this.uniqueId;
        }
        if (actualUserName.equals(this.actualUserName)) {
            return this.actualUniqueId;
        }
        final ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null) {
            this.resetActualUser();
            return this.uniqueId;
        }
        final NetworkPlayerInfo playerInfo = clientPacketListener.getNetworkPlayerInfo(actualUserName);
        if (playerInfo == null) {
            this.resetActualUser();
            return this.uniqueId;
        }
        this.actualUserName = actualUserName;
        this.actualUniqueId = playerInfo.profile().getUniqueId();
        this.actualNetworkPlayerInfo = playerInfo;
        this.gameUser = null;
        return this.actualUniqueId;
    }
    
    private void resetActualUser() {
        if (this.actualUniqueId == null) {
            return;
        }
        this.actualUserName = null;
        this.actualUniqueId = null;
        this.actualNetworkPlayerInfo = null;
        this.gameUser = null;
    }
}
