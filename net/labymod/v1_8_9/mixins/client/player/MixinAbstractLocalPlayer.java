// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.EntityPose;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.core.client.entity.player.abilities.DefaultPlayerAbilities;
import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntityLivingBase;

@Mixin({ bet.class })
public abstract class MixinAbstractLocalPlayer extends MixinEntityLivingBase implements Player
{
    private final PlayerAbilities labyMod$playerAbilities;
    private final Position labyMod$chasingPosition;
    private final Position labyMod$previousChasingPosition;
    private GameUser labyMod$gameUser;
    
    public MixinAbstractLocalPlayer() {
        this.labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> this.labyMod$abilities().a, value -> this.labyMod$abilities().a = value), new BooleanAbilityOption(() -> this.labyMod$abilities().b, value -> this.labyMod$abilities().b = value), new BooleanAbilityOption(() -> this.labyMod$abilities().c, value -> this.labyMod$abilities().c = value), new BooleanAbilityOption(() -> this.labyMod$abilities().d, value -> this.labyMod$abilities().d = value), new BooleanAbilityOption(() -> this.labyMod$abilities().e, value -> this.labyMod$abilities().e = value), new FloatAbilityOption(() -> this.labyMod$abilities().a(), value -> this.labyMod$abilities().a(value)), new FloatAbilityOption(() -> this.labyMod$abilities().b(), value -> this.labyMod$abilities().b(value)));
        this.labyMod$chasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bt, x -> this.labyMod$getPlayer().bt = x, () -> this.labyMod$getPlayer().bu, y -> this.labyMod$getPlayer().bu = y, () -> this.labyMod$getPlayer().bv, z -> this.labyMod$getPlayer().bv = z);
        this.labyMod$previousChasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bq, x -> this.labyMod$getPlayer().bq = x, () -> this.labyMod$getPlayer().br, y -> this.labyMod$getPlayer().br = y, () -> this.labyMod$getPlayer().bs, z -> this.labyMod$getPlayer().bs = z);
    }
    
    @Override
    public boolean isSwingingHand() {
        return this.labyMod$getPlayer().ar;
    }
    
    @Override
    public float getArmSwingProgress() {
        return this.labyMod$getPlayer().az;
    }
    
    @Override
    public float getPreviousArmSwingProgress() {
        return this.labyMod$getPlayer().ay;
    }
    
    @Override
    public boolean isDead() {
        return this.labyMod$getPlayer().I;
    }
    
    @Override
    public String getName() {
        return this.labyMod$getPlayer().cd().getName();
    }
    
    @Override
    public Position chasingPosition() {
        return this.labyMod$chasingPosition;
    }
    
    @Override
    public Position previousChasingPosition() {
        return this.labyMod$previousChasingPosition;
    }
    
    @Override
    public float getCameraYaw() {
        return this.labyMod$getPlayer().bo;
    }
    
    @Override
    public float getPreviousCameraYaw() {
        return this.labyMod$getPlayer().bn;
    }
    
    @Override
    public float getPreviousWalkDistance() {
        return this.labyMod$getPlayer().L;
    }
    
    @Override
    public float getWalkDistance() {
        return this.labyMod$getPlayer().M;
    }
    
    @Override
    public float getPreviousRotationHeadYaw() {
        return this.labyMod$getPlayer().aL;
    }
    
    @Override
    public float getRotationHeadYaw() {
        return this.labyMod$getPlayer().aK;
    }
    
    @Override
    public float getPreviousLimbSwingStrength() {
        return this.labyMod$getPlayer().aA;
    }
    
    @Override
    public float getLimbSwingStrength() {
        return this.labyMod$getPlayer().aB;
    }
    
    @Override
    public float getLimbSwing() {
        return this.labyMod$getPlayer().aC;
    }
    
    @Override
    public float getRenderTick(final float partialTicks) {
        return this.labyMod$getPlayer().W + partialTicks;
    }
    
    @Override
    public double getSprintingSpeed() {
        return this.labyMod$getPlayer().a(vy.d).e();
    }
    
    @Override
    public ResourceLocation skinTexture() {
        return (ResourceLocation)this.labyMod$getPlayer().i();
    }
    
    @Nullable
    @Override
    public ResourceLocation getCloakTexture() {
        final jy location = this.labyMod$getPlayer().k();
        return (location == null) ? null : ((ResourceLocation)location);
    }
    
    @Override
    public boolean isSlim() {
        return this.labyMod$getPlayer().l().equals("slim");
    }
    
    @Override
    public EntityPose entityPose() {
        final bet player = this.labyMod$getPlayer();
        if (player.bJ()) {
            return EntityPose.SLEEPING;
        }
        return super.entityPose();
    }
    
    @Override
    public boolean isWearingTop() {
        return this.labyMod$getPlayer().q(2) != null;
    }
    
    @Override
    public GameMode gameMode() {
        final NetworkPlayerInfo networkPlayerInfo = this.networkPlayerInfo();
        if (networkPlayerInfo == null) {
            return GameMode.UNKNOWN;
        }
        return networkPlayerInfo.gameMode();
    }
    
    @Override
    public GameProfile profile() {
        final NetworkPlayerInfo playerInfo = this.networkPlayerInfo();
        if (playerInfo != null) {
            return playerInfo.profile();
        }
        return (GameProfile)this.labyMod$getPlayer().cd();
    }
    
    @Override
    public FoodData foodData() {
        return (FoodData)this.labyMod$getPlayer().cl();
    }
    
    @Override
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }
    
    @Override
    public int getExperienceLevel() {
        return this.labyMod$getPlayer().bB;
    }
    
    @Override
    public int getTotalExperience() {
        return this.labyMod$getPlayer().bC;
    }
    
    @Override
    public float getExperienceProgress() {
        return this.labyMod$getPlayer().bD;
    }
    
    @Override
    public int getExperienceNeededForNextLevel() {
        return this.labyMod$getPlayer().ck();
    }
    
    @Override
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }
    
    @Override
    public boolean isShownModelPart(final PlayerClothes part) {
        wo modelPart = null;
        switch (part) {
            case CAPE: {
                modelPart = wo.a;
                break;
            }
            case JACKET: {
                modelPart = wo.b;
                break;
            }
            case LEFT_SLEEVE: {
                modelPart = wo.c;
                break;
            }
            case RIGHT_SLEEVE: {
                modelPart = wo.d;
                break;
            }
            case LEFT_PANTS_LEG: {
                modelPart = wo.e;
                break;
            }
            case RIGHT_PANTS_LEG: {
                modelPart = wo.f;
                break;
            }
            case HAT: {
                modelPart = wo.g;
                break;
            }
            default: {
                return true;
            }
        }
        return this.labyMod$getPlayer().a(modelPart);
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.labyMod$getPlayer().bT();
    }
    
    @Override
    public LivingEntity.Hand getUsedItemHand() {
        return LivingEntity.Hand.MAIN_HAND;
    }
    
    private wl labyMod$abilities() {
        return this.labyMod$getPlayer().bA;
    }
    
    private bet labyMod$getPlayer() {
        return (bet)this;
    }
    
    @Override
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(this.labyMod$getPlayer().aK());
        }
        return this.labyMod$gameUser;
    }
    
    @Inject(method = { "getFovModifier" }, at = { @At("RETURN") }, cancellable = true)
    private void modifyFieldOfViewModifier(final CallbackInfoReturnable<Float> cir) {
        Float fieldOfView = (Float)cir.getReturnValue();
        final FieldOfViewModifierEvent fieldOfViewModifierEvent = new FieldOfViewModifierEvent(fieldOfView);
        Laby.fireEvent(fieldOfViewModifierEvent);
        fieldOfView = fieldOfViewModifierEvent.getFieldOfView();
        cir.setReturnValue((Object)fieldOfView);
    }
}
