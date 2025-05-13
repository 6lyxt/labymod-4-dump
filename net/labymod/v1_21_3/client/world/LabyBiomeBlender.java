// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class LabyBiomeBlender implements dgk
{
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private dhi level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public LabyBiomeBlender setLevel(final dhi level) {
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
    
    public float a(@NotNull final jm direction, final boolean shade) {
        return this.level.a(direction, shade);
    }
    
    @NotNull
    public etm C_() {
        return this.level.C_();
    }
    
    @Nullable
    public dux c_(@NotNull final jh blockPos) {
        return this.level.c_(blockPos);
    }
    
    @NotNull
    public dxv a_(@NotNull final jh blockPos) {
        return this.level.a_(blockPos);
    }
    
    @NotNull
    public etx b_(@NotNull final jh blockPos) {
        return this.level.b_(blockPos);
    }
    
    public int M_() {
        return this.level.M_();
    }
    
    public int a(@NotNull final jh blockPos, @NotNull final dgs resolver) {
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final int[] rgb = new int[3];
        int total = 0;
        final jh.a currentPos = new jh.a();
        for (int xIndex = this.minX; xIndex <= this.maxX; ++xIndex) {
            total = this.processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        return (total <= 0) ? -1 : ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }
    
    public int L_() {
        return this.level.L_();
    }
    
    private int processZAxis(final int x, final int y, final int z, final jh.a currentPos, final dgs resolver, int total, final int[] rgb, final int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; ++zIndex) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = this.blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }
    
    private int blendColors(final jh.a currentPos, final dgs resolver, int total, final int[] rgb) {
        final jq<dik> holder = (jq<dik>)this.level.t((jh)currentPos);
        final dik biome = (holder == null) ? null : ((dik)holder.a());
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
