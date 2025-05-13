// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.entity;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_17_1.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.v1_17_1.client.util.MinecraftUtil;
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

@Mixin({ atu.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity
{
    private final Map<asy, PotionEffect> labyMod$activePotionEffects;
    @Shadow
    public int aK;
    @Shadow
    public int aN;
    @Mutable
    @Shadow
    @Final
    private Map<asy, ata> bR;
    
    public MixinLivingEntity() {
        this.labyMod$activePotionEffects = Maps.newHashMap();
    }
    
    @Shadow
    public abstract asa shadow$eG();
    
    @Shadow
    public abstract int eI();
    
    @Shadow
    public abstract bqq b(final atl p0);
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.labyMod$getLivingEntity().dU();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.labyMod$getLivingEntity().ef();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.labyMod$getLivingEntity().eC();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.labyMod$getLivingEntity().eJ();
    }
    
    @Override
    public Hand getUsedItemHand() {
        return (this.shadow$eG() == asa.a) ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
    
    @Override
    public boolean isMainHandRight() {
        return this.labyMod$getLivingEntity().eE() == atp.b;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().el());
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return (this.labyMod$getLivingEntity().eE() == atp.b) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return (this.labyMod$getLivingEntity().eE() == atp.a) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().em());
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(this.labyMod$getLivingEntity(), equipmentSlot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aX;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aY;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aX = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aY = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aZ;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().ba;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aZ = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().ba = rotationY;
    }
    
    @Override
    public boolean isWearingElytra() {
        final bqq itemStack = this.b(atl.e);
        return itemStack.a(bqs.lT);
    }
    
    @Override
    public int getHurtTime() {
        return this.aK;
    }
    
    @Override
    public int getDeathTime() {
        return this.aN;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof bhq;
    }
    
    @ModifyConstant(method = { "tick" }, constant = { @Constant(floatValue = 180.0f, ordinal = 0) })
    private float labyMod$oldBackwards(final float value) {
        final BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return (animation == null) ? value : animation.modify(value);
    }
    
    private atu labyMod$getLivingEntity() {
        return (atu)this;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final atk param0, final bwq param1, final CallbackInfo ci) {
        this.bR = new WatchableHashMap<asy, ata>(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }
    
    @Shadow
    public abstract boolean shadow$eV();
    
    @Shadow
    public abstract int eJ();
    
    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return this.shadow$eV();
    }
}
