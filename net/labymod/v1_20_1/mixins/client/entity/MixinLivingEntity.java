// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.entity;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_20_1.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.BackwardsOldAnimation;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
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

@Mixin({ bfz.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity
{
    private final Map<bey, PotionEffect> labyMod$activePotionEffects;
    @Shadow
    public int aL;
    @Shadow
    public int aN;
    @Mutable
    @Shadow
    @Final
    private Map<bey, bfa> bR;
    
    public MixinLivingEntity() {
        this.labyMod$activePotionEffects = Maps.newHashMap();
    }
    
    @Shadow
    public abstract bdw shadow$fj();
    
    @Shadow
    public abstract int fl();
    
    @Shadow
    public abstract cfz c(final bfo p0);
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.labyMod$getLivingEntity().er();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.labyMod$getLivingEntity().eI();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.labyMod$getLivingEntity().ff();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return this.labyMod$getLivingEntity().fm();
    }
    
    @Override
    public Hand getUsedItemHand() {
        return (this.shadow$fj() == bdw.a) ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
    
    @Override
    public boolean isMainHandRight() {
        return this.labyMod$getLivingEntity().fh() == bft.b;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eO());
    }
    
    @Override
    public ItemStack getRightHandItemStack() {
        return (this.labyMod$getLivingEntity().fh() == bft.b) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getLeftHandItemStack() {
        return (this.labyMod$getLivingEntity().fh() == bft.a) ? this.getMainHandItemStack() : this.getOffHandItemStack();
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.labyMod$getLivingEntity().eP());
    }
    
    @Override
    public ItemStack getEquipmentItemStack(final EquipmentSpot equipmentSlot) {
        return MinecraftUtil.fromMinecraftSlot(this.labyMod$getLivingEntity(), equipmentSlot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aV;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aW;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aV = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aW = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aX;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().aY;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aX = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aY = rotationY;
    }
    
    @Override
    public boolean isWearingElytra() {
        final cfz itemStack = this.c(bfo.e);
        return itemStack.a(cgc.nh);
    }
    
    @Override
    public int getHurtTime() {
        return this.aL;
    }
    
    @Override
    public int getDeathTime() {
        return this.aN;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof bvu;
    }
    
    @ModifyConstant(method = { "tick" }, constant = { @Constant(floatValue = 180.0f, ordinal = 0) })
    private float labyMod$oldBackwards(final float value) {
        final BackwardsOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("backwards");
        return (animation == null) ? value : animation.modify(value);
    }
    
    private bfz labyMod$getLivingEntity() {
        return (bfz)this;
    }
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final bfn param0, final cmm param1, final CallbackInfo ci) {
        this.bR = new WatchableHashMap<bey, bfa>(new WatchableActivePotionMap(this.labyMod$activePotionEffects));
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotionEffects.values();
    }
    
    @Shadow
    public abstract boolean shadow$fy();
    
    @Intrinsic
    public boolean livingEntity$isSleeping() {
        return this.shadow$fy();
    }
}
