// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.world.level.chunks;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.labymod.v1_8_9.client.world.VersionedBlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ adm.class })
public class MixinLevel
{
    @Shadow
    @Final
    public boolean D;
    
    @Inject(method = { "setBlockState(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z" }, at = { @At("RETURN") })
    private void labyMod$fireChunkBlockUpdateEvent(final cj pos, final alz newState, final int flags, final CallbackInfoReturnable<Boolean> cir) {
        if (!this.D) {
            return;
        }
        final BlockState state = new VersionedBlockState(newState, pos.n(), pos.o(), pos.p());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
