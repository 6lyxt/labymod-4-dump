// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.world.level.chunks;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dkj.class })
public abstract class MixinLevel
{
    @Shadow
    public abstract boolean A_();
    
    @Inject(method = { "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z" }, at = { @At("RETURN") })
    private void labyMod$fireChunkBlockUpdateEvent(final iw pos, final ebq newState, final int flags, final int recursionLeft, final CallbackInfoReturnable<Boolean> cir) {
        if (!this.A_()) {
            return;
        }
        final BlockState state = (BlockState)newState;
        state.setCoordinates(pos.u(), pos.v(), pos.w());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
