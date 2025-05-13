// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw;

import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstance;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.Laby;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.render.draw.EntityHeartRenderer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceApplier;
import net.labymod.core.client.render.draw.shader.rounded.RoundedGeometryShaderInstanceHolder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.core.client.render.draw.builder.DefaultResourceBuilder;

@Singleton
@Implements(ResourceRenderer.class)
public class DefaultResourceRenderer extends DefaultResourceBuilder<ResourceRenderer> implements ResourceRenderer
{
    private final RoundedGeometryShaderInstanceHolder shaderInstanceHolder;
    private final RoundedGeometryShaderInstanceApplier shaderInstanceApplier;
    private final ResourceRenderContext resourceRenderContext;
    private final PlayerHeadRenderer playerHeadRenderer;
    private final BatchResourceRenderer batchResourceRenderer;
    
    @Inject
    public DefaultResourceRenderer(final RoundedGeometryShaderInstanceHolder shaderInstanceHolder, final RoundedGeometryShaderInstanceApplier shaderInstanceApplier, final ResourceRenderContext resourceRenderContext, final PlayerHeadRenderer playerHeadRenderer, final BatchResourceRenderer batchResourceRenderer) {
        this.shaderInstanceHolder = shaderInstanceHolder;
        this.shaderInstanceApplier = shaderInstanceApplier;
        this.resourceRenderContext = resourceRenderContext;
        this.playerHeadRenderer = playerHeadRenderer;
        this.batchResourceRenderer = batchResourceRenderer;
    }
    
    @Override
    public PlayerHeadRenderer head() {
        return this.playerHeadRenderer;
    }
    
    @Override
    public BatchResourceRenderer beginBatch(final Stack stack, final ResourceLocation location) {
        return this.batchResourceRenderer.beginBatch(stack, location);
    }
    
    @Override
    public HeartRenderer heartRenderer() {
        return DefaultHeartRenderer.create();
    }
    
    @Override
    public EntityHeartRenderer entityHeartRenderer(final LivingEntity livingEntity) {
        return DefaultEntityHeartRenderer.of(livingEntity);
    }
    
    @Override
    public void render(final Stack stack) {
        this.validateBuilder();
        if (ColorUtil.isNoColor(this.color)) {
            this.resetBuilder();
            return;
        }
        final RoundedGeometryShaderInstance shaderInstance = this.shaderInstanceHolder.get("texture");
        final boolean shouldRound = this.data != null;
        if (shouldRound) {
            if (!shaderInstance.complied()) {
                shaderInstance.setDiffuseSamplerTexture();
                shaderInstance.prepare(Laby.labyAPI().renderPipeline().vertexFormatRegistry().getPositionColorTextureLightmap());
                return;
            }
            this.shaderInstanceApplier.apply(shaderInstance, this.data);
        }
        this.resourceRenderContext.blur(this.blur);
        if (shouldRound) {
            this.resourceRenderContext.begin(stack, this.resourceLocation, shaderInstance);
        }
        else {
            this.resourceRenderContext.begin(stack, this.resourceLocation);
        }
        this.resourceRenderContext.blur(false);
        if (this.spriteXChanged) {
            final float resolutionWidth = this.resolutionWidthChanged ? this.resolutionWidth : 256.0f;
            final float resolutionHeight = this.resolutionHeightChanged ? this.resolutionHeight : 256.0f;
            this.resourceRenderContext.blit(this.x, this.y, this.width, this.height, this.spriteX, this.spriteY, this.spriteWidth, this.spriteHeight, resolutionWidth, resolutionHeight, this.color);
        }
        else {
            this.resourceRenderContext.directBlit(this.x, this.y, this.width, this.height, this.offset, this.color);
        }
        this.resourceRenderContext.getBuilder().uploadToBuffer();
        this.resetBuilder();
    }
}
