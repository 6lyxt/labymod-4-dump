// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.multiplayer;

import net.labymod.core.main.LabyMod;
import net.labymod.core.client.world.DefaultClientWorld;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.world.EntityDestructEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dwt.class })
public class MixinClientLevel
{
    @Insert(method = { "addEntity(ILnet/minecraft/world/entity/Entity;)V" }, at = @At("TAIL"))
    private void labyMod$addEntity(final int entityId, final aqa entity, final InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entityId, (Entity)entity));
        this.clientWorld().addEntity((Entity)entity);
    }
    
    @Inject(method = { "onEntityRemoved" }, at = { @At("TAIL") })
    private void labyMod$removeEntity(final aqa entity, final CallbackInfo ci) {
        Laby.fireEvent(new EntityDestructEvent((Entity)entity));
        this.clientWorld().removeEntity((Entity)entity);
    }
    
    private DefaultClientWorld clientWorld() {
        return (DefaultClientWorld)LabyMod.getInstance().minecraft().clientWorld();
    }
}
