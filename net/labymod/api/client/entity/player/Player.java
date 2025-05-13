// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import net.labymod.api.user.GameUser;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.Laby;
import net.labymod.api.client.network.NetworkPlayerInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.entity.LivingEntity;

public interface Player extends LivingEntity
{
    boolean isSwingingHand();
    
    float getArmSwingProgress();
    
    float getPreviousArmSwingProgress();
    
    boolean isDead();
    
    String getName();
    
    Position chasingPosition();
    
    Position previousChasingPosition();
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosX() {
        return (float)this.chasingPosition().getX();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosX() {
        return (float)this.previousChasingPosition().getX();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosY() {
        return (float)this.chasingPosition().getY();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosY() {
        return (float)this.previousChasingPosition().getY();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getChasingPosZ() {
        return (float)this.chasingPosition().getZ();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousChasingPosZ() {
        return (float)this.previousChasingPosition().getZ();
    }
    
    float getCameraYaw();
    
    float getPreviousCameraYaw();
    
    float getPreviousWalkDistance();
    
    float getWalkDistance();
    
    float getPreviousRotationHeadYaw();
    
    float getRotationHeadYaw();
    
    default float getAgeTick() {
        return this.getRenderTick(1.0f);
    }
    
    float getPreviousLimbSwingStrength();
    
    float getLimbSwingStrength();
    
    float getLimbSwing();
    
    float getRenderTick(final float p0);
    
    default float getMovementFactor() {
        return this.getMovementFactor(1.0f);
    }
    
    default float getMovementFactor(final float partialTicks) {
        return this.getLimbSwing() - this.getLimbSwingStrength() * (1.0f - partialTicks);
    }
    
    default float getWalkingSpeed() {
        return this.getWalkingSpeed(1.0f);
    }
    
    default float getWalkingSpeed(final float partialTicks) {
        return MathHelper.lerp(this.getLimbSwingStrength(), this.getPreviousLimbSwingStrength(), partialTicks);
    }
    
    default float getArmSwingProgress(final float partialTicks) {
        final float prevSwingProgress = this.getPreviousArmSwingProgress();
        float swingProgressDiff = this.getArmSwingProgress() - prevSwingProgress;
        if (swingProgressDiff < 0.0) {
            ++swingProgressDiff;
        }
        return prevSwingProgress + swingProgressDiff * partialTicks;
    }
    
    boolean isShownModelPart(final PlayerClothes p0);
    
    double getSprintingSpeed();
    
    ResourceLocation skinTexture();
    
    @Nullable
    ResourceLocation getCloakTexture();
    
    boolean isSlim();
    
    GameMode gameMode();
    
    boolean isWearingTop();
    
    default boolean canDestroyBlocks() {
        return this.gameMode() == GameMode.SURVIVAL || this.gameMode() == GameMode.CREATIVE;
    }
    
    @Nullable
    default NetworkPlayerInfo getNetworkPlayerInfo() {
        final ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null) {
            return null;
        }
        return clientPacketListener.getNetworkPlayerInfo(this.getUniqueId());
    }
    
    @Deprecated
    @Nullable
    default NetworkPlayerInfo networkPlayerInfo() {
        return this.getNetworkPlayerInfo();
    }
    
    GameProfile profile();
    
    FoodData foodData();
    
    PlayerAbilities abilities();
    
    int getExperienceLevel();
    
    int getTotalExperience();
    
    float getExperienceProgress();
    
    int getExperienceNeededForNextLevel();
    
    @ApiStatus.Internal
    @ApiStatus.Experimental
    PlayerModel playerModel();
    
    GameUser gameUser();
}
