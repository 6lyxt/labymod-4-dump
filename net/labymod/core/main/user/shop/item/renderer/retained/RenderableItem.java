// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.renderer.retained;

import net.labymod.api.client.gfx.pipeline.program.RenderParameters;
import net.labymod.api.Textures;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.pipeline.program.parameters.TexturingRenderParameter;

public abstract class RenderableItem
{
    protected static final TexturingRenderParameter DEFAULT_TEXTURING_RENDER_PARAMETER;
    protected Model model;
    @Nullable
    protected RenderedBuffer renderedBuffer;
    protected FloatMatrix4 modelViewMatrix;
    protected FloatMatrix4 projectionMatrix;
    protected int packedLightCoords;
    protected float[] boneMatrices;
    
    public abstract void render();
    
    public Model getModel() {
        return this.model;
    }
    
    @Nullable
    public RenderedBuffer getRenderedBuffer() {
        return this.renderedBuffer;
    }
    
    public FloatMatrix4 getModelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    public FloatMatrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    static {
        DEFAULT_TEXTURING_RENDER_PARAMETER = RenderParameters.createTextureParameter(Textures.EMPTY);
    }
}
