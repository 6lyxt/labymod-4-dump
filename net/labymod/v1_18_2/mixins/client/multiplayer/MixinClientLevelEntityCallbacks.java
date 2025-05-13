// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.multiplayer;

import net.labymod.core.main.LabyMod;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.client.world.DefaultClientWorld;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks" })
public class MixinClientLevelEntityCallbacks
{
    @Inject(method = { "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;)V" }, at = { @At("TAIL") })
    private void labyMod$init(final CallbackInfo ci) {
        ((DefaultClientWorld)this.getClientWorld()).resetEntityList();
    }
    
    @Inject(method = { "onTrackingStart(Lnet/minecraft/world/entity/Entity;)V" }, at = { @At("TAIL") })
    private void labyMod$startEntityTracking(final axk entity, final CallbackInfo ci) {
        ((DefaultClientWorld)this.getClientWorld()).addEntity((Entity)entity);
    }
    
    @Inject(method = { "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V" }, at = { @At("TAIL") })
    private void labyMod$stopEntityTracking(final axk entity, final CallbackInfo ci) {
        ((DefaultClientWorld)this.getClientWorld()).removeEntity((Entity)entity);
    }
    
    private ClientWorld getClientWorld() {
        return LabyMod.getInstance().minecraft().clientWorld();
    }
}
