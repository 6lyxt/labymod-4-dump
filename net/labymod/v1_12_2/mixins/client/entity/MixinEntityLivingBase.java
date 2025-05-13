// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.entity;

import java.util.Collection;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_12_2.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import net.labymod.api.client.world.effect.PotionEffect;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.LivingEntity;

@Mixin({ vp.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntityLivingBase extends MixinEntity implements LivingEntity
{
    @Shadow
    public int ay;
    @Shadow
    public int aB;
    private final Map<uz, PotionEffect> labyMod$activePotions;
    @Mutable
    @Shadow
    @Final
    private Map<uz, va> bu;
    
    public MixinEntityLivingBase() {
        this.labyMod$activePotions = new HashMap<uz, PotionEffect>();
    }
    
    @Shadow
    public abstract boolean cz();
    
    @Shadow
    public abstract float cd();
    
    @Shadow
    public abstract float cj();
    
    @Shadow
    public abstract float cD();
    
    @Shadow
    public abstract int cL();
    
    @Shadow
    public abstract aip b(final ub p0);
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final amu world, final CallbackInfo ci) {
        this.bu = new WatchableHashMap<uz, va>(new WatchableActivePotionMap(this.labyMod$activePotions));
    }
    
    @ModifyConstant(method = { "onUpdate" }, constant = { @Constant(floatValue = 180.0f, ordinal = 0) })
    private float labyMod$oldBackwards(final float value) {
        final BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return (animation == null) ? value : animation.modify(value);
    }
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.cd();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.cj();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.cD();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.cL();
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.b(ub.a));
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.b(ub.b));
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return this.getMainHandItemStack();
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return this.getMainHandItemStack();
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final EquipmentSpot equipmentSpot) {
        return MinecraftUtil.fromMinecraftSlot((vp)this, equipmentSpot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aN;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aO;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aN = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aO = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aP;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().aQ;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aP = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aQ = rotationY;
    }
    
    @Override
    public boolean isWearingElytra() {
        final ItemStack itemStack = this.getEquipmentItemStack(EquipmentSpot.CHEST);
        return itemStack.getAsItem() == air.cS;
    }
    
    @Override
    public int getHurtTime() {
        return this.ay;
    }
    
    @Override
    public int getDeathTime() {
        return this.aB;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof acw;
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotions.values();
    }
    
    @Override
    public boolean isSleeping() {
        return this.cz();
    }
    
    private vp labyMod$getLivingEntity() {
        return (vp)this;
    }
}
