// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.world.phys;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.phys.hit.HitResult;

@Mixin({ edc.class })
public abstract class MixinHitResult implements HitResult
{
    private FloatVector3 labyMod$hitLocation;
    
    @Insert(method = { "<init>(Lnet/minecraft/world/phys/Vec3;)V" }, at = @At("TAIL"))
    private void labyMod$createApiVector(final ede vector, final InsertInfo info) {
        this.labyMod$hitLocation = new FloatVector3((float)vector.c, (float)vector.d, (float)vector.e);
    }
    
    @Override
    public FloatVector3 location() {
        return this.labyMod$hitLocation;
    }
    
    protected HitType labyMod$getHitType(final edc.a type) {
        switch (type) {
            case a: {
                return HitType.MISS;
            }
            case b: {
                return HitType.BLOCK;
            }
            case c: {
                return HitType.ENTITY;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
            }
        }
    }
}
