// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.panorama;

import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.pipeline.renderer.batch.BufferBatchable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.LabyAPI;

public abstract class AbstractPanoramaRenderer implements PanoramaRenderer
{
    private final LabyAPI labyAPI;
    private float time;
    
    public AbstractPanoramaRenderer(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Override
    public void render(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        final MinecraftTextures textures = this.labyAPI.minecraft().textures();
        final ResourceLocation[] resourceLocations = textures.panoramaTextures();
        final GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
        final FloatMatrix4 projectionMatrix = new FloatMatrix4();
        projectionMatrix.setPerspective(85.0, (right - left) / (bottom - top), 0.05f, 10.0f);
        renderPipeline.setProjectionMatrix(projectionMatrix);
        stack.push();
        stack.identity();
        stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        final GFXBridge gfx = renderPipeline.gfx();
        gfx.storeBlaze3DStates();
        gfx.enableDepth();
        gfx.disableCull();
        gfx.enableBlend();
        gfx.depthMask(false);
        gfx.defaultBlend();
        final float time2 = this.time + tickDelta;
        this.time = time2;
        final float time = time2;
        final float rotX = (float)(Math.sin(time * 0.001f) * 5.0 + 25.0);
        final float rotY = -time * 0.1f;
        for (int j = 0; j < 4; ++j) {
            stack.push();
            final float x = (j % 2 / 2.0f - 0.5f) / 256.0f;
            final float y = (j / 2 / 2.0f - 0.5f) / 256.0f;
            stack.translate(x, y, 0.0f);
            stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
            stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
            renderPipeline.matrixStorage().setModelViewMatrix(stack.getProvider().getPosition(), 4);
            for (int side = 0; side < 6; ++side) {
                final float alpha = 1.0f / (j + 1);
                final BufferBatchable batchable = (BufferBatchable)Laby.references().gfxRenderPipeline().renderBuffers().singleBatchable();
                batchable.begin(RenderPrograms.getDefaultTexture(resourceLocations[side]));
                final BufferBuilder builder = batchable.bufferBuilder();
                if (side == 0) {
                    this.addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 1.0f, 0.0f, alpha);
                }
                if (side == 1) {
                    this.addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                }
                if (side == 2) {
                    this.addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                }
                if (side == 3) {
                    this.addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 1.0f, 0.0f, alpha);
                }
                if (side == 4) {
                    this.addVertexWithUV(builder, -1.0f, -1.0f, -1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, -1.0f, 1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, -1.0f, -1.0f, 1.0f, 0.0f, alpha);
                }
                if (side == 5) {
                    this.addVertexWithUV(builder, -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, alpha);
                    this.addVertexWithUV(builder, -1.0f, 1.0f, -1.0f, 0.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, alpha);
                    this.addVertexWithUV(builder, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, alpha);
                }
                batchable.end();
            }
            stack.pop();
        }
        stack.pop();
        final ResourceLocation overlayTexture = this.labyAPI.minecraft().textures().panoramaOverlayTexture();
        if (overlayTexture != null) {
            this.labyAPI.renderPipeline().resourceRenderer().texture(overlayTexture).pos(left, top, right, bottom).sprite(0.0f, 0.0f, 16.0f, 128.0f).resolution(16.0f, 128.0f).render(stack);
        }
        gfx.restoreBlaze3DStates();
    }
    
    private void addVertexWithUV(final BufferBuilder builder, final float x, final float y, final float z, final float u, final float v, final float alpha) {
        builder.putFloat(x, y, z).uv(u, v).color(1.0f, 1.0f, 1.0f, alpha).endVertex();
    }
}
