// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.Laby;
import java.util.function.IntSupplier;
import java.util.function.BiFunction;

public class UniformDynamicSampler extends UniformSampler
{
    public static BiFunction<String, Integer, UniformDynamicSampler> LIGHTMAP_SAMPLER;
    private final IntSupplier textureSupplier;
    private int textureId;
    
    public UniformDynamicSampler(final String name, final int samplerSlot, final IntSupplier textureSupplier) {
        super(name, samplerSlot);
        this.textureId = -1;
        this.textureSupplier = textureSupplier;
    }
    
    @Override
    public void upload(final boolean force) {
        final int textureId = this.textureSupplier.getAsInt();
        if (textureId != this.textureId) {
            this.set(this.textureId = textureId);
        }
        super.upload(force);
    }
    
    static {
        UniformDynamicSampler.LIGHTMAP_SAMPLER = (BiFunction<String, Integer, UniformDynamicSampler>)((name, samplerSlot) -> new UniformDynamicSampler(name, samplerSlot, () -> Laby.labyAPI().gfxRenderPipeline().getLightmapTexture().getTextureId()));
    }
}
