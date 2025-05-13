// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model;

import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.entity.player.Player;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.model.camera.CameraInsight;
import net.labymod.core.labynet.insight.model.player.PlayerInsight;
import java.util.List;
import net.labymod.core.labynet.insight.Insight;

public class ScreenshotInsight implements Insight
{
    private final List<PlayerInsight> players;
    private CameraInsight camera;
    private WindowInsight window;
    private ServerInsight server;
    
    public ScreenshotInsight(final JsonObject object) {
        this.players = new ArrayList<PlayerInsight>();
        this.fromJsonObject(object);
    }
    
    public ScreenshotInsight() {
        this.players = new ArrayList<PlayerInsight>();
    }
    
    public void addPlayer(final Player player, final float partialTicks) {
        final PlayerInsight playerMeta = new PlayerInsight(player, partialTicks);
        if (this.camera != null && this.window != null) {
            final Position position = player.position();
            final Position previousPosition = player.previousPosition();
            final DoubleVector3 playerPosition = new DoubleVector3(position.lerpX(previousPosition, partialTicks), position.lerpY(previousPosition, partialTicks), position.lerpZ(previousPosition, partialTicks));
            final DoubleVector3 vector = this.camera.getPosition().copy().sub(playerPosition);
            vector.transform(this.camera.getRotation().copy().conj());
            final float halfWidth = this.window.getWidth() / 2.0f;
            final float halfHeight = this.window.getHeight() / 2.0f;
            final double tan = Math.tan(Math.toRadians(this.camera.getFov() / 2.0));
            final double scaleFactor = halfHeight / (vector.getZ() * tan);
            float screenX = (float)(halfWidth - vector.getX() * scaleFactor);
            float screenY = (float)(halfHeight - vector.getY() * scaleFactor);
            screenX = MathHelper.clamp(screenX, 0.0f, (float)this.window.getWidth());
            screenY = MathHelper.clamp(screenY, 0.0f, (float)this.window.getHeight());
            if (!Float.isNaN(screenX) && !Float.isNaN(screenY)) {
                playerMeta.setScreenPosition(new FloatVector2(screenX, screenY));
            }
        }
        this.players.add(playerMeta);
    }
    
    public List<PlayerInsight> getPlayers() {
        return this.players;
    }
    
    public ServerInsight getServer() {
        return this.server;
    }
    
    public CameraInsight getCamera() {
        return this.camera;
    }
    
    public WindowInsight getWindow() {
        return this.window;
    }
    
    public void setCamera(final CameraInsight cameraMeta) {
        this.camera = cameraMeta;
    }
    
    public void setWindow(final WindowInsight windowMeta) {
        this.window = windowMeta;
    }
    
    public void setServerData(final ServerInsight server) {
        this.server = server;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject object = new JsonObject();
        final JsonObject metadata = new JsonObject();
        final JsonArray players = new JsonArray();
        for (final PlayerInsight taggedPlayer : this.players) {
            players.add((JsonElement)taggedPlayer.toJsonObject());
        }
        metadata.add("players", (JsonElement)players);
        if (this.server != null) {
            metadata.add("server", (JsonElement)this.server.toJsonObject());
        }
        if (this.camera != null) {
            metadata.add("camera", (JsonElement)this.camera.toJsonObject());
        }
        if (this.window != null) {
            metadata.add("window", (JsonElement)this.window.toJsonObject());
        }
        object.add("metadata", (JsonElement)metadata);
        return object;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        final JsonObject metaDataObject = object.get("metadata").getAsJsonObject();
        if (metaDataObject.has("players")) {
            final JsonArray playersArray = metaDataObject.get("players").getAsJsonArray();
            for (final JsonElement playerElement : playersArray) {
                this.players.add(new PlayerInsight(playerElement.getAsJsonObject()));
            }
        }
        if (metaDataObject.has("server")) {
            this.server = new ServerInsight(metaDataObject.get("server").getAsJsonObject());
        }
        if (metaDataObject.has("camera")) {
            this.camera = new CameraInsight(metaDataObject.get("camera").getAsJsonObject());
        }
        if (metaDataObject.has("window")) {
            this.window = new WindowInsight(metaDataObject.get("window").getAsJsonObject());
        }
    }
}
