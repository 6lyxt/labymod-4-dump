// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.gfx;

import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.gfx.pipeline.pass.passes.StencilRenderPass;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
public class CircleStencilTestActivity extends TestActivity
{
    private boolean stencilTestEnabled;
    
    public CircleStencilTestActivity() {
        this.stencilTestEnabled = true;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.O) {
            this.stencilTestEnabled = !this.stencilTestEnabled;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final RenderPipeline renderPipeline = Laby.references().renderPipeline();
        final RectangleRenderer rectangleRenderer = renderPipeline.rectangleRenderer();
        final CircleRenderer circleRenderer = renderPipeline.circleRenderer();
        final Stack stack = context.stack();
        stack.push();
        stack.translate(0.0f, 0.0f, 0.0f);
        final float scaleValue = TimeUtil.getMillis() / 3600.0f % 2.0f;
        stack.scale(scaleValue);
        final GFXBridge gfx = Laby.gfx();
        final GFXRenderPipeline gfxRenderPipeline = this.labyAPI.gfxRenderPipeline();
        gfxRenderPipeline.renderToActivityTarget(target -> {
            if (this.stencilTestEnabled) {
                gfx.enableStencil();
                gfxRenderPipeline.clear(target);
                final StencilRenderPass stencilRenderPass = new StencilRenderPass();
                stencilRenderPass.begin();
                final float radius = 50.0f;
                circleRenderer.pos(radius, radius).radius(radius).color(Integer.MAX_VALUE).render(stack);
                stencilRenderPass.end();
                rectangleRenderer.pos(0.0f, 0.0f).size(50.0f).color(-65536).render(stack);
                rectangleRenderer.pos(0.0f, 50.0f).size(50.0f).color(-16711936).render(stack);
                rectangleRenderer.pos(50.0f, 50.0f).size(50.0f).color(-16776961).render(stack);
                rectangleRenderer.pos(50.0f, 0.0f).size(50.0f).color(-1).render(stack);
                gfx.disableStencil();
            }
            else {
                rectangleRenderer.pos(0.0f, 0.0f).size(50.0f).color(-65536).render(stack);
                rectangleRenderer.pos(0.0f, 50.0f).size(50.0f).color(-16711936).render(stack);
                rectangleRenderer.pos(50.0f, 50.0f).size(50.0f).color(-16776961).render(stack);
                rectangleRenderer.pos(50.0f, 0.0f).size(50.0f).color(-1).render(stack);
            }
            return;
        });
        stack.pop();
        renderPipeline.textRenderer().text("Stencil Test: " + (this.stencilTestEnabled ? "§aenabled" : "§cdisabled")).pos(0.0f, 0.0f).color(-1).render(stack);
    }
}
