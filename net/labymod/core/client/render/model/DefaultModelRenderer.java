// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model;

import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.models.Implements;
import net.labymod.api.client.render.model.ModelRenderer;

@Implements(ModelRenderer.class)
public class DefaultModelRenderer implements ModelRenderer
{
    private final GFXRenderPipeline renderPipeline;
    private final ModelUploader modelUploader;
    
    @Inject
    public DefaultModelRenderer() {
        this.renderPipeline = Laby.references().gfxRenderPipeline();
        this.modelUploader = Laby.references().modelUploader();
    }
    
    @Override
    public ModelUploader modelUploader() {
        return this.modelUploader;
    }
    
    @Override
    public void render(final Stack stack, final Model model, final boolean forceProjectionMatrix) {
        final RenderedBuffer renderedBuffer = model.getRenderedBuffer();
        if (renderedBuffer == null) {
            return;
        }
        if (forceProjectionMatrix) {
            this.renderPipeline.setProjectionMatrix();
        }
        this.renderPipeline.setModelViewMatrix(stack.getProvider().getPosition());
        renderedBuffer.drawWithProgram();
    }
}
