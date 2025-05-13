// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.pipeline.target;

import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.AttributeMask;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.target.attachment.CustomDepthRenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.ColorRenderTargetAttachment;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.gfx.target.RenderTarget;

public class VersionedRenderTarget extends RenderTarget
{
    private final fjr renderTarget;
    
    public VersionedRenderTarget(final fjr renderTarget) {
        this.renderTarget = renderTarget;
        this.attachments.add(ColorRenderTargetAttachment.create(0, () -> MinecraftUtil.getGpuTextureId(this.renderTarget.c())));
        this.attachments.add(new CustomDepthRenderTargetAttachment(() -> MinecraftUtil.getGpuTextureId(this.renderTarget.d())));
    }
    
    @Override
    public void bind(final boolean updateViewport) {
        this.bind(FramebufferTarget.BOTH, updateViewport);
    }
    
    @Override
    public void bind(final FramebufferTarget target, final boolean updateViewport) {
        this.gfx.bindFramebuffer(target, this.getId());
        if (updateViewport) {
            final GpuTexture colorTexture = this.renderTarget.c();
            this.gfx.viewport(0, 0, colorTexture.getWidth(0), colorTexture.getHeight(0));
        }
    }
    
    @Override
    public void unbind() {
        this.gfx.bindFramebuffer(FramebufferTarget.BOTH, 0);
    }
    
    @Override
    public void unbind(final FramebufferTarget target) {
        this.gfx.bindFramebuffer(target, 0);
    }
    
    @Override
    public void render(final int width, final int height, final boolean disableBlending) {
        final float u = this.renderTarget.e / (float)this.renderTarget.c;
        final float v = this.renderTarget.f / (float)this.renderTarget.d;
        GlStateManager._enableBlend();
        this.renderTargetBuffer.render(width, height, u, v);
        GlStateManager._disableBlend();
    }
    
    @Override
    public int getWidth() {
        return this.renderTarget.c;
    }
    
    @Override
    public int getHeight() {
        return this.renderTarget.d;
    }
    
    @Override
    public void clear() {
    }
    
    @Override
    public int getId() {
        final GpuTexture colorTexture = this.renderTarget.c();
        if (colorTexture instanceof final fjl glTexture) {
            return glTexture.a(((fjg)RenderSystem.getDevice()).b(), this.renderTarget.d());
        }
        throw new IllegalStateException("OpenGL implementation was expected, but got " + colorTexture.getClass().getName());
    }
    
    @Override
    public void setClearColor(final float red, final float green, final float blue, final float alpha) {
        this.bindWrite(true);
        this.gfx.clearColor(red, green, blue, alpha);
        this.unbindWrite();
    }
    
    @Override
    public void resize(final int width, final int height) {
        this.renderTarget.a(width, height);
    }
    
    @Override
    public void clearMask(final AttributeMask... bits) {
        this.bindWrite(true);
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        GlStateManager._clear(mask);
        this.unbindWrite();
    }
    
    @Override
    public void dispose() {
        this.renderTarget.a();
    }
    
    @Override
    public RenderTarget addAttachment(final RenderTargetAttachment attachment) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public RenderTarget addColorAttachment(final int index) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void createTarget() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void createTarget(final int width, final int height) {
        throw new UnsupportedOperationException();
    }
}
