// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.client.render.draw.builder.ResourceBuilder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.core.client.render.draw.builder.DefaultResourceBuilder;

@Singleton
@Implements(BatchResourceRenderer.class)
public class DefaultBatchResourceRenderer extends DefaultResourceBuilder<BatchResourceRenderer> implements BatchResourceRenderer
{
    private final ResourceRenderContext resourceRenderContext;
    
    @Inject
    public DefaultBatchResourceRenderer(final ResourceRenderContext resourceRenderContext) {
        this.resourceRenderContext = resourceRenderContext;
    }
    
    @Override
    public BatchResourceRenderer beginBatch(final Stack stack, final ResourceLocation resourceLocation) {
        this.resourceLocation = null;
        this.resourceRenderContext.begin(stack, resourceLocation);
        this.resourceLocation = resourceLocation;
        return this;
    }
    
    @Override
    public BatchResourceRenderer texture(final ResourceLocation resourceLocation) {
        throw new UnsupportedOperationException("Cannot change the texture of a MultiResourceRenderer");
    }
    
    @Override
    public void upload() {
        this.resourceRenderContext.uploadToBuffer();
    }
    
    @Override
    public BatchResourceRenderer build() {
        this.validateBuilder();
        if (this.spriteXChanged) {
            final float resolutionWidth = this.resolutionWidthChanged ? this.resolutionWidth : 256.0f;
            final float resolutionHeight = this.resolutionHeightChanged ? this.resolutionHeight : 256.0f;
            this.resourceRenderContext.blit(this.x, this.y, this.width, this.height, this.spriteX, this.spriteY, this.spriteWidth, this.spriteHeight, resolutionWidth, resolutionHeight, this.color);
        }
        else {
            this.resourceRenderContext.directBlit(this.x, this.y, this.width, this.height, this.offset, this.color);
        }
        this.resetBuilder();
        return this;
    }
}
