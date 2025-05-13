// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.multiplayer;

import net.labymod.api.event.client.world.EntityDestructEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bdb.class })
public class MixinWorldClient
{
    @Inject(method = { "spawnEntityInWorld" }, at = { @At("TAIL") })
    private void labyMod$addEntity(final pk entity, final CallbackInfoReturnable<Boolean> cir) {
        Laby.fireEvent(new EntitySpawnEvent(entity.F(), (Entity)entity));
    }
    
    @Inject(method = { "removeEntity(Lnet/minecraft/entity/Entity;)V" }, at = { @At("HEAD") })
    private void labyMod$removeEntity(final pk entity, final CallbackInfo ci) {
        Laby.fireEvent(new EntityDestructEvent((Entity)entity));
    }
}
