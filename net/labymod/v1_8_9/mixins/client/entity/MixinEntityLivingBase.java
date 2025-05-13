// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.entity;

import java.util.Collection;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.core.client.entity.MouseDelayFix;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.watcher.map.WatchableMap;
import net.labymod.core.watcher.map.WatchableHashMap;
import net.labymod.v1_8_9.client.util.WatchableActivePotionMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.HashMap;
import net.labymod.api.client.world.effect.PotionEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import java.util.Map;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.LivingEntity;

@Mixin({ pr.class })
@Implements({ @Interface(iface = LivingEntity.class, prefix = "livingEntity$", remap = Interface.Remap.NONE) })
public abstract class MixinEntityLivingBase extends MixinEntity implements LivingEntity
{
    @Shadow
    public int au;
    @Shadow
    public int ax;
    @Mutable
    @Shadow
    @Final
    private Map<Integer, pf> g;
    private final Map<Integer, PotionEffect> labyMod$activePotions;
    
    public MixinEntityLivingBase() {
        this.labyMod$activePotions = new HashMap<Integer, PotionEffect>();
    }
    
    @Shadow
    public abstract float bn();
    
    @Shadow
    public abstract float bu();
    
    @Shadow
    public abstract float bN();
    
    @Shadow
    public abstract zx bA();
    
    @Shadow
    public abstract zx p(final int p0);
    
    @Shadow
    public abstract boolean bJ();
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$useWatchableMap(final adm world, final CallbackInfo ci) {
        this.g = new WatchableHashMap<Integer, pf>(new WatchableActivePotionMap(this.labyMod$activePotions));
    }
    
    @Inject(method = { "getLook" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$getLook(final float partialTicks, final CallbackInfoReturnable<aui> ci) {
        final pr entity = (pr)this;
        if (entity instanceof bew && MouseDelayFix.isEnabled()) {
            ci.setReturnValue((Object)super.d(partialTicks));
        }
    }
    
    @Intrinsic
    public float livingEntity$getHealth() {
        return this.bn();
    }
    
    @Override
    public float getMaximalHealth() {
        return this.bu();
    }
    
    @Override
    public float getAbsorptionHealth() {
        return this.bN();
    }
    
    @Override
    public int getItemUseDurationTicks() {
        return 0;
    }
    
    @Override
    public ItemStack getMainHandItemStack() {
        return MinecraftUtil.fromMinecraft(this.bA());
    }
    
    @Override
    public ItemStack getOffHandItemStack() {
        return MinecraftUtil.fromMinecraft(MinecraftUtil.AIR);
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
        return MinecraftUtil.fromMinecraftSlot((pr)this, equipmentSpot);
    }
    
    @Override
    public float getBodyRotationY() {
        return this.labyMod$getLivingEntity().aI;
    }
    
    @Override
    public float getPreviousBodyRotationY() {
        return this.labyMod$getLivingEntity().aJ;
    }
    
    @Override
    public void setBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aI = rotationY;
    }
    
    @Override
    public void setPreviousBodyRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aJ = rotationY;
    }
    
    @Override
    public float getHeadRotationY() {
        return this.labyMod$getLivingEntity().aK;
    }
    
    @Override
    public float getPreviousHeadRotationY() {
        return this.labyMod$getLivingEntity().aL;
    }
    
    @Override
    public void setHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aK = rotationY;
    }
    
    @Override
    public void setPreviousHeadRotationY(final float rotationY) {
        this.labyMod$getLivingEntity().aL = rotationY;
    }
    
    @Override
    public int getHurtTime() {
        return this.au;
    }
    
    @Override
    public int getDeathTime() {
        return this.ax;
    }
    
    @Override
    public boolean isHostile() {
        return this instanceof vq;
    }
    
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.labyMod$activePotions.values();
    }
    
    @Override
    public boolean isSleeping() {
        return this.bJ();
    }
    
    private pr labyMod$getLivingEntity() {
        return (pr)this;
    }
}
