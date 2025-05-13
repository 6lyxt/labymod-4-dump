// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player;

import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.client.entity.player.abilities.NOPPlayerAbilities;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.entity.player.GameMode;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Collection;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.DefaultPosition;
import java.util.UUID;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.entity.player.Player;

public class DummyPlayer implements Player
{
    private static final ResourceLocation ENTITY_ID;
    private final Position position;
    private final Position previousPosition;
    private final Position chasingPosition;
    private final Position previousChasingPosition;
    private GameUser gameUser;
    private boolean wearingElytra;
    private UUID uniqueId;
    
    public DummyPlayer(final UUID uniqueId) {
        this.position = new DefaultPosition();
        this.previousPosition = new DefaultPosition();
        this.chasingPosition = new DefaultPosition();
        this.previousChasingPosition = new DefaultPosition();
        this.wearingElytra = false;
        this.uniqueId = uniqueId;
    }
    
    public DummyPlayer() {
        this(UUID.randomUUID());
    }
    
    @Override
    public Position position() {
        return this.position;
    }
    
    @Override
    public Position previousPosition() {
        return this.previousPosition;
    }
    
    @Override
    public boolean isCrouching() {
        return false;
    }
    
    @Override
    public boolean isInvisible() {
        return false;
    }
    
    @Override
    public boolean isInvisibleFor(final Player player) {
        return false;
    }
    
