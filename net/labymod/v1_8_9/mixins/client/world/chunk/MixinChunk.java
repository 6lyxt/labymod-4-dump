// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.world.chunk;

import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.Map;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.v1_8_9.client.world.VersionedBlockState;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.client.world.chunk.HeightmapType;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.v1_8_9.client.world.VersionedHeightmap;
import net.labymod.api.client.world.chunk.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.chunk.Chunk;

@Mixin({ amy.class })
@Implements({ @Interface(iface = Chunk.class, prefix = "chunk$", remap = Interface.Remap.NONE) })
public abstract class MixinChunk implements Chunk
{
    @Shadow
    @Final
    public int a;
    @Shadow
    @Final
    public int b;
    @Shadow
    private boolean h;
    @Shadow
    @Final
    private amz[] d;
    private final Heightmap labyMod$heightmap;
    
    public MixinChunk() {
        this.labyMod$heightmap = new VersionedHeightmap((amy)this);
    }
    
    @Shadow
    public abstract amz[] h();
    
    @Intrinsic
    public int chunk$getChunkX() {
        return this.a;
    }
    
    @Intrinsic
    public int chunk$getChunkZ() {
        return this.b;
    }
    
    @Override
    public int getAbsoluteBlockX(final int chunkBlockX) {
        return (this.a << 4) + chunkBlockX;
    }
    
    @Override
    public int getAbsoluteBlockZ(final int chunkBlockZ) {
        return (this.b << 4) + chunkBlockZ;
    }
    
    @Override
    public Heightmap heightmap(final HeightmapType type) {
        Preconditions.notNull(type, "type");
        return this.labyMod$heightmap;
    }
    
    @Intrinsic
    public boolean chunk$isLoaded() {
        return this.h;
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final int section = y >> 4;
        final int absoluteBlockX = this.getAbsoluteBlockX(x);
        final int absoluteBlockZ = this.getAbsoluteBlockZ(z);
        if (section < 0 || section >= this.d.length) {
            return new VersionedBlockState(afi.a.Q(), absoluteBlockX, y, absoluteBlockZ);
        }
        final amz storageArray = this.d[section];
        if (storageArray == null) {
            return new VersionedBlockState(afi.a.Q(), absoluteBlockX, y, absoluteBlockZ);
        }
        final alz state = storageArray.a(x, y & 0xF, z);
        return new VersionedBlockState(state, absoluteBlockX, y, absoluteBlockZ);
    }
    
    @Override
    public BlockEntity[] getBlockEntities() {
        final Map<cj, akw> map = ((amy)this).r();
        final BlockEntity[] blockEntities = new BlockEntity[map.size()];
        int index = 0;
        for (final Map.Entry<cj, akw> entry : map.entrySet()) {
            blockEntities[index++] = (BlockEntity)entry.getValue();
        }
        return blockEntities;
    }
    
    @Override
    public int getHeightBasedOnSection(final int startY) {
        final amz[] sections = this.h();
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
            final amz section = sections[index];
            if (section != null) {
                if (!section.a()) {
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
            final amz section = sections[index];
            if (!section.a()) {
                result = bottomY + (index << 4) + 15;
                return result;
            }
        }
        return result;
    }
}
