// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.shader.ShaderTextures;

public class UniformSampler extends UniformInt
{
    private final int samplerIndex;
    private int textureId;
    
    public UniformSampler(final String name, final int samplerIndex) {
        super(name, 1);
        this.samplerIndex = samplerIndex;
    }
    
    public final void set(final int value) {
        this.update(0, value);
    }
    
    @Override
    public void upload(final boolean force) {
        final int shaderTexture = ShaderTextures.getShaderTexture(this.samplerIndex);
        if (this.textureId != shaderTexture) {
            this.set(this.textureId = shaderTexture);
        }
        super.upload(force);
    }
    
    @Override
    protected void updateUniform() {
        this.bridge.uniform1i(this.getLocation(), this.samplerIndex);
        this.bridge.setActiveTexture(this.samplerIndex);
        this.bridge.enableTexture();
        this.bridge.bindTexture2D(TextureId.of(this.buffer.get(0)));
    }
    
    public int getSamplerIndex() {
        return this.samplerIndex;
    }
}
