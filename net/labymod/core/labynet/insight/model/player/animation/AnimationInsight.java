// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.player.animation;

import com.google.gson.JsonElement;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.player.Player;
import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.model.player.animation.emote.EmoteInsight;
import net.labymod.core.labynet.insight.Insight;

public class AnimationInsight implements Insight
{
    private float rotationHeadYaw;
    private float rotationRenderYawOffset;
    private float limbSwing;
    private float limbSwingStrength;
    private float walkingSpeed;
    private boolean crouching;
    private float armSwingProgress;
    private EmoteInsight emote;
    
    public AnimationInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public AnimationInsight(final Player player, final float partialTicks) {
        this.rotationHeadYaw = MathHelper.lerp(player.getRotationHeadYaw(), player.getPreviousRotationHeadYaw(), partialTicks);
        this.rotationRenderYawOffset = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        this.limbSwing = player.getLimbSwing();
        this.limbSwingStrength = MathHelper.lerp(player.getLimbSwingStrength(), player.getPreviousLimbSwingStrength(), partialTicks);
        this.walkingSpeed = player.getWalkingSpeed(partialTicks);
        this.crouching = player.isCrouching();
        this.armSwingProgress = player.getArmSwingProgress(partialTicks);
        final EmoteInsight emote = new EmoteInsight(player);
        if (emote.isPlaying()) {
            this.emote = emote;
        }
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject animation = new JsonObject();
        animation.addProperty("head_yaw", (Number)this.rotationHeadYaw);
        animation.addProperty("render_yaw_offset", (Number)this.rotationRenderYawOffset);
        animation.addProperty("limb_swing", (Number)this.limbSwing);
        animation.addProperty("limb_swing_strength", (Number)this.limbSwingStrength);
        animation.addProperty("walking_speed", (Number)this.walkingSpeed);
        animation.addProperty("crouching", Boolean.valueOf(this.crouching));
        animation.addProperty("arm_swing_progress", (Number)this.armSwingProgress);
        if (this.emote != null) {
            animation.add("emote", (JsonElement)this.emote.toJsonObject());
        }
        return animation;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        this.rotationHeadYaw = object.get("head_yaw").getAsFloat();
        this.rotationRenderYawOffset = object.get("render_yaw_offset").getAsFloat();
        this.limbSwing = object.get("limb_swing").getAsFloat();
        this.limbSwingStrength = object.get("limb_swing_strength").getAsFloat();
        this.walkingSpeed = object.get("walking_speed").getAsFloat();
        this.crouching = object.get("crouching").getAsBoolean();
        this.armSwingProgress = object.get("arm_swing_progress").getAsFloat();
        if (object.has("emote")) {
            this.emote = new EmoteInsight(object.get("emote").getAsJsonObject());
        }
    }
}
