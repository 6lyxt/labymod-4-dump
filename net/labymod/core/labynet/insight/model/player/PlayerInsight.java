// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.player;

import com.google.gson.JsonElement;
import java.util.Iterator;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.mojang.Property;
import java.util.Collection;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.vector.FloatVector2;
import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.model.player.animation.AnimationInsight;
import java.util.UUID;
import java.util.Base64;
import net.labymod.core.labynet.insight.Insight;

public class PlayerInsight implements Insight
{
    private static final Base64.Decoder BASE64_DECODER;
    private UUID uuid;
    private String name;
    private double positionX;
    private double positionY;
    private double positionZ;
    private float rotationYaw;
    private float rotationPitch;
    private AnimationInsight animation;
    private JsonObject textures;
    private UserInsight user;
    private FloatVector2 screenPosition;
    
    public PlayerInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public PlayerInsight(final Player player, final float partialTicks) {
        this.uuid = player.getUniqueId();
        this.name = ColorUtil.removeLegacyColors(player.getName());
        final Position position = player.position();
        final Position previousPosition = player.previousPosition();
        this.positionX = position.lerpX(previousPosition, partialTicks);
        this.positionY = position.lerpY(previousPosition, partialTicks);
        this.positionZ = position.lerpZ(previousPosition, partialTicks);
        this.rotationYaw = MathHelper.lerp(player.getRotationYaw(), player.getPreviousRotationYaw(), partialTicks);
        this.rotationPitch = MathHelper.lerp(player.getRotationPitch(), player.getPreviousRotationPitch(), partialTicks);
        this.animation = new AnimationInsight(player, partialTicks);
        try {
            final Collection<Property> textures = player.profile().getProperties().get("textures");
            if (textures != null) {
                final Iterator<Property> iterator = textures.iterator();
                if (iterator.hasNext()) {
                    final Property texture = iterator.next();
                    final String json = new String(PlayerInsight.BASE64_DECODER.decode(texture.getValue()));
                    this.textures = ((JsonObject)GsonUtil.DEFAULT_GSON.fromJson(json, (Class)JsonObject.class)).getAsJsonObject("textures");
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        final UserInsight user = new UserInsight(player);
        if (user.isLoaded()) {
            this.user = user;
        }
    }
    
    public void setScreenPosition(final FloatVector2 position) {
        this.screenPosition = position;
    }
    
    public FloatVector2 getScreenPosition() {
        return this.screenPosition;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject player = new JsonObject();
        player.addProperty("uuid", this.uuid.toString());
        player.addProperty("name", this.name);
        final JsonObject position = new JsonObject();
        position.addProperty("x", (Number)this.positionX);
        position.addProperty("y", (Number)this.positionY);
        position.addProperty("z", (Number)this.positionZ);
        player.add("position", (JsonElement)position);
        final JsonObject rotation = new JsonObject();
        rotation.addProperty("yaw", (Number)this.rotationYaw);
        rotation.addProperty("pitch", (Number)this.rotationPitch);
        player.add("rotation", (JsonElement)rotation);
        player.add("animation", (JsonElement)this.animation.toJsonObject());
        if (this.textures != null) {
            player.add("textures", (JsonElement)this.textures);
        }
        if (this.user != null) {
            player.add("user", (JsonElement)this.user.toJsonObject());
        }
        if (this.screenPosition != null) {
            final JsonObject screenPosition = new JsonObject();
            screenPosition.addProperty("x", (Number)this.screenPosition.getX());
            screenPosition.addProperty("y", (Number)this.screenPosition.getY());
            player.add("screen_position", (JsonElement)screenPosition);
        }
        return player;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        this.uuid = UUID.fromString(object.get("uuid").getAsString());
        this.name = object.get("name").getAsString();
        final JsonObject position = object.get("position").getAsJsonObject();
        this.positionX = position.get("x").getAsDouble();
        this.positionY = position.get("y").getAsDouble();
        this.positionZ = position.get("z").getAsDouble();
        final JsonObject rotation = object.get("rotation").getAsJsonObject();
        this.rotationYaw = rotation.get("yaw").getAsFloat();
        this.rotationPitch = rotation.get("pitch").getAsFloat();
        this.animation = new AnimationInsight(object.get("animation").getAsJsonObject());
        if (object.has("textures")) {
            this.textures = object.get("textures").getAsJsonObject();
        }
        if (object.has("user")) {
            this.user = new UserInsight(object.get("user").getAsJsonObject());
        }
        if (object.has("screen_position")) {
            final JsonObject screenPosition = object.get("screen_position").getAsJsonObject();
            this.screenPosition = new FloatVector2(screenPosition.get("x").getAsFloat(), screenPosition.get("y").getAsFloat());
        }
    }
    
    static {
        BASE64_DECODER = Base64.getDecoder();
    }
}
