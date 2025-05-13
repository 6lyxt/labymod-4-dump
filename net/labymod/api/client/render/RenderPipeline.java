// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.client.render.vertex.OldVertexFormatRegistry;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.TriangleRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RenderPipeline
{
    void setModifiedAlpha(final boolean p0);
    
    void setAlpha(final float p0);
    
    void multiplyAlpha(final float p0);
    
    void multiplyAlpha(final float p0, final Runnable p1);
    
    void setAlpha(final float p0, final Runnable p1);
    
    float getAlpha();
    
    BufferBuilder createBufferBuilder();
    
    TextRenderer textRenderer();
    
    GlStateBridge glStateBridge();
    
    PlayerHeartRenderer heartRenderer();
    
    ComponentRenderer componentRenderer();
    
    CircleRenderer circleRenderer();
    
    RectangleRenderer rectangleRenderer();
    
    TriangleRenderer triangleRenderer();
    
    ResourceRenderer resourceRenderer();
    
    OldVertexFormatRegistry vertexFormatRegistry();
    
    ShaderProvider shaderProvider();
    
    ModelService modelService();
    
    Resources resources();
    
    ResourcePackRepository resourcePackRepository();
    
    ResourcePack.Factory resourcePackFactory();
    
    RenderContexts renderContexts();
    
    RenderConstants renderConstants();
    
    GameTickProvider gameTickProvider();
    
    default void renderSeeThrough(final Entity entity, final Runnable renderer) {
        this.renderSeeThrough(entity, 0.5f, renderer);
    }
    
    void renderSeeThrough(final Entity p0, final float p1, final Runnable p2);
    
    void renderNoneStandardNameTag(final Entity p0, final Runnable p1);
}
