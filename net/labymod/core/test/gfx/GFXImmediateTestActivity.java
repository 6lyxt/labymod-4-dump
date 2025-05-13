// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.gfx;

import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.primitive.RectangleRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gfx.pipeline.renderer.batch.SingleBufferBatch;
import net.labymod.api.client.gfx.pipeline.renderer.batch.Batchable;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
public class GFXImmediateTestActivity extends TestActivity
{
    private final Batchable batchable;
    
    public GFXImmediateTestActivity() {
        this.batchable = new SingleBufferBatch();
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        this.batchable.begin(RenderPrograms.getPositionTextureColorProgram());
        this.batchable.useRenderer(RectangleRenderer.class, renderer -> renderer.draw(97.5f, 100.0f, 150.0f, 110.0f, -65536));
        final Stack stack = context.stack();
        this.batchable.end(stack);
        final TextRenderer textRenderer = Laby.references().renderPipeline().textRenderer();
        textRenderer.text("Hello, World").pos(100.0f, 100.0f).color(-1).render(stack);
    }
}
