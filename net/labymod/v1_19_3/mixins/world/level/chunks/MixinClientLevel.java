// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.world.level.chunks;

import net.labymod.api.client.world.ClientWorld;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import net.labymod.api.client.world.chunk.Chunk;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eyz.class })
public class MixinClientLevel
{
    @Inject(method = { "unload" }, at = { @At("TAIL") })
    private void labyMod$fireChunkUnloadEvent(final dba chunk, final CallbackInfo ci) {
        Laby.fireEvent(ChunkEvent.unload((Chunk)chunk));
    }
    
    @Inject(method = { "onChunkLoaded" }, at = { @At("TAIL") })
    private void labyMod$fireChunkLoadEvent(final cjd chunkPos, final CallbackInfo ci) {
        final ClientWorld level = Laby.references().clientWorld();
        Laby.fireEvent(ChunkEvent.load(level.getChunk(chunkPos.e, chunkPos.f)));
    }
}
