// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.phys;

import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.phys.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.phys.hit.EntityHitResult;

@Mixin({ dmx.class })
@Implements({ @Interface(iface = EntityHitResult.class, prefix = "entityHitResult$", remap = Interface.Remap.NONE) })
public abstract class MixinEntityHitResult extends MixinHitResult implements EntityHitResult
{
    @Shadow
    @Final
    private atg b;
    
    @Shadow
    public abstract dmy.a c();
    
    @Override
    public HitResult.HitType type() {
        return this.labyMod$getHitType(this.c());
    }
    
    @Intrinsic
    public Entity entityHitResult$getEntity() {
        if (this.b == null) {
            return null;
        }
        return (Entity)this.b;
    }
}