    @Override
    public boolean isSprinting() {
        return false;
    }
    
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    @Override
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return null;
    }
    
    @Override
    public FloatVector3 perspectiveVector(final float partialTicks) {
        return null;
    }
    
    @Override
    public EntityPose entityPose() {
        return null;
    }
    
    @Override
    public boolean canEnterEntityPose(final EntityPose pose) {
        return false;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.0f;
    }
    
    @Override
    public DataWatcher dataWatcher() {
        return null;
    }
    
    @Override
    public TagType nameTagType() {
        return null;
    }
    
    @Override
    public void setNameTagType(final TagType type) {
    }
    
    @Override
    public void setRendered(final boolean rendered) {
    }
    
    @Override
    public boolean isRendered() {
        return false;
    }
    
    @Override
    public boolean isInWater() {
        return false;
    }
    
    @Override
    public boolean isInLava() {
        return false;
    }
    
    @Override
    public boolean isUnderWater() {
        return false;
    }
    
    @Override
    public boolean isOnFire() {
        return false;
    }
    
    @Override
    public boolean isOnGround() {
        return false;
    }
    
    @Override
    public Entity getVehicle() {
        return null;
    }
    
    @Override
    public ResourceLocation entityId() {
        return DummyPlayer.ENTITY_ID;
    }
    
    @Override
    public GameUser gameUser() {
        if (this.gameUser == null) {
            this.gameUser = Laby.references().gameUserService().gameUser(this.getUniqueId());
        }
        return this.gameUser;
    }
    
    public void setUniqueId(final UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.gameUser = Laby.references().gameUserService().gameUser(uniqueId);
    }
    
    @Override
    public float getHealth() {
        return 0.0f;
    }
    
    @Override
    public float getMaximalHealth() {
        return 0.0f;
    }
    
    @Override
    public float getAbsorptionHealth() {
        return 0.0f;
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return 0;
    }
    
    @Override
    public LivingEntity.Hand getUsedItemHand() {
        return LivingEntity.Hand.MAIN_HAND;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return null;
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return null;
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return null;
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return null;
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final LivingEntity.EquipmentSpot equipmentSpot) {
        return null;
    }
    
    @Override
    public int getHurtTime() {
        return 0;
    }
    
    @Override
    public int getDeathTime() {
        return 0;
    }
    
    @Override
    public boolean isHostile() {
        return false;
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return null;
    }
    
    @Override
    public boolean isSwingingHand() {
        return false;
    }
    
    @Override
    public float getArmSwingProgress() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousArmSwingProgress() {
        return 0.0f;
    }
    
    @Override
    public boolean isDead() {
        return false;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public Position chasingPosition() {
        return this.chasingPosition;
    }
    
    @Override
    public Position previousChasingPosition() {
        return this.previousChasingPosition;
    }
    
    @Override
    public float getBodyRotationY() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return 0.0f;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
    }
    
    @Override
    public float getHeadRotationY() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return 0.0f;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
    }
    
    @Override
    public float getCameraYaw() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousCameraYaw() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousWalkDistance() {
        return 0.0f;
    }
    
    @Override
    public float getWalkDistance() {
        return 0.0f;
    }
    
    @Override
    public float getRotationYaw() {
        return 0.0f;
    }
    
    @Override
    public void setRotationYaw(final float rotationYaw) {
    }
    
    @Override
    public float getPreviousRotationYaw() {
        return 0.0f;
    }
    
    @Override
    public void setPreviousRotationYaw(final float previousRotationYaw) {
    }
    
    @Override
    public float getRotationPitch() {
        return 0.0f;
    }
    
    @Override
    public void setRotationPitch(final float rotationPitch) {
    }
    
    @Override
    public float getPreviousRotationPitch() {
        return 0.0f;
    }
    
    @Override
    public void setPreviousRotationPitch(final float previousRotationPitch) {
    }
    
    @Override
    public float getPreviousRotationHeadYaw() {
        return 0.0f;
    }
    
    @Override
    public float getRotationHeadYaw() {
        return 0.0f;
    }
    
    @Override
    public float getPreviousLimbSwingStrength() {
        return 0.0f;
    }
    
    @Override
    public float getLimbSwingStrength() {
        return 0.0f;
    }
    
    @Override
    public float getLimbSwing() {
        return 0.0f;
    }
    
    @Override
    public float getRenderTick(final float partialTicks) {
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return TimeUtil.getMillis() % 10000000L / 50.0f;
        }
        return clientPlayer.getRenderTick(partialTicks);
    }
    
    @Override
    public boolean isShownModelPart(final PlayerClothes part) {
        final MinecraftOptions options = Laby.labyAPI().minecraft().options();
        final int modelParts = options.getModelParts();
        return switch (part) {
            default -> throw new MatchException(null, null);
            case CAPE -> SkinLayer.CAPE.isEnabled(modelParts);
            case JACKET -> SkinLayer.JACKET.isEnabled(modelParts);
            case LEFT_SLEEVE -> SkinLayer.LEFT_SLEEVE.isEnabled(modelParts);
            case RIGHT_SLEEVE -> SkinLayer.RIGHT_SLEEVE.isEnabled(modelParts);
            case LEFT_PANTS_LEG -> SkinLayer.LEFT_PANTS_LEG.isEnabled(modelParts);
            case RIGHT_PANTS_LEG -> SkinLayer.RIGHT_PANTS_LEG.isEnabled(modelParts);
            case HAT -> SkinLayer.HAT.isEnabled(modelParts);
        };
    }
    
    @Override
    public double getSprintingSpeed() {
        return 0.0;
    }
    
    @Override
    public ResourceLocation skinTexture() {
        return null;
    }
    
    @Nullable
    @Override
    public ResourceLocation getCloakTexture() {
        return null;
    }
    
    @Override
    public boolean isSlim() {
        return false;
    }
    
    @Override
    public GameMode gameMode() {
        return null;
    }
    
    @Override
    public boolean isWearingTop() {
        return false;
    }
    
    @Override
    public boolean isWearingElytra() {
        return this.wearingElytra;
    }
    
    public void setWearingElytra(final boolean wearingElytra) {
        this.wearingElytra = wearingElytra;
    }
    
    @Override
    public GameProfile profile() {
        return null;
    }
    
    @Override
    public FoodData foodData() {
        return null;
    }
    
    @Override
    public PlayerAbilities abilities() {
        return NOPPlayerAbilities.INSTANCE;
    }
    
    @Override
    public int getExperienceLevel() {
        return 0;
    }
    
    @Override
    public int getTotalExperience() {
        return 0;
    }
    
    @Override
    public float getExperienceProgress() {
        return 0.0f;
    }
    
    @Override
    public int getExperienceNeededForNextLevel() {
        return 0;
    }
    
    @Override
    public PlayerModel playerModel() {
        return null;
    }
    
    @Override
    public boolean isSleeping() {
        return false;
    }
    
    static {
        ENTITY_ID = ResourceLocation.create("minecraft", "player");
    }
}
