// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.labymod.api.Laby;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.entity.player.GameMode;
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
import net.labymod.v1_16_5.mixins.client.entity.MixinLivingEntity;

@Mixin({ dzj.class })
@Implements({ @Interface(iface = Player.class, prefix = "player$", remap = Interface.Remap.NONE) })
public abstract class MixinAbstractLocalPlayer extends MixinLivingEntity implements Player
{
    private final PlayerAbilities labyMod$playerAbilities;
    private final Position labyMod$chasingPosition;
    private final Position labyMod$previousChasingPosition;
    private GameUser labyMod$gameUser;
    
    public MixinAbstractLocalPlayer() {
        this.labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> this.labyMod$abilities().a, value -> this.labyMod$abilities().a = value), new BooleanAbilityOption(() -> this.labyMod$abilities().b, value -> this.labyMod$abilities().b = value), new BooleanAbilityOption(() -> this.labyMod$abilities().c, value -> this.labyMod$abilities().c = value), new BooleanAbilityOption(() -> this.labyMod$abilities().d, value -> this.labyMod$abilities().d = value), new BooleanAbilityOption(() -> this.labyMod$abilities().e, value -> this.labyMod$abilities().e = value), new FloatAbilityOption(() -> this.labyMod$abilities().a(), value -> this.labyMod$abilities().a(value)), new FloatAbilityOption(() -> this.labyMod$abilities().b(), value -> this.labyMod$abilities().b(value)));
        this.labyMod$chasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().by, x -> this.labyMod$getPlayer().by = x, () -> this.labyMod$getPlayer().bz, y -> this.labyMod$getPlayer().bz = y, () -> this.labyMod$getPlayer().bA, z -> this.labyMod$getPlayer().bA = z);
        this.labyMod$previousChasingPosition = new DynamicPosition(() -> this.labyMod$getPlayer().bv, x -> this.labyMod$getPlayer().bv = x, () -> this.labyMod$getPlayer().bw, y -> this.labyMod$getPlayer().bw = y, () -> this.labyMod$getPlayer().bx, z -> this.labyMod$getPlayer().bx = z);
    }
    
    @Override
    public boolean isSwingingHand() {
        return this.labyMod$getPlayer().ai;
    }
    
    @Override
    public float getArmSwingProgress() {
        return this.labyMod$getPlayer().as;
    }
    
    @Override
    public float getPreviousArmSwingProgress() {
        return this.labyMod$getPlayer().ar;
    }
    
    @Override
    public boolean isDead() {
        return !this.labyMod$getPlayer().aX();
    }
    
    @Override
    public String getName() {
        return this.labyMod$getPlayer().eA().getName();
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
        return this.labyMod$getPlayer().bt;
    }
    
    @Override
    public float getPreviousCameraYaw() {
        return this.labyMod$getPlayer().bs;
    }
    
    @Override
    public float getPreviousWalkDistance() {
        return this.labyMod$getPlayer().z;
    }
    
    @Override
    public float getWalkDistance() {
        return this.labyMod$getPlayer().A;
    }
    
    @Override
    public float getPreviousRotationHeadYaw() {
        return this.labyMod$getPlayer().aD;
    }
    
    @Override
    public float getRotationHeadYaw() {
        return this.labyMod$getPlayer().aC;
    }
    
    @Override
    public float getRenderTick(final float partialTicks) {
        return this.labyMod$getPlayer().K + partialTicks;
    }
    
    @Override
    public double getSprintingSpeed() {
        return this.labyMod$getPlayer().b(arl.d);
    }
    
    @Override
    public ResourceLocation skinTexture() {
        return (ResourceLocation)this.labyMod$getPlayer().o();
    }
    
    @Nullable
    @Override
    public ResourceLocation getCloakTexture() {
        final vk location = this.labyMod$getPlayer().p();
        return (location == null) ? null : ((ResourceLocation)location);
    }
    
    @Override
    public boolean isSlim() {
        return this.labyMod$getPlayer().u().equals("slim");
    }
    
    @Override
    public boolean isWearingTop() {
        return this.labyMod$getPlayer().a(aqf.e);
    }
    
    @Override
    public float getPreviousLimbSwingStrength() {
        return this.labyMod$getPlayer().au;
    }
    
    @Override
    public float getLimbSwingStrength() {
        return this.labyMod$getPlayer().av;
    }
    
    @Override
    public float getLimbSwing() {
        return this.labyMod$getPlayer().aw;
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
        bfx modelPart = null;
        switch (part) {
            case CAPE: {
                modelPart = bfx.a;
                break;
            }
            case JACKET: {
                modelPart = bfx.b;
                break;
            }
            case LEFT_SLEEVE: {
                modelPart = bfx.c;
                break;
            }
            case RIGHT_SLEEVE: {
                modelPart = bfx.d;
                break;
            }
            case LEFT_PANTS_LEG: {
                modelPart = bfx.e;
                break;
            }
            case RIGHT_PANTS_LEG: {
                modelPart = bfx.f;
                break;
            }
            case HAT: {
                modelPart = bfx.g;
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
        return (GameProfile)this.labyMod$getPlayer().eA();
    }
    
    @Override
    public FoodData foodData() {
        return (FoodData)this.labyMod$getPlayer().eI();
    }
    
    @Override
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }
    
    @Override
    public int getExperienceLevel() {
        return this.labyMod$getPlayer().bD;
    }
    
    @Override
    public int getTotalExperience() {
        return this.labyMod$getPlayer().bE;
    }
    
    @Override
    public float getExperienceProgress() {
        return this.labyMod$getPlayer().bF;
    }
    
    @Override
    public int getExperienceNeededForNextLevel() {
        return this.labyMod$getPlayer().eH();
    }
    
    @Override
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }
    
    private bft labyMod$abilities() {
        return this.labyMod$getPlayer().bC;
    }
    
    private dzj labyMod$getPlayer() {
        return (dzj)this;
    }
    
    @Override
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(this.labyMod$getPlayer().bS());
        }
        return this.labyMod$gameUser;
    }
    
    @Inject(method = { "getFieldOfViewModifier" }, at = { @At("RETURN") }, cancellable = true)
    private void modifyFieldOfViewModifier(final CallbackInfoReturnable<Float> cir) {
        float fieldOfView = (float)cir.getReturnValue();
        final FieldOfViewModifierEvent fieldOfViewModifierEvent = new FieldOfViewModifierEvent(fieldOfView);
        Laby.fireEvent(fieldOfViewModifierEvent);
        fieldOfView = fieldOfViewModifierEvent.getFieldOfView();
        cir.setReturnValue((Object)fieldOfView);
    }
}
