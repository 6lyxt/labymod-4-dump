// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.world.level.chunks;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import net.labymod.api.client.world.chunk.Chunk;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bcz.class })
public class MixinChunkProviderClient
{
    @WrapOperation(method = { "unloadChunk" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;onChunkUnload()V") })
    private void labyMod$fireChunkUnloadEvent(final amy instance, final Operation<Void> original) {
        original.call(new Object[] { instance });
        Laby.fireEvent(ChunkEvent.unload((Chunk)instance));
    }
    
    @Inject(method = { "loadChunk" }, at = { @At("TAIL") })
    private void labyMod$fireChunkEvent(final int chunkX, final int chunkZ, final CallbackInfoReturnable<amy> cir) {
        Laby.fireEvent(ChunkEvent.load((Chunk)cir.getReturnValue()));
    }
}
