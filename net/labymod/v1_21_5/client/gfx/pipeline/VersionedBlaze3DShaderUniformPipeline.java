// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.pipeline;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.pipeline.Matrices;
import javax.inject.Inject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DShaderUniformPipeline;

@Singleton
@Implements(Blaze3DShaderUniformPipeline.class)
public class VersionedBlaze3DShaderUniformPipeline extends AbstractBlaze3DShaderUniformPipeline implements Blaze3DShaderUniformPipeline
{
    @Inject
    public VersionedBlaze3DShaderUniformPipeline() {
        this.matrices = new VersionedMatrices();
        this.fog.setStart(() -> RenderSystem.getShaderFog().a());
        this.fog.setEnd(() -> RenderSystem.getShaderFog().b());
    }
    
    @Override
    public float getLineWidth() {
        return RenderSystem.getShaderLineWidth();
    }
    
    @Override
    public float getGameTime() {
        return RenderSystem.getShaderGameTime();
    }
    
    private static class VersionedMatrices extends Matrices
    {
        @Override
        public FloatMatrix4 modelViewMatrix() {
            return MathHelper.mapper().fromMatrix4f(RenderSystem.getModelViewMatrix());
        }
    }
}
