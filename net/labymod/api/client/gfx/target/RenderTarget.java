// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target;

import net.labymod.api.client.gfx.target.attachment.DepthRenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.Depth24Stencil8RenderTargetAttachment;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import net.labymod.api.client.gfx.target.exception.RenderTargetException;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.target.attachment.ColorRenderTargetAttachment;
import net.labymod.api.client.gfx.GFXBridgeAccessor;
import java.util.ArrayList;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import java.util.function.Supplier;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.gfx.target.buffer.RenderTargetBuffer;
import java.util.List;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.gfx.GFXBridge;
import java.util.function.Predicate;
import net.labymod.api.util.Disposable;

public class RenderTarget implements Disposable
{
    public static final Predicate<RenderTargetAttachment> COLOR_ATTACHMENT_FILTER;
    private static final Predicate<RenderTargetAttachment> DEPTH_ATTACHMENT_FILTER;
    private static final Predicate<RenderTargetAttachment> DEPTH24_STENCIL8_ATTACHMENT_FILTER;
    protected final GFXBridge gfx;
    private int width;
    private int height;
    private int viewWidth;
    private int viewHeight;
    private final FloatVector4 colorChannel;
    protected final List<RenderTargetAttachment> attachments;
    protected final RenderTargetBuffer renderTargetBuffer;
    @ApiStatus.Experimental
    protected boolean applied;
    private int id;
    
    public RenderTarget() {
        this(target -> RenderPrograms::getRenderTargetProgram);
    }
    
    public RenderTarget(final Function<RenderTarget, Supplier<RenderProgram>> programConstructor) {
        this.colorChannel = new FloatVector4(0.0f, 0.0f, 0.0f, 1.0f);
        this.attachments = new ArrayList<RenderTargetAttachment>();
        this.id = -1;
        this.gfx = GFXBridgeAccessor.get();
        this.renderTargetBuffer = new RenderTargetBuffer(this, programConstructor.apply(this));
    }
    
    public RenderTarget addAttachment(final RenderTargetAttachment attachment) {
        this.attachments.add(attachment);
        return this;
    }
    
    public RenderTarget addColorAttachment(final int index) {
        return this.addAttachment(ColorRenderTargetAttachment.create(index));
    }
    
    public RenderTarget addColorAttachment(final int index, final TextureFilter defaultFilterMode) {
        return this.addAttachment(ColorRenderTargetAttachment.createWithFilter(index, defaultFilterMode));
    }
    
    public void createTarget() {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final int width = (window == null) ? 0 : window.getRawWidth();
        final int height = (window == null) ? 0 : window.getRawHeight();
        this.createTarget(width, height);
    }
    
    public void createTarget(int width, int height) {
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        this.width = width;
        this.height = height;
        this.viewWidth = width;
        this.viewHeight = height;
        final int bindingFramebuffer = this.gfx.getBindingFramebuffer();
        this.id = this.gfx.genFramebuffers();
        for (final RenderTargetAttachment attachment : this.attachments) {
            attachment.create(this.width, this.height);
        }
        this.bind(FramebufferTarget.BOTH, false);
        for (final RenderTargetAttachment attachment : this.attachments) {
            this.gfx.framebufferTexture2D(attachment.getTarget(), attachment.getAttachmentTarget(), attachment.getTextureTarget(), attachment.getId(), 0);
        }
        this.gfx.onCreateFramebufferError((Function<String, RuntimeException>)RenderTargetException::new);
        Laby.references().gfxRenderPipeline().clear(this);
        this.gfx.bindFramebuffer(FramebufferTarget.BOTH, bindingFramebuffer);
    }
    
    public void bind(final boolean updateViewport) {
        this.bind(FramebufferTarget.BOTH, updateViewport);
    }
    
    public void bind(final FramebufferTarget target, final boolean updateViewport) {
        if (this.id == -1) {
            this.createTarget();
        }
        this.gfx.bindFramebuffer(target, this.id);
        if (updateViewport) {
            this.gfx.viewport(0, 0, this.viewWidth, this.viewHeight);
        }
        this.applied = true;
    }
    
    public void bindWrite(final boolean updateViewport) {
        this.bind(FramebufferTarget.DRAW, updateViewport);
    }
    
    public void bindRead(final boolean updateViewport) {
        this.bind(FramebufferTarget.READ, updateViewport);
    }
    
    public void unbind() {
        this.unbind(FramebufferTarget.BOTH);
    }
    
