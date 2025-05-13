// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class LabyBiomeBlender implements css
{
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private ctp level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public LabyBiomeBlender setLevel(final ctp level) {
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
    
    public float a(@NotNull final ic direction, final boolean shade) {
        return this.level.a(direction, shade);
    }
    
    @NotNull
    public eeg z_() {
        return this.level.z_();
    }
    
    @Nullable
    public dgv c_(@NotNull final hx blockPos) {
        return this.level.c_(blockPos);
    }
    
    @NotNull
    public djh a_(@NotNull final hx blockPos) {
        return this.level.a_(blockPos);
    }
    
    @NotNull
    public eer b_(@NotNull final hx blockPos) {
        return this.level.b_(blockPos);
    }
    
    public int K_() {
        return this.level.K_();
    }
    
    public int J_() {
        return this.level.J_();
    }
    
    public int a(@NotNull final hx blockPos, @NotNull final cta resolver) {
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final int[] rgb = new int[3];
        int total = 0;
        final hx.a currentPos = new hx.a();
        for (int xIndex = this.minX; xIndex <= this.maxX; ++xIndex) {
            total = this.processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        return (total <= 0) ? -1 : ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }
    
    private int processZAxis(final int x, final int y, final int z, final hx.a currentPos, final cta resolver, int total, final int[] rgb, final int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; ++zIndex) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = this.blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }
    
    private int blendColors(final hx.a currentPos, final cta resolver, int total, final int[] rgb) {
        final ih<cuo> holder = (ih<cuo>)this.level.t((hx)currentPos);
        final cuo biome = (holder == null) ? null : ((cuo)holder.a());
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
