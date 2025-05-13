// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.entity;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_20_2.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.v1_20_2.client.util.MinecraftUtil;
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

@Mixin({ bjg.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity
{
    private final Map<bib, PotionEffect> labyMod$activePotionEffects;
    @Shadow
    public int aK;
    @Shadow
    public int aM;
    @Mutable
    @Shadow
    @Final
    private Map<bib, bid> bQ;
    
    public MixinLivingEntity() {
        this.labyMod$activePotionEffects = Maps.newHashMap();
    }
    
    @Shadow
    public abstract bgx shadow$fn();
    
    @Shadow
    public abstract int fp();
    
    @Shadow
    public abstract cjf c(final biv p0);
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.labyMod$getLivingEntity().eu();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.labyMod$getLivingEntity().eL();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.labyMod$getLivingEntity().fj();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.labyMod$getLivingEntity().fq();
    }
    
    @Override
    public Hand getUsedItemHand() {
        return (this.shadow$fn() == bgx.a) ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
    
    @Override
    public boolean isMainHandRight() {
        return this.labyMod$getLivingEntity().fl() == bja.b;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eS());
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return (this.labyMod$getLivingEntity().fl() == bja.b) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return (this.labyMod$getLivingEntity().fl() == bja.a) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eT());
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(this.labyMod$getLivingEntity(), equipmentSlot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aU;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aV;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aU = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aV = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aW;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().aX;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aW = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aX = rotationY;
    }
    
    @Override
    public boolean isWearingElytra() {
        final cjf itemStack = this.c(biv.e);
        return itemStack.a(cji.nh);
    }
    
    @Override
    public int getHurtTime() {
        return this.aK;
    }
    
    @Override
    public int getDeathTime() {
        return this.aM;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof bza;
    }
    
    @ModifyConstant(method = { "tick" }, constant = { @Constant(floatValue = 180.0f, ordinal = 0) })
    private float labyMod$oldBackwards(final float value) {
        final BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return (animation == null) ? value : animation.modify(value);
    }
    
    private bjg labyMod$getLivingEntity() {
        return (bjg)this;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final biu param0, final cpv param1, final CallbackInfo ci) {
        this.bQ = new WatchableHashMap<bib, bid>(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }
    
    @Shadow
    public abstract boolean shadow$fC();
    
    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return this.shadow$fC();
    }
}
