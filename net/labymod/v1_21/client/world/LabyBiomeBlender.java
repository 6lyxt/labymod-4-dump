// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class LabyBiomeBlender implements dbz
{
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private dcw level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public LabyBiomeBlender setLevel(final dcw level) {
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
    
    public float a(@NotNull final ji direction, final boolean shade) {
        return this.level.a(direction, shade);
    }
    
    @NotNull
    public eot y_() {
        return this.level.y_();
    }
    
    @Nullable
    public dqh c_(@NotNull final jd blockPos) {
        return this.level.c_(blockPos);
    }
    
    @NotNull
    public dtc a_(@NotNull final jd blockPos) {
        return this.level.a_(blockPos);
    }
    
    @NotNull
    public epe b_(@NotNull final jd blockPos) {
        return this.level.b_(blockPos);
    }
    
    public int J_() {
        return this.level.J_();
    }
    
    public int I_() {
        return this.level.I_();
    }
    
    public int a(@NotNull final jd blockPos, @NotNull final dch resolver) {
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final int[] rgb = new int[3];
        int total = 0;
        final jd.a currentPos = new jd.a();
        for (int xIndex = this.minX; xIndex <= this.maxX; ++xIndex) {
            total = this.processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        return (total <= 0) ? -1 : ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }
    
    private int processZAxis(final int x, final int y, final int z, final jd.a currentPos, final dch resolver, int total, final int[] rgb, final int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; ++zIndex) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = this.blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }
    
    private int blendColors(final jd.a currentPos, final dch resolver, int total, final int[] rgb) {
        final jm<ddw> holder = (jm<ddw>)this.level.t((jd)currentPos);
        final ddw biome = (holder == null) ? null : ((ddw)holder.a());
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
