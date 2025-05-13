// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world.chunk;

import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.Map;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.v1_12_2.client.world.VersionedBlockState;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.client.world.chunk.HeightmapType;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.v1_12_2.client.world.VersionedHeightmap;
import net.labymod.api.client.world.chunk.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.chunk.Chunk;

@Mixin({ axw.class })
@Implements({ @Interface(iface = Chunk.class, prefix = "chunk$", remap = Interface.Remap.NONE) })
public abstract class MixinChunk implements Chunk
{
    @Shadow
    @Final
    private axx[] f;
    @Shadow
    @Final
    public int b;
    @Shadow
    @Final
    public int c;
    @Shadow
    private boolean j;
    private final Heightmap labyMod$heightmap;
    
    public MixinChunk() {
        this.labyMod$heightmap = new VersionedHeightmap((axw)this);
    }
    
    @Shadow
    public abstract axx[] h();
    
    @Intrinsic
    public int chunk$getChunkX() {
        return this.b;
    }
    
    @Intrinsic
    public int chunk$getChunkZ() {
        return this.c;
    }
    
    @Override
    public int getAbsoluteBlockX(final int chunkBlockX) {
        return this.b << 4 + chunkBlockX;
    }
    
    @Override
    public int getAbsoluteBlockZ(final int chunkBlockZ) {
        return this.c << 4 + chunkBlockZ;
    }
    
    @Override
    public Heightmap heightmap(final HeightmapType type) {
        Preconditions.notNull(type, "type");
        return this.labyMod$heightmap;
    }
    
    @Intrinsic
    public boolean chunk$isLoaded() {
        return this.j;
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final int section = y >> 4;
        final int absoluteBlockX = this.getAbsoluteBlockX(x);
        final int absoluteBlockZ = this.getAbsoluteBlockZ(z);
        if (section < 0 || section >= this.f.length) {
            return new VersionedBlockState(aox.a.t(), absoluteBlockX, y, absoluteBlockZ);
        }
        final axx storageArray = this.f[section];
        if (storageArray == null) {
            return new VersionedBlockState(aox.a.t(), absoluteBlockX, y, absoluteBlockZ);
        }
        final awt state = storageArray.a(x, y & 0xF, z);
        return new VersionedBlockState(state, absoluteBlockX, y, absoluteBlockZ);
    }
    
    @Override
    public BlockEntity[] getBlockEntities() {
        final Map<et, avj> map = ((axw)this).s();
        final BlockEntity[] blockEntities = new BlockEntity[map.size()];
        int index = 0;
        for (final Map.Entry<et, avj> entry : map.entrySet()) {
            blockEntities[index++] = (BlockEntity)entry.getValue();
        }
        return blockEntities;
    }
    
    @Override
    public int getHeightBasedOnSection(final int startY) {
        final axx[] sections = this.h();
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
            final axx section = sections[index];
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
            final axx section = sections[index];
            if (!section.a()) {
                result = bottomY + (index << 4) + 15;
                return result;
            }
        }
        return result;
    }
}
