// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gfx.pipeline;

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
        this.fog.setStart(RenderSystem::getShaderFogStart);
        this.fog.setEnd(RenderSystem::getShaderFogEnd);
    }
    
    @Override
    public float getLineWidth() {
        return RenderSystem.getShaderLineWidth();
    }
    
    @Override
    public float getGameTime() {
        return RenderSystem.getShaderGameTime();
    }
}
