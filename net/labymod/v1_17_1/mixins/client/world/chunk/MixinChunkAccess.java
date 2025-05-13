// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.chunk;

import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.Map;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.client.world.chunk.Heightmap;
import net.labymod.api.client.world.chunk.HeightmapType;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.chunk.Chunk;

@Mixin({ cmm.class })
public interface MixinChunkAccess extends Chunk
{
    @Shadow
    cpt a(final cpt.a p0);
    
    @Shadow
    bvv f();
    
    @Shadow
    cmy[] d();
    
    default int getChunkX() {
        return this.f().b;
    }
    
    default int getChunkZ() {
        return this.f().c;
    }
    
    default int getAbsoluteBlockX(final int chunkBlockX) {
        return this.f().a(chunkBlockX);
    }
    
    default int getAbsoluteBlockZ(final int chunkBlockZ) {
        return this.f().b(chunkBlockZ);
    }
    
    default Heightmap heightmap(final HeightmapType type) {
        Preconditions.notNull(type, "type");
        return (Heightmap)this.a(cpt.a.b);
    }
    
    default boolean isLoaded() {
        return true;
    }
    
    default BlockState getBlockState(final int x, final int y, final int z) {
        final int index = y >> 4;
        if (index < 0 || index >= this.d().length) {
            return (BlockState)bzq.a.n();
        }
        final cmy section = this.d()[index];
        if (section == null) {
            return (BlockState)bzq.a.n();
        }
        final int yPos = y & 0xF;
        final BlockState blockState = (BlockState)section.a(x, yPos, z);
        blockState.setCoordinates(this.getAbsoluteBlockX(x), y, this.getAbsoluteBlockZ(z));
        return blockState;
    }
    
    default BlockEntity[] getBlockEntities() {
        if (this instanceof final cmx levelChunk) {
            final Map<gg, ciq> map = levelChunk.z();
            final BlockEntity[] blockEntities = new BlockEntity[map.size()];
            int index = 0;
            for (final Map.Entry<gg, ciq> entry : map.entrySet()) {
                blockEntities[index++] = (BlockEntity)entry.getValue();
            }
            return blockEntities;
        }
        return new BlockEntity[0];
    }
    
    default int getHeightBasedOnSection(final int startY) {
        final cmy[] sections = this.d();
        if (sections.length == 0) {
            return 0;
        }
        final int bottomY = Laby.labyAPI().minecraft().clientWorld().getMinBuildHeight();
        int startSectionIndex = Math.min(startY - bottomY >> 4, sections.length - 1);
        if (startSectionIndex < 0) {
            startSectionIndex = 0;
        }
        int result = 0;
        for (int index = startSectionIndex; index < sections.length; ++index) {
            final cmy section = sections[index];
            if (section != null) {
                if (!section.c()) {
                    result = bottomY + (index << 4) + 15;
                }
            }
        }
        if (result != 0) {
            return result;
        }
        if (startSectionIndex == 0) {
            return 0;
        }
        for (int index = startSectionIndex - 1; index >= 0; --index) {
            final cmy section = sections[index];
            if (!section.c()) {
                result = bottomY + (index << 4) + 15;
                return result;
            }
        }
        return result;
    }
}
