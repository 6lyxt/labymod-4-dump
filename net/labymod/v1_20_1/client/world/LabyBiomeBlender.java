// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class LabyBiomeBlender implements clp
{
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private cmm level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public LabyBiomeBlender setLevel(final cmm level) {
        this.level = level;
        return this;
    }
    
    public LabyBiomeBlender setArea(final int minX, final int minZ, final int maxX, final int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        return this;
    }
    
    public float a(@NotNull final ha direction, final boolean shade) {
        return this.level.a(direction, shade);
    }
    
    @NotNull
    public dwt s_() {
        return this.level.s_();
    }
    
    @Nullable
    public czn c_(@NotNull final gu blockPos) {
        return this.level.c_(blockPos);
    }
    
    @NotNull
    public dcb a_(@NotNull final gu blockPos) {
        return this.level.a_(blockPos);
    }
    
    @NotNull
    public dxe b_(@NotNull final gu blockPos) {
        return this.level.b_(blockPos);
    }
    
    public int D_() {
        return this.level.D_();
    }
    
    public int C_() {
        return this.level.C_();
    }
    
    public int a(@NotNull final gu blockPos, @NotNull final clx resolver) {
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final int[] rgb = new int[3];
        int total = 0;
        final gu.a currentPos = new gu.a();
        for (int xIndex = this.minX; xIndex <= this.maxX; ++xIndex) {
            total = this.processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        return (total <= 0) ? -1 : ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }
    
    private int processZAxis(final int x, final int y, final int z, final gu.a currentPos, final clx resolver, int total, final int[] rgb, final int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; ++zIndex) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = this.blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }
    
    private int blendColors(final gu.a currentPos, final clx resolver, int total, final int[] rgb) {
        final he<cnk> holder = (he<cnk>)this.level.s((gu)currentPos);
        final cnk biome = (holder == null) ? null : ((cnk)holder.a());
        if (biome != null) {
            final int color = resolver.getColor(biome, (double)currentPos.u(), (double)currentPos.w());
            final int n = 0;
            rgb[n] += ColorFormat.ARGB32.red(color);
            final int n2 = 1;
            rgb[n2] += ColorFormat.ARGB32.green(color);
            final int n3 = 2;
            rgb[n3] += ColorFormat.ARGB32.blue(color);
            ++total;
        }
        return total;
    }
}
