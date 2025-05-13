// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline.target;

import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.target.attachment.CustomDepthRenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.ColorRenderTargetAttachment;
import net.labymod.api.client.gfx.target.RenderTarget;

public class VersionedRenderTarget extends RenderTarget
{
    private final bvd renderTarget;
    
    public VersionedRenderTarget(final bvd renderTarget) {
        this.renderTarget = renderTarget;
        this.attachments.add(ColorRenderTargetAttachment.create(0, () -> renderTarget.g));
        this.attachments.add(new CustomDepthRenderTargetAttachment(() -> renderTarget.h));
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
        if (cii.j()) {
            bus.G();
            final float u = this.renderTarget.c / (float)this.renderTarget.a;
            final float v = this.renderTarget.d / (float)this.renderTarget.b;
            bus.m();
            this.renderTargetBuffer.render(width, height, u, v);
            bus.l();
            bus.H();
        }
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
        this.renderTarget.f();
    }
    
    @Override
    public int getId() {
        return this.renderTarget.f;
    }
    
    @Override
    public void setClearColor(final float red, final float green, final float blue, final float alpha) {
        this.renderTarget.a(red, green, blue, alpha);
    }
    
    @Override
    public void resize(final int width, final int height) {
        this.renderTarget.a(width, height);
    }
    
    @Override
    public void clearMask(final AttributeMask... bits) {
        this.bind(true);
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        bus.m(mask);
        this.unbind();
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
