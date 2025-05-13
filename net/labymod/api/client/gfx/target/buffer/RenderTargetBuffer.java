// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.buffer;

import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.util.math.vector.FloatMatrix4;

public class RenderTargetBuffer
{
    private static final FloatMatrix4 DEFAULT_MODE_VIEW_MATRIX;
    private static final float DEFAULT_NEAR = 1000.0f;
    private static final float DEFAULT_FAR = 3000.0f;
    private final FloatMatrix4 projectionMatrix;
    private final RenderTarget renderTarget;
    private Supplier<RenderProgram> programSupplier;
    private ProjectionMatrixSetter projectionSetter;
    private RenderedBuffer renderedBuffer;
    private boolean needsRebuild;
    private int width;
    private int height;
    private float u;
    private float v;
    
    public RenderTargetBuffer(final RenderTarget renderTarget, final Supplier<RenderProgram> programSupplier) {
        this.projectionMatrix = FloatMatrix4.newIdentity();
        this.projectionSetter = ((projectionMatrix, width, height, near, far) -> projectionMatrix.setOrthographic(width, -height, near, far));
        this.renderTarget = renderTarget;
        this.programSupplier = programSupplier;
    }
    
    public void setProgramSupplier(final Supplier<RenderProgram> programSupplier) {
        this.programSupplier = programSupplier;
        this.needsRebuild = true;
    }
    
    public void setProjectionSetter(final ProjectionMatrixSetter projectionSetter) {
        this.projectionSetter = projectionSetter;
    }
    
    @Deprecated
    public void setDirty() {
        this.needsRebuild = true;
    }
    
    public void render(final int width, final int height, final float u, final float v) {
        if (this.needsRebuild || this.width != width || this.height != height || this.u != u || this.v != v) {
            this.width = width;
            this.height = height;
            this.u = u;
            this.v = v;
            this.rebuild();
        }
        if (this.renderedBuffer == null) {
            return;
        }
        final RenderTargetAttachment colorAttachment = this.renderTarget.findColorAttachment();
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        if (colorAttachment != null) {
            final int attachmentHandle = colorAttachment.getId();
            final GFXBridge gfx = renderPipeline.gfx();
            gfx.setActiveTexture(0);
            gfx.bindTexture2D(TextureId.of(attachmentHandle));
        }
        renderPipeline.matrixStorage().setModelViewMatrix(RenderTargetBuffer.DEFAULT_MODE_VIEW_MATRIX, 4);
        this.projectionSetter.set(this.projectionMatrix, (float)width, (float)height, 1000.0f, 3000.0f);
        renderPipeline.setProjectionMatrix(this.projectionMatrix);
        this.renderedBuffer.drawWithProgram();
    }
    
    private void rebuild() {
        this.needsRebuild = false;
        final BufferBuilder bufferBuilder = Laby.labyAPI().gfxRenderPipeline().getDefaultBufferBuilder();
        final RenderProgram renderProgram = this.programSupplier.get();
        bufferBuilder.begin(renderProgram, () -> "Render Target");
        final float zLevel = 300.0f;
        bufferBuilder.pos(0.0f, (float)this.height, zLevel).uv(0.0f, 0.0f).color(-1).endVertex();
        bufferBuilder.pos((float)this.width, (float)this.height, zLevel).uv(this.u, 0.0f).color(-1).endVertex();
        bufferBuilder.pos((float)this.width, 0.0f, zLevel).uv(this.u, this.v).color(-1).endVertex();
        bufferBuilder.pos(0.0f, 0.0f, zLevel).uv(0.0f, this.v).color(-1).endVertex();
        final BufferState bufferState = bufferBuilder.end();
        if (bufferState == null) {
            return;
        }
        if (this.renderedBuffer != null) {
            this.renderedBuffer.dispose();
        }
        (this.renderedBuffer = bufferState.uploadStaticDraw()).skipFramebufferCheck();
    }
    
    static {
        DEFAULT_MODE_VIEW_MATRIX = FloatMatrix4.createTranslateMatrix(0.0f, 0.0f, -2000.0f);
    }
    
    public interface ProjectionMatrixSetter
    {
        void set(final FloatMatrix4 p0, final float p1, final float p2, final float p3, final float p4);
    }
}
