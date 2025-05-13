// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.gfx.pipeline.target;

import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.api.client.gfx.AttributeMask;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import java.util.List;
import net.labymod.api.client.gfx.target.attachment.CustomDepthRenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.ColorRenderTargetAttachment;
import java.util.Objects;
import net.labymod.api.client.gfx.target.RenderTarget;

public class VersionedRenderTarget extends RenderTarget
{
    private final eym renderTarget;
    
    public VersionedRenderTarget(final eym renderTarget) {
        this.renderTarget = renderTarget;
        final List<RenderTargetAttachment> attachments = this.attachments;
        final int index = 0;
        final eym renderTarget2 = this.renderTarget;
        Objects.requireNonNull(renderTarget2);
        attachments.add(ColorRenderTargetAttachment.create(index, renderTarget2::f));
        final List<RenderTargetAttachment> attachments2 = this.attachments;
        final eym renderTarget3 = this.renderTarget;
        Objects.requireNonNull(renderTarget3);
        attachments2.add(new CustomDepthRenderTargetAttachment(renderTarget3::g));
    }
    
    @Override
    public void bind(final boolean updateViewport) {
        this.renderTarget.a(updateViewport);
    }
    
    @Override
    public void bind(final FramebufferTarget target, final boolean updateViewport) {
        this.renderTarget.a(true);
    }
    
    @Override
    public void unbind() {
        this.renderTarget.e();
    }
    
    @Override
    public void unbind(final FramebufferTarget target) {
        this.renderTarget.e();
    }
    
    @Override
    public void render(final int width, final int height, final boolean disableBlending) {
        final float u = this.renderTarget.e / (float)this.renderTarget.c;
        final float v = this.renderTarget.f / (float)this.renderTarget.d;
        RenderSystem.enableBlend();
        this.renderTargetBuffer.render(width, height, u, v);
        RenderSystem.disableBlend();
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
        this.renderTarget.b(ffh.a);
    }
    
    @Override
    public int getId() {
        return this.renderTarget.h;
    }
    
    @Override
    public void setClearColor(final float red, final float green, final float blue, final float alpha) {
        this.renderTarget.a(red, green, blue, alpha);
    }
    
    @Override
    public void resize(final int width, final int height) {
        this.renderTarget.a(width, height, true);
    }
    
    @Override
    public void clearMask(final AttributeMask... bits) {
        this.renderTarget.a(true);
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        GlStateManager._clear(mask, ffh.a);
        this.renderTarget.e();
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
