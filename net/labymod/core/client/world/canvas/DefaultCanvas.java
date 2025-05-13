// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import net.labymod.api.util.math.vector.Matrix4;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.canvas.template.CanvasTemplate;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.canvas.CanvasRenderer;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.world.signobject.object.SignObject3DRenderer;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.client.world.signobject.object.SignObject3D;

public class DefaultCanvas extends SignObject3D<CanvasMeta> implements Canvas, SignObject3DRenderer<CanvasMeta>
{
    private static final MutableMouse MOUSE;
    private static final float OVERLAY_Z_OFFSET = 0.3f;
    private final FloatVector3 renderOffset;
    private CanvasRenderer renderer;
    
    public DefaultCanvas(final CanvasMeta meta, final SignObjectPosition position) {
        super(meta, position);
        this.renderOffset = meta.template().renderOffset();
        this.set3DRenderer(this);
    }
    
    @Override
    public CanvasRenderer renderer() {
        return this.renderer;
    }
    
    @Override
    public void setRenderer(final CanvasRenderer renderer) {
        this.renderer = renderer;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.renderer != null) {
            this.renderer.dispose(this);
        }
    }
    
    @Override
    public void render3D(final Stack stack, final SignObject<CanvasMeta> object, final double x, final double y, final double z, final float tickDelta) {
        if (this.renderer == null) {
            return;
        }
        stack.push();
        stack.translate(x + 0.5, y, z + 0.5);
        stack.rotate(object.position().rotationYaw(), 0.0f, 1.0f, 0.0f);
        stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        stack.translate(0.5f, 0.0f, 0.5f);
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.enableBlend();
        gfx.defaultBlend();
        Laby.references().renderEnvironmentContext().setPackedLight(15728880);
        stack.translate(this.renderOffset.getX(), -this.renderOffset.getY(), this.renderOffset.getZ() + 0.042f);
        final float scale = 0.06295f;
        stack.scale(-scale, scale, scale);
        stack.push();
        stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        stack.translate(-this.getWidth() / 2.0f - 7.9411764f, 0.0f, 0.0f);
        this.wrapRender(CanvasRenderer.CanvasSide.BACK, () -> this.renderer.render2D(stack, DefaultCanvas.MOUSE, this, CanvasRenderer.CanvasSide.BACK, tickDelta));
        stack.pop();
        stack.push();
        stack.translate(-this.getWidth() / 2.0f + 7.9411764f, 0.0f, 0.0f);
        this.wrapRender(CanvasRenderer.CanvasSide.FRONT, () -> this.renderer.render2D(stack, DefaultCanvas.MOUSE, this, CanvasRenderer.CanvasSide.FRONT, tickDelta));
        stack.pop();
        if (this.renderer.hasOverlay()) {
            stack.push();
            stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            stack.translate(-this.getWidth() / 2.0f - 7.9411764f, 0.0f, 0.3f);
            this.wrapRender(CanvasRenderer.CanvasSide.BACK, () -> this.renderer.renderOverlay2D(stack, DefaultCanvas.MOUSE, this, CanvasRenderer.CanvasSide.BACK, tickDelta));
            stack.pop();
            stack.push();
            stack.translate(-this.getWidth() / 2.0f + 7.9411764f, 0.0f, 0.3f);
            this.wrapRender(CanvasRenderer.CanvasSide.FRONT, () -> this.renderer.renderOverlay2D(stack, DefaultCanvas.MOUSE, this, CanvasRenderer.CanvasSide.FRONT, tickDelta));
            stack.pop();
        }
        stack.pop();
        gfx.restoreBlaze3DStates();
    }
    
    private void wrapRender(final CanvasRenderer.CanvasSide side, final Runnable runnable) {
        final Scissor scissor = Laby.labyAPI().gfxRenderPipeline().scissor();
        scissor.disableScissorWorld();
        final GFXBridge gfx = Laby.gfx();
        gfx.enableDepth();
        gfx.depthFunc(515);
        gfx.depthMask(true);
        Laby.labyAPI().gfxRenderPipeline().overlappingTranslator().translateOverlappingElements((object, matrix) -> matrix.translate(0.0f, 0.0f, 0.01f), runnable);
        scissor.enableScissorWorld();
    }
    
    static {
        MOUSE = new MutableMouse(-1.0, -1.0);
    }
}
