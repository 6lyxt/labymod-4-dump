// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.world.level.chunks;

import net.labymod.api.client.world.ClientWorld;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.ChunkEvent;
import net.labymod.api.client.world.chunk.Chunk;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ few.class })
public class MixinClientLevel
{
    @Inject(method = { "unload" }, at = { @At("TAIL") })
    private void labyMod$fireChunkUnloadEvent(final dei chunk, final CallbackInfo ci) {
        Laby.fireEvent(ChunkEvent.unload((Chunk)chunk));
    }
    
    @Inject(method = { "onChunkLoaded" }, at = { @At("TAIL") })
    private void labyMod$fireChunkLoadEvent(final clt chunkPos, final CallbackInfo ci) {
        final ClientWorld level = Laby.references().clientWorld();
        Laby.fireEvent(ChunkEvent.load(level.getChunk(chunkPos.e, chunkPos.f)));
    }
}
