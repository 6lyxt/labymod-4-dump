// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.world.chunk;

import java.util.Iterator;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.client.world.chunk.Heightmap;
import net.labymod.api.client.world.chunk.HeightmapType;
import java.util.Map;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.chunk.Chunk;

@Mixin({ dua.class })
public abstract class MixinChunkAccess implements Chunk
{
    @Shadow
    @Final
    protected dul[] m;
    @Shadow
    @Final
    protected dbh d;
    @Shadow
    @Final
    protected Map<iz, dpj> k;
    
    @Shadow
    public abstract dxw a(final dxw.a p0);
    
    @Shadow
    public abstract dul b(final int p0);
    
    @Shadow
    public abstract dul[] d();
    
    @Shadow
    public abstract int I_();
    
    @Override
    public int getChunkX() {
        return this.d.e;
    }
    
    @Override
    public int getChunkZ() {
        return this.d.f;
    }
    
    @Override
    public int getAbsoluteBlockX(final int chunkBlockX) {
        return this.d.a(chunkBlockX);
    }
    
    @Override
    public int getAbsoluteBlockZ(final int chunkBlockZ) {
        return this.d.b(chunkBlockZ);
    }
    
    @Override
    public Heightmap heightmap(final HeightmapType type) {
        Preconditions.notNull(type, "type");
        return (Heightmap)this.a(dxw.a.b);
    }
    
    @Override
    public boolean isLoaded() {
        return true;
    }
    
    @Override
    public BlockState getBlockState(final int x, final int y, final int z) {
        final int index = ((dua)this).e(y);
        if (index < 0 || index >= this.m.length) {
            return (BlockState)dfd.a.o();
        }
        final dul section = this.m[index];
        if (section == null) {
            return (BlockState)dfd.a.o();
        }
        final int yPos = y & 0xF;
        final BlockState blockState = (BlockState)section.a(x, yPos, z);
        blockState.setCoordinates(this.getAbsoluteBlockX(x), y, this.getAbsoluteBlockZ(z));
        return blockState;
    }
    
    @Override
    public BlockEntity[] getBlockEntities() {
        final BlockEntity[] blockEntities = new BlockEntity[this.k.size()];
        int index = 0;
        for (final Map.Entry<iz, dpj> entry : this.k.entrySet()) {
            blockEntities[index++] = (BlockEntity)entry.getValue();
        }
        return blockEntities;
    }
    
    @Override
    public int getHeightBasedOnSection(final int startY) {
        final dul[] sections = this.d();
        if (sections.length == 0) {
            return 0;
        }
        final int bottomY = this.I_();
        int startSectionIndex = Math.min(startY - bottomY >> 4, sections.length - 1);
        if (startSectionIndex < 0) {
            startSectionIndex = 0;
        }
        int result = 0;
        for (int index = startSectionIndex; index < sections.length; ++index) {
            final dul section = sections[index];
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
            final dul section = sections[index];
            if (!section.c()) {
                result = bottomY + (index << 4) + 15;
                return result;
            }
        }
        return result;
    }
}
