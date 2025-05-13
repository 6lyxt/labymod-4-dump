// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.world.EntityDestructEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gfk.class })
public class MixinClientLevel
{
    @Insert(method = { "addEntity(Lnet/minecraft/world/entity/Entity;)V" }, at = @At("TAIL"))
    private void labyMod$addEntity(final bvk entity, final InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entity.ar(), (Entity)entity));
    }
    
    @Redirect(method = { "removeEntity" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void labyMod$removeEntity(final bvk entity, final bvk.d param0) {
        entity.c(param0);
        Laby.fireEvent(new EntityDestructEvent((Entity)entity));
    }
}
