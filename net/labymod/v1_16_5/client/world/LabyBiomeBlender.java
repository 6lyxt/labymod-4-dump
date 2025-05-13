// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.world;

import net.labymod.api.util.color.format.ColorFormat;
import net.minecraft.world.level.ColorResolver;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class LabyBiomeBlender implements bra
{
    private static final int RED_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int BLUE_INDEX = 2;
    private static final int COMPONENTS = 3;
    private brx level;
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    public LabyBiomeBlender setLevel(final brx level) {
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
    
    public float a(@NotNull final gc direction, final boolean shade) {
        return this.level.a(direction, shade);
    }
    
    @NotNull
    public cuo e() {
        return this.level.e();
    }
    
    @Nullable
    public ccj c(@NotNull final fx blockPos) {
        return this.level.c(blockPos);
    }
    
    @NotNull
    public ceh d_(@NotNull final fx blockPos) {
        return this.level.d_(blockPos);
    }
    
    @NotNull
    public cux b(@NotNull final fx blockPos) {
        return this.level.b(blockPos);
    }
    
    public int a(@NotNull final fx blockPos, @NotNull final ColorResolver resolver) {
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final int[] rgb = new int[3];
        int total = 0;
        final fx.a currentPos = new fx.a();
        for (int xIndex = this.minX; xIndex <= this.maxX; ++xIndex) {
            total = this.processZAxis(x, y, z, currentPos, resolver, total, rgb, xIndex);
        }
        return (total <= 0) ? -1 : ColorFormat.ARGB32.pack(rgb[0] / total, rgb[1] / total, rgb[2] / total);
    }
    
    private int processZAxis(final int x, final int y, final int z, final fx.a currentPos, final ColorResolver resolver, int total, final int[] rgb, final int xIndex) {
        for (int zIndex = this.minZ; zIndex < this.maxZ; ++zIndex) {
            if (xIndex == 0 || zIndex == 0) {
                currentPos.d(x + xIndex, y, z + zIndex);
                total = this.blendColors(currentPos, resolver, total, rgb);
            }
        }
        return total;
    }
    
    private int blendColors(final fx.a currentPos, final ColorResolver resolver, int total, final int[] rgb) {
        final bsv biome = this.level.v((fx)currentPos);
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
