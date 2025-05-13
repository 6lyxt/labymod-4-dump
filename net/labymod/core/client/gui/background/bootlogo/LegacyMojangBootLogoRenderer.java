// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.renderer.batch.Batchable;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;

public class LegacyMojangBootLogoRenderer extends AbstractBootLogoRenderer
{
    private static final boolean RENDER_LABYMOD_BRAND = false;
    
    @Override
    public void renderBackground(final Stack stack, final float left, final float top, final float right, final float bottom, final float partialTicks) {
        Laby.gfx().clearDepth();
        final ResourceLocation resourceLocation = this.labyAPI.minecraft().textures().splashTexture();
        this.labyAPI.renderPipeline().resourceRenderer().texture(resourceLocation).pos(left, top, right, bottom).sprite(0.0f, 0.0f, 1.0f, 1.0f).resolution(256.0f, 256.0f).render(stack);
    }
    
    @Override
    public void renderForeground(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta, final boolean uiContext) {
        final ResourceLocation resourceLocation = this.labyAPI.minecraft().textures().splashTexture();
        final float centerX = left + (right - left) / 2.0f;
        final float centerY = top + (bottom - top) / 2.0f;
        final float size = bottom - top;
        final Batchable batchable = Laby.references().gfxRenderPipeline().renderBuffers().singleBatchable();
        final net.labymod.api.client.gfx.pipeline.renderer.primitive.ResourceRenderer resourceRenderer = batchable.getRenderer(net.labymod.api.client.gfx.pipeline.renderer.primitive.ResourceRenderer.class);
        batchable.begin(RenderPrograms.getDefaultTexture(resourceLocation));
        resourceRenderer.draw(centerX - size / 2.0f, top, size, size, 0.0f, 0.0f, 256.0f, 256.0f, 256.0f, 256.0f);
        this.labyAPI.gfxRenderPipeline().setModelViewMatrix(stack.getProvider().getPosition());
        if (uiContext) {
            batchable.end(stack);
        }
        else {
            batchable.end();
        }
    }
    
    @Override
    protected void onPreloadResources() {
        this.preloadTexture(Textures.Splash.LABYMOD);
    }
}
