// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.target.RenderTarget;

record PostPassRenderTarget(RenderTarget renderTarget, boolean clear) {
    public void clearTarget() {
        if (this.clear) {
            this.renderTarget.clear();
        }
    }
    
    public int getWidth() {
        return this.renderTarget.getWidth();
    }
    
    public int getHeight() {
        return this.renderTarget.getHeight();
    }
    
    public void bindWrite(final boolean updateViewport) {
        this.renderTarget.bindWrite(updateViewport);
    }
    
    public void unbindWrite() {
        this.renderTarget.unbindWrite();
    }
    
    public void unbindRead() {
        this.renderTarget.unbindRead();
    }
    
    public RenderTargetAttachment findColorAttachment() {
        return this.renderTarget.findColorAttachment();
    }
}
