// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
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
import net.labymod.v1_12_2.mixins.client.entity.MixinEntityLivingBase;

@Mixin({ bua.class })
public abstract class MixinAbstractLocalPlayer extends MixinEntityLivingBase implements Player
{
    private final PlayerAbilities labyMod$playerAbilities;
    private final Position labyMod$chasingPosition;
    private final Position labyMod$previousChasingPosition;
    private GameUser labyMod$gameUser;
    
    public MixinAbstractLocalPlayer() {
        this.labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> this.labyMod$abilities().a, value -> this.labyMod$abilities().a = value), new BooleanAbilityOption(() -> this.labyMod$abilities().b, value -> this.labyMod$abilities().b = value), new BooleanAbilityOption(() -> this.labyMod$abilities().c, value -> this.labyMod$abilities().c = value), new BooleanAbilityOption(() -> this.labyMod$abilities().d, value -> this.labyMod$abilities().d = value), new BooleanAbilityOption(() -> this.labyMod$abilities().e, value -> this.labyMod$abilities().e = value), new FloatAbilityOption(() -> this.labyMod$abilities().a(), value -> this.labyMod$abilities().a(value)), new FloatAbilityOption(() -> this.labyMod$abilities().b(), value -> this.labyMod$abilities().b(value)));
        this.labyMod$chasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bH, x -> this.labyMod$getPlayer().bH = x, () -> this.labyMod$getPlayer().bI, y -> this.labyMod$getPlayer().bI = y, () -> this.labyMod$getPlayer().bJ, z -> this.labyMod$getPlayer().bJ = z);
        this.labyMod$previousChasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bE, x -> this.labyMod$getPlayer().bE = x, () -> this.labyMod$getPlayer().bF, y -> this.labyMod$getPlayer().bF = y, () -> this.labyMod$getPlayer().bG, z -> this.labyMod$getPlayer().bG = z);
    }
    
    @Override
    public boolean isSwingingHand() {
        return this.labyMod$getPlayer().au;
    }
    
    @Override
    public float getArmSwingProgress() {
        return this.labyMod$getPlayer().aD;
    }
    
    @Override
    public float getPreviousArmSwingProgress() {
        return this.labyMod$getPlayer().aC;
    }
    
    @Override
    public boolean isDead() {
        return this.labyMod$getPlayer().F;
    }
    
    @Override
    public String getName() {
        return this.labyMod$getPlayer().da().getName();
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
        return this.labyMod$getPlayer().bC;
    }
    
    @Override
    public float getPreviousCameraYaw() {
        return this.labyMod$getPlayer().bB;
    }
    
    @Override
    public float getPreviousWalkDistance() {
        return this.labyMod$getPlayer().I;
    }
    
    @Override
    public float getWalkDistance() {
        return this.labyMod$getPlayer().J;
    }
    
    @Override
    public float getPreviousRotationHeadYaw() {
        return this.labyMod$getPlayer().aQ;
    }
    
    @Override
    public float getRotationHeadYaw() {
        return this.labyMod$getPlayer().aP;
    }
    
    @Override
    public float getPreviousLimbSwingStrength() {
        return this.labyMod$getPlayer().aF;
    }
    
    @Override
    public float getLimbSwingStrength() {
        return this.labyMod$getPlayer().aG;
    }
    
    @Override
    public float getLimbSwing() {
        return this.labyMod$getPlayer().aH;
    }
    
    @Override
    public float getRenderTick(final float partialTicks) {
        return this.labyMod$getPlayer().T + partialTicks;
    }
    
    @Override
    public double getSprintingSpeed() {
        return this.labyMod$getPlayer().a(adh.d).e();
    }
    
    @Override
    public ResourceLocation skinTexture() {
        return (ResourceLocation)this.labyMod$getPlayer().m();
    }
    
    @Nullable
    @Override
    public ResourceLocation getCloakTexture() {
        final nf location = this.labyMod$getPlayer().q();
        return (location == null) ? null : ((ResourceLocation)location);
    }
    
    @Override
    public boolean isSlim() {
        return this.labyMod$getPlayer().t().equals("slim");
    }
    
    @Override
    public EntityPose entityPose() {
        final bua player = this.labyMod$getPlayer();
        if (player.cz()) {
            return EntityPose.SLEEPING;
        }
        return super.entityPose();
    }
    
    @Override
    public boolean isWearingTop() {
        final aip itemStack = this.labyMod$getPlayer().b(vl.e);
        return itemStack != null && !itemStack.b();
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
        return (GameProfile)this.labyMod$getPlayer().da();
    }
    
    @Override
    public FoodData foodData() {
        return (FoodData)this.labyMod$getPlayer().di();
    }
    
    @Override
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }
    
    @Override
    public int getExperienceLevel() {
        return this.labyMod$getPlayer().bP;
    }
    
    @Override
    public int getTotalExperience() {
        return this.labyMod$getPlayer().bQ;
    }
    
    @Override
    public float getExperienceProgress() {
        return this.labyMod$getPlayer().bR;
    }
    
    @Override
    public int getExperienceNeededForNextLevel() {
        return this.labyMod$getPlayer().dh();
    }
    
    @Override
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }
    
    @Override
    public boolean isShownModelPart(final PlayerClothes part) {
        aee modelPart = null;
        switch (part) {
            case CAPE: {
                modelPart = aee.a;
                break;
            }
            case JACKET: {
                modelPart = aee.b;
                break;
            }
            case LEFT_SLEEVE: {
                modelPart = aee.c;
                break;
            }
            case RIGHT_SLEEVE: {
                modelPart = aee.d;
                break;
            }
            case LEFT_PANTS_LEG: {
                modelPart = aee.e;
                break;
            }
            case RIGHT_PANTS_LEG: {
                modelPart = aee.f;
                break;
            }
            case HAT: {
                modelPart = aee.g;
                break;
            }
            default: {
                return true;
            }
        }
        return this.labyMod$getPlayer().a(modelPart);
    }
    
    @Override
    public LivingEntity.Hand getUsedItemHand() {
        return (this.labyMod$getPlayer().cH() == ub.a) ? LivingEntity.Hand.MAIN_HAND : LivingEntity.Hand.OFF_HAND;
    }
    
    @Override
    public boolean isMainHandRight() {
        return this.labyMod$getPlayer().cF() == vo.b;
    }
    
    private aeb labyMod$abilities() {
        return this.labyMod$getPlayer().bO;
    }
    
    private bua labyMod$getPlayer() {
        return (bua)this;
    }
    
    @Override
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(this.labyMod$getPlayer().bm());
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
