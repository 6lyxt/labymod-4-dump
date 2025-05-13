// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.world.level.chunks;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.labymod.v1_12_2.client.world.VersionedBlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ amu.class })
public class MixinLevel
{
    @Shadow
    @Final
    public boolean G;
    
    @Inject(method = { "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z" }, at = { @At("RETURN") })
    private void labyMod$fireChunkBlockUpdateEvent(final et pos, final awt newState, final int flags, final CallbackInfoReturnable<Boolean> cir) {
        if (!this.G) {
            return;
        }
        final BlockState state = new VersionedBlockState(newState, pos.p(), pos.q(), pos.r());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
