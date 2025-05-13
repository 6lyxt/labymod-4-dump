// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.world.DefaultClientWorld;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ amu.class })
public class MixinWorld
{
    @Shadow
    @Final
    public boolean G;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void labyMod$init(final bfe p_i528_1_, final bfd p_i528_2_, final aym p_i528_3_, final rl p_i528_4_, final boolean p_i528_5_, final CallbackInfo ci) {
        ((DefaultClientWorld)this.getClientWorld()).resetEntityList();
    }
    
    @Inject(method = { "spawnEntity" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/world/World;loadedEntityList:Ljava/util/List;", shift = At.Shift.AFTER) })
    private void startEntityTracking(final vg entity, final CallbackInfoReturnable<Boolean> cir) {
        ((DefaultClientWorld)this.getClientWorld()).addEntity((Entity)entity);
    }
    
    @Inject(method = { "removeEntity" }, at = { @At("HEAD") })
    private void labyMod$stopEntityTracking$removeEntity(final vg entity, final CallbackInfo ci) {
        ((DefaultClientWorld)this.getClientWorld()).removeEntity((Entity)entity);
    }
    
    private ClientWorld getClientWorld() {
        return LabyMod.getInstance().minecraft().clientWorld();
    }
}
