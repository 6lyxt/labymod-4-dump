// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.entity;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_19_4.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Intrinsic;
import com.google.common.collect.Maps;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.LivingEntity;

@Mixin({ bfx.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity
{
    private final Map<bew, PotionEffect> labyMod$activePotionEffects;
    @Shadow
    public int aJ;
    @Shadow
    public int aL;
    @Mutable
    @Shadow
    @Final
    private Map<bew, bey> bO;
    
    public MixinLivingEntity() {
        this.labyMod$activePotionEffects = Maps.newHashMap();
    }
    
    @Shadow
    public abstract int fh();
    
    @Shadow
    public abstract bdx shadow$ff();
    
    @Shadow
    public abstract cfv c(final bfm p0);
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.labyMod$getLivingEntity().eo();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.labyMod$getLivingEntity().eE();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.labyMod$getLivingEntity().fb();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.labyMod$getLivingEntity().fi();
    }
    
    @Override
    public Hand getUsedItemHand() {
        return (this.shadow$ff() == bdx.a) ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
    
    @Override
    public boolean isMainHandRight() {
        return this.labyMod$getLivingEntity().fd() == bfr.b;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eK());
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return (this.labyMod$getLivingEntity().fd() == bfr.b) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return (this.labyMod$getLivingEntity().fd() == bfr.a) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eL());
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(this.labyMod$getLivingEntity(), equipmentSlot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aT;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aU;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aT = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aU = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aV;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().aW;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aV = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aW = rotationY;
    }
    
    @Override
    public boolean isWearingElytra() {
        final cfv itemStack = this.c(bfm.e);
        return itemStack.a(cfy.nd);
    }
    
    @Override
    public int getHurtTime() {
        return this.aJ;
    }
    
    @Override
    public int getDeathTime() {
        return this.aL;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof bvs;
    }
    
    @ModifyConstant(method = { "tick" }, constant = { @Constant(floatValue = 180.0f, ordinal = 0) })
    private float labyMod$oldBackwards(final float value) {
        final BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return (animation == null) ? value : animation.modify(value);
    }
    
    private bfx labyMod$getLivingEntity() {
        return (bfx)this;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final bfl param0, final cmi param1, final CallbackInfo ci) {
        this.bO = new WatchableHashMap<bew, bey>(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }
    
    @Shadow
    public abstract boolean shadow$fu();
    
    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return this.shadow$fu();
    }
}
