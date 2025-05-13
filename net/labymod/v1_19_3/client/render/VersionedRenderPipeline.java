// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.render;

import net.labymod.api.client.render.PlayerHeartRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.v1_19_3.client.render.vertex.VersionedBufferBuilder;
import net.labymod.api.client.render.vertex.BufferBuilder;
import javax.inject.Inject;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.DefaultAbstractRenderPipeline;

@Singleton
@Implements(RenderPipeline.class)
public class VersionedRenderPipeline extends DefaultAbstractRenderPipeline
{
    @Inject
    public VersionedRenderPipeline() {
    }
    
    @Override
    public BufferBuilder createBufferBuilder() {
        return new VersionedBufferBuilder();
    }
    
    @Override
    protected GlStateBridge createGlStateBridge() {
        return Laby.references().glStateBridge();
    }
    
    @Override
    protected PlayerHeartRenderer createHeartRenderer() {
        return Laby.references().playerHeartRenderer();
    }
}
