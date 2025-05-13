// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.HitResult;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.world.phys.hit.BlockHitResult;

@Implements({ @Interface(iface = BlockHitResult.class, prefix = "blockHitResult$", remap = Interface.Remap.NONE) })
@Mixin({ dcj.class })
public abstract class MixinBlockHitResult extends MixinHitResult implements BlockHitResult
{
    private Direction labyMod$direction;
    private FloatVector3 labyMod$blockPosition;
    @Shadow
    @Final
    private fx c;
    @Shadow
    @Final
    private gc b;
    
    @Shadow
    public abstract boolean d();
    
    @Shadow
    public abstract dcl.a c();
    
    @Override
    public FloatVector3 getBlockPosition() {
        if (this.labyMod$blockPosition == null && this.c != null) {
            this.labyMod$blockPosition = new FloatVector3((float)this.c.u(), (float)this.c.v(), (float)this.c.w());
        }
        return this.labyMod$blockPosition;
    }
    
    @Override
    public Direction getBlockDirection() {
        if (this.labyMod$direction == null && this.b != null) {
            this.labyMod$direction = MinecraftUtil.fromMinecraft(this.b);
        }
        return this.labyMod$direction;
    }
    
    @Intrinsic
    public boolean blockHitResult$isInside() {
        return this.d();
    }
    
    @Override
    public HitResult.HitType type() {
        return this.labyMod$getHitType(this.c());
    }
}
