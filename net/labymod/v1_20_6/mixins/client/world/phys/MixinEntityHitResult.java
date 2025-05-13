// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.HitResult;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.phys.hit.EntityHitResult;

@Mixin({ evq.class })
public abstract class MixinEntityHitResult extends MixinHitResult implements EntityHitResult
{
    private Entity labyMod$entity;
    
    @Shadow
    public abstract evr.a c();
    
    @Insert(method = { "<init>(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V" }, at = @At("TAIL"))
    private void createApiEntity(final bsw entity, final evt position, final InsertInfo info) {
        this.labyMod$entity = (Entity)entity;
    }
    
    @Override
    public Entity getEntity() {
        return this.labyMod$entity;
    }
    
    @Override
    public HitResult.HitType type() {
        return this.labyMod$getHitType(this.c());
    }
}
