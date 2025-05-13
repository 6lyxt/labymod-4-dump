// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.batch;

import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhases;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.ResourceRenderContext;

@Singleton
@Implements(ResourceRenderContext.class)
public class DefaultResourceRenderContext extends DefaultRenderContext<ResourceRenderContext> implements ResourceRenderContext
{
    private static final float TEXTURE_PADDING = 0.025f;
    private BufferBuilder builder;
    private Stack stack;
    private boolean blur;
    
    @Inject
    public DefaultResourceRenderContext() {
    }
    
    @Override
    public ResourceRenderContext begin(final Stack stack, final ResourceLocation location, final Function<RenderPhase, BufferBuilder> builderFunction) {
        this.stack = stack;
        final Stack.EnvironmentData matrixData = stack.getEnvironmentData();
        final RenderPhase phase = matrixData.isShouldUseBufferSource() ? RenderPhases.createTexturePhase(location, this.blur, false, Laby.references().renderEnvironmentContext().isScreenContext()) : RenderPhases.create3DTexturePhase(location);
        this.builder = builderFunction.apply(phase);
        final BufferBuilder builder = this.builder;
        final RenderPhase phase2 = phase;
        Objects.requireNonNull(stack);
        builder.begin(phase2, stack::getMultiBufferSource);
        return this;
    }
    
    @Override
    public ResourceRenderContext begin(final Stack stack, final ResourceLocation location, final ShaderInstance shaderInstance, final Function<RenderPhase, BufferBuilder> builderFunction) {
        final Stack.EnvironmentData matrixData = stack.getEnvironmentData();
        this.stack = stack;
        final boolean shouldUseBufferSource = matrixData.isShouldUseBufferSource();
        final RenderPhase phase = shouldUseBufferSource ? RenderPhases.createTexturePhase(location, shaderInstance, Laby.references().renderEnvironmentContext().isScreenContext()) : RenderPhases.create3DTexturePhase(location);
        this.builder = builderFunction.apply(phase);
        if (shouldUseBufferSource) {
            final BufferBuilder builder = this.builder;
            final RenderPhase phase2 = phase;
            Objects.requireNonNull(stack);
            builder.begin(phase2, stack::getMultiBufferSource);
        }
        else {
            this.builder.begin(phase);
        }
        return this;
    }
    
    @Override
    public ResourceRenderContext blit(final float x, final float y, final float width, final float height, final float minU, final float minV, final float maxU, final float maxV, final float resolutionWidth, final float resolutionHeight, final float red, final float green, final float blue, final float alpha) {
        return this.innerBlit(x, x + width, y, y + height, this.zOffset, maxU, maxV, minU, minV, resolutionWidth, resolutionHeight, red, green, blue, alpha);
    }
    
    @Override
    public ResourceRenderContext blitSprite(final TextureUV uv, final int spriteWidth, final int spriteHeight, final int spriteX, final int spriteY, final int x, final int y, final int zOffset, final int width, final int height, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        final float alpha = colorFormat.normalizedAlpha(color);
        return this.innerBlit((float)x, (float)(x + width), (float)y, (float)(y + height), (float)zOffset, uv.getU(spriteX / (float)spriteWidth), uv.getU((spriteX + width) / (float)spriteWidth), uv.getV(spriteY / (float)spriteHeight), uv.getV((spriteY + height) / (float)spriteHeight), red, green, blue, alpha);
    }
    
    @Override
    public ResourceRenderContext directBlit(final float x, final float y, final float width, final float height, final float offset, final float red, final float green, final float blue, final float alpha) {
        final int light = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        this.builder.vertex(this.stack, x, y + height, offset).color(red, green, blue, alpha).texture(0.0f, height).lightMap(light).next();
        this.builder.vertex(this.stack, x + width, y + height, offset).color(red, green, blue, alpha).texture(width, height).lightMap(light).next();
        this.builder.vertex(this.stack, x + width, y, offset).color(red, green, blue, alpha).texture(width, 0.0f).lightMap(light).next();
        this.builder.vertex(this.stack, x, y, offset).color(red, green, blue, alpha).texture(0.0f, 0.0f).lightMap(light).next();
        return this;
    }
    
    @Override
    public ResourceRenderContext blur(final boolean blur) {
        this.blur = blur;
        return this;
    }
    
    @Override
    public ResourceRenderContext blitSprite(final int x, final int y, final int width, final int height, final int offset, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float red = colorFormat.normalizedRed(color);
        final float green = colorFormat.normalizedGreen(color);
        final float blue = colorFormat.normalizedBlue(color);
        final float alpha = colorFormat.normalizedAlpha(color);
        final int light = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        this.builder.vertex(this.stack, (float)x, (float)y, (float)offset).color(red, green, blue, alpha).texture(minU, minV).lightMap(light).next();
        this.builder.vertex(this.stack, (float)x, (float)(y + height), (float)offset).color(red, green, blue, alpha).texture(minU, maxV).lightMap(light).next();
        this.builder.vertex(this.stack, (float)(x + width), (float)(y + height), (float)offset).color(red, green, blue, alpha).texture(maxU, maxV).lightMap(light).next();
        this.builder.vertex(this.stack, (float)(x + width), (float)y, (float)offset).color(red, green, blue, alpha).texture(maxU, minV).lightMap(light).next();
        return this;
    }
    
    private ResourceRenderContext innerBlit(final float left, final float right, final float top, final float bottom, final float offset, final float minU, final float minV, final float maxU, final float maxV, final float resolutionWidth, final float resolutionHeight, final float red, final float green, final float blue, final float alpha) {
        final float spriteX = maxU / resolutionWidth;
        final float spriteWidth = (maxU + minU) / resolutionWidth;
        final float spriteY = maxV / resolutionHeight;
        final float spriteHeight = (maxV + minV) / resolutionHeight;
        return this.innerBlit(left, right, top, bottom, offset, spriteX, spriteWidth, spriteY, spriteHeight, red, green, blue, alpha);
    }
    
    private ResourceRenderContext innerBlit(float left, float right, float top, float bottom, final float offset, final float minU, final float maxU, final float minV, final float maxV, final float red, final float green, final float blue, final float alpha) {
        final RenderEnvironmentContext context = Laby.references().renderEnvironmentContext();
        final int light = context.getPackedLightWithCondition();
        if (context.isScreenContext()) {
            left += 0.025f;
            top += 0.025f;
            right += 0.025f;
            bottom += 0.025f;
        }
        this.builder.vertex(this.stack, left, bottom, offset).color(red, green, blue, alpha).texture(minU, maxV).lightMap(light).next();
        this.builder.vertex(this.stack, right, bottom, offset).color(red, green, blue, alpha).texture(maxU, maxV).lightMap(light).next();
        this.builder.vertex(this.stack, right, top, offset).color(red, green, blue, alpha).texture(maxU, minV).lightMap(light).next();
        this.builder.vertex(this.stack, left, top, offset).color(red, green, blue, alpha).texture(minU, minV).lightMap(light).next();
        return this;
    }
    
    @Override
    public RenderContext<ResourceRenderContext> uploadToBuffer() {
        this.builder.uploadToBuffer();
        return this;
    }
}