    public void unbindWrite() {
        this.unbind(FramebufferTarget.DRAW);
    }
    
    public void unbindRead() {
        this.unbind(FramebufferTarget.READ);
    }
    
    public void unbind(final FramebufferTarget target) {
        this.gfx.bindFramebuffer(target, 0);
    }
    
    public void setRenderProgram(final Supplier<RenderProgram> programSupplier) {
        this.renderTargetBuffer.setProgramSupplier(programSupplier);
    }
    
    public void setProjectionSetter(final RenderTargetBuffer.ProjectionMatrixSetter projectionSetter) {
        this.renderTargetBuffer.setProjectionSetter(projectionSetter);
    }
    
    public void render(final boolean scaled) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final int width = scaled ? window.getScaledWidth() : window.getRawWidth();
        final int height = scaled ? window.getScaledHeight() : window.getRawHeight();
        this.render(width, height, false);
    }
    
    public void render(final int width, final int height, final boolean disableBlending) {
        final float u = this.viewWidth / (float)this.width;
        final float v = this.viewHeight / (float)this.height;
        this.renderTargetBuffer.render(width, height, u, v);
    }
    
    public void setClearColor(final float red, final float green, final float blue, final float alpha) {
        this.colorChannel.set(MathHelper.clamp(red, 0.0f, 1.0f), MathHelper.clamp(green, 0.0f, 1.0f), MathHelper.clamp(blue, 0.0f, 1.0f), MathHelper.clamp(alpha, 0.0f, 1.0f));
    }
    
    public void clear() {
        this.bind(FramebufferTarget.BOTH, true);
        this.gfx.clearColor(this.colorChannel.getX(), this.colorChannel.getY(), this.colorChannel.getZ(), this.colorChannel.getW());
        this._clear(AttributeMask.COLOR_DEPTH_STENCIL_BUFFER_BIT);
        this.unbind(FramebufferTarget.BOTH);
    }
    
    public void clearMask(final AttributeMask... bits) {
        this._clear(bits);
    }
    
    private void _clear(final AttributeMask... bits) {
        this.gfx.clear(bits);
        this.applied = false;
    }
    
    @ApiStatus.Experimental
    public boolean isApplied() {
        return this.applied;
    }
    
    public void resize(final int width, final int height) {
        ThreadSafe.ensureRenderThread();
        if (this.id >= 0) {
            this.dispose();
        }
        this.createTarget(width, height);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public void dispose() {
        ThreadSafe.ensureRenderThread();
        final int bindingFramebuffer = this.gfx.getBindingFramebuffer();
        this.unbind();
        for (final RenderTargetAttachment attachment : this.attachments) {
            attachment.dispose();
        }
        this.gfx.deleteFramebuffers(this.id);
        this.id = -1;
        if (bindingFramebuffer != this.id) {
            this.gfx.bindFramebuffer(FramebufferTarget.BOTH, bindingFramebuffer);
        }
    }
    
    @Nullable
    public RenderTargetAttachment findAttachment(final Predicate<RenderTargetAttachment> filter) {
        for (final RenderTargetAttachment attachment : this.attachments) {
            if (!filter.test(attachment)) {
                continue;
            }
            return attachment;
        }
        return null;
    }
    
    public RenderTargetAttachment findColorAttachment() {
        return this.findAttachment(RenderTarget.COLOR_ATTACHMENT_FILTER);
    }
    
    public RenderTargetAttachment findDepthAttachment() {
        return this.findAttachment(RenderTarget.DEPTH_ATTACHMENT_FILTER);
    }
    
    public RenderTargetAttachment findDepth24Stencil8Attachment() {
        return this.findAttachment(RenderTarget.DEPTH24_STENCIL8_ATTACHMENT_FILTER);
    }
    
    public List<RenderTargetAttachment> getAttachments() {
        return this.attachments;
    }
    
    @Deprecated
    public void setDirty() {
        if (this.renderTargetBuffer != null) {
            this.renderTargetBuffer.setDirty();
        }
    }
    
    static {
        COLOR_ATTACHMENT_FILTER = (attachment -> attachment instanceof ColorRenderTargetAttachment);
        DEPTH_ATTACHMENT_FILTER = (attachment -> attachment instanceof DepthRenderTargetAttachment);
        DEPTH24_STENCIL8_ATTACHMENT_FILTER = (attachment -> attachment instanceof Depth24Stencil8RenderTargetAttachment);
    }
}
