// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.Laby;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.v1_19_4.client.util.WalkAnimationStateAccessor;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.core.client.entity.player.abilities.DefaultPlayerAbilities;
import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_19_4.mixins.client.entity.MixinLivingEntity;

@Mixin({ fhh.class })
@Implements({ @Interface(iface = Player.class, prefix = "player$", remap = Interface.Remap.NONE) })
public abstract class MixinAbstractLocalPlayer extends MixinLivingEntity implements Player
{
    private final PlayerAbilities labyMod$playerAbilities;
    private final Position labyMod$chasingPosition;
    private final Position labyMod$previousChasingPosition;
    private GameUser labyMod$gameUser;
    
    public MixinAbstractLocalPlayer() {
        this.labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> this.labyMod$abilities().a, value -> this.labyMod$abilities().a = value), new BooleanAbilityOption(() -> this.labyMod$abilities().b, value -> this.labyMod$abilities().b = value), new BooleanAbilityOption(() -> this.labyMod$abilities().c, value -> this.labyMod$abilities().c = value), new BooleanAbilityOption(() -> this.labyMod$abilities().d, value -> this.labyMod$abilities().d = value), new BooleanAbilityOption(() -> this.labyMod$abilities().e, value -> this.labyMod$abilities().e = value), new FloatAbilityOption(() -> this.labyMod$abilities().a(), value -> this.labyMod$abilities().a(value)), new FloatAbilityOption(() -> this.labyMod$abilities().b(), value -> this.labyMod$abilities().b(value)));
        this.labyMod$chasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bY, x -> this.labyMod$getPlayer().bY = x, () -> this.labyMod$getPlayer().bZ, y -> this.labyMod$getPlayer().bZ = y, () -> this.labyMod$getPlayer().ca, z -> this.labyMod$getPlayer().ca = z);
        this.labyMod$previousChasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bV, x -> this.labyMod$getPlayer().bV = x, () -> this.labyMod$getPlayer().bW, y -> this.labyMod$getPlayer().bW = y, () -> this.labyMod$getPlayer().bX, z -> this.labyMod$getPlayer().bX = z);
    }
    
    @Override
    public boolean isSwingingHand() {
        return this.labyMod$getPlayer().aE;
    }
    
    @Override
    public float getArmSwingProgress() {
        return this.labyMod$getPlayer().aN;
    }
    
    @Override
    public float getPreviousArmSwingProgress() {
        return this.labyMod$getPlayer().aM;
    }
    
    @Override
    public boolean isDead() {
        return !this.labyMod$getPlayer().bq();
    }
    
    @Override
    public String getName() {
        return this.labyMod$getPlayer().fI().getName();
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
        return this.labyMod$getPlayer().bT;
    }
    
    @Override
    public float getPreviousCameraYaw() {
        return this.labyMod$getPlayer().bS;
    }
    
    @Override
    public float getPreviousWalkDistance() {
        return this.labyMod$getPlayer().W;
    }
    
    @Override
    public float getWalkDistance() {
        return this.labyMod$getPlayer().X;
    }
    
    @Override
    public float getPreviousRotationHeadYaw() {
        return this.labyMod$getPlayer().aW;
    }
    
    @Override
    public float getRotationHeadYaw() {
        return this.labyMod$getPlayer().aV;
    }
    
    @Override
    public float getRenderTick(final float partialTicks) {
        return this.labyMod$getPlayer().ag + partialTicks;
    }
    
    @Override
    public double getSprintingSpeed() {
        return this.labyMod$getPlayer().b(bhe.d);
    }
    
    @Override
    public ResourceLocation skinTexture() {
        return (ResourceLocation)this.labyMod$getPlayer().d();
    }
    
    @Nullable
    @Override
    public ResourceLocation getCloakTexture() {
        final add location = this.labyMod$getPlayer().e();
        return (location == null) ? null : ((ResourceLocation)location);
    }
    
    @Override
    public boolean isSlim() {
        return this.labyMod$getPlayer().m().equals("slim");
    }
    
    @Override
    public boolean isWearingTop() {
        return this.labyMod$getPlayer().b(bfm.e);
    }
    
    @Override
    public float getPreviousLimbSwingStrength() {
        return ((WalkAnimationStateAccessor)this.labyMod$getPlayer().aP).getSpeedOld();
    }
    
    @Override
    public float getLimbSwingStrength() {
        return this.labyMod$getPlayer().aP.a();
    }
    
    @Override
    public float getLimbSwing() {
        return this.labyMod$getPlayer().aP.b();
    }
    
    @Override
    public GameMode gameMode() {
        final NetworkPlayerInfo playerInfo = this.networkPlayerInfo();
        if (playerInfo == null) {
            return GameMode.UNKNOWN;
        }
        return playerInfo.gameMode();
    }
    
    @Override
    public boolean isShownModelPart(final PlayerClothes part) {
        byn modelPart = null;
        switch (part) {
            case CAPE: {
                modelPart = byn.a;
                break;
            }
            case JACKET: {
                modelPart = byn.b;
                break;
            }
            case LEFT_SLEEVE: {
                modelPart = byn.c;
                break;
            }
            case RIGHT_SLEEVE: {
                modelPart = byn.d;
                break;
            }
            case LEFT_PANTS_LEG: {
                modelPart = byn.e;
                break;
            }
            case RIGHT_PANTS_LEG: {
                modelPart = byn.f;
                break;
            }
            case HAT: {
                modelPart = byn.g;
                break;
            }
            default: {
                return true;
            }
        }
        return this.labyMod$getPlayer().a(modelPart);
    }
    
    @Override
    public GameProfile profile() {
        final NetworkPlayerInfo playerInfo = this.networkPlayerInfo();
        if (playerInfo != null) {
            return playerInfo.profile();
        }
        return (GameProfile)this.labyMod$getPlayer().fI();
    }
    
    @Override
    public FoodData foodData() {
        return (FoodData)this.labyMod$getPlayer().fT();
    }
    
    @Override
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }
    
    @Override
    public int getExperienceLevel() {
        return this.labyMod$getPlayer().cc;
    }
    
    @Override
    public int getTotalExperience() {
        return this.labyMod$getPlayer().cd;
    }
    
    @Override
    public float getExperienceProgress() {
        return this.labyMod$getPlayer().ce;
    }
    
    @Override
    public int getExperienceNeededForNextLevel() {
        return this.labyMod$getPlayer().fS();
    }
    
    @Override
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }
    
    private byj labyMod$abilities() {
        return this.labyMod$getPlayer().fK();
    }
    
    private fhh labyMod$getPlayer() {
        return (fhh)this;
    }
    
    @Override
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(this.labyMod$getPlayer().cs());
        }
        return this.labyMod$gameUser;
    }
    
    @Inject(method = { "getFieldOfViewModifier" }, at = { @At("RETURN") }, cancellable = true)
    private void modifyFieldOfViewModifier(final CallbackInfoReturnable<Float> cir) {
        Float fieldOfView = (Float)cir.getReturnValue();
        final FieldOfViewModifierEvent fieldOfViewModifierEvent = new FieldOfViewModifierEvent(fieldOfView);
        Laby.fireEvent(fieldOfViewModifierEvent);
        fieldOfView = fieldOfViewModifierEvent.getFieldOfView();
        cir.setReturnValue((Object)fieldOfView);
    }
}
