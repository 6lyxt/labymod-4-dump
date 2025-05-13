// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.world.EntityDestructEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glo.class })
public class MixinClientLevel
{
    @Insert(method = { "addEntity(Lnet/minecraft/world/entity/Entity;)V" }, at = @At("TAIL"))
    private void labyMod$addEntity(final bxe entity, final InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entity.ao(), (Entity)entity));
    }
    
    @Redirect(method = { "removeEntity" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void labyMod$removeEntity(final bxe entity, final bxe.d param0) {
        entity.c(param0);
        Laby.fireEvent(new EntityDestructEvent((Entity)entity));
    }
}
