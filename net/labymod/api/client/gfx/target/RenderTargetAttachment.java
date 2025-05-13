// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target;

import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.GFXBridgeAccessor;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.Disposable;

public abstract class RenderTargetAttachment implements Disposable
{
    protected final GFXBridge gfx;
    private final FramebufferTarget target;
    private final int attachmentTarget;
    private final TextureTarget textureTarget;
    private int id;
    private TextureFilter defaultFilterMode;
    private TextureFilter filterMode;
    
    public RenderTargetAttachment(final FramebufferTarget target, final int attachmentTarget, final TextureTarget textureTarget) {
        this(target, attachmentTarget, textureTarget, TextureFilter.NEAREST);
    }
    
    public RenderTargetAttachment(final FramebufferTarget target, final int attachmentTarget, final TextureTarget textureTarget, final TextureFilter defaultFilterMode) {
        this.gfx = GFXBridgeAccessor.get();
        this.target = target;
        this.attachmentTarget = attachmentTarget;
        this.textureTarget = textureTarget;
        this.defaultFilterMode = defaultFilterMode;
    }
    
    public void create(final int width, final int height) {
        this.id = this.gfx.genTextures();
        this.gfx.setActiveTexture(0);
        this.gfx.enableTexture();
        this.gfx.bindTexture(this.textureTarget, TextureId.of(this.id));
        this.configureFramebufferTexture(width, height);
    }
    
    public abstract void configureFramebufferTexture(final int p0, final int p1);
    
    public void applyDefaultFilter() {
        this.applyFilter(this.defaultFilterMode);
    }
    
    public void applyFilter(final TextureFilter filterMode) {
        this.applyFilter(filterMode, false);
    }
    
    public void applyFilter(final TextureFilter filterMode, final boolean unbindTexture) {
        this.filterMode = filterMode;
        this.gfx.bindTexture(this.textureTarget, TextureId.of(this.id));
        this.gfx.texParameter(this.textureTarget, TextureParameterName.TEXTURE_MAG_FILTER, filterMode);
        this.gfx.texParameter(this.textureTarget, TextureParameterName.TEXTURE_MIN_FILTER, filterMode);
        if (unbindTexture) {
            this.gfx.bindTexture(this.textureTarget, TextureId.ZERO);
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public void dispose() {
        this.gfx.deleteTextures(this.id);
        this.id = -1;
    }
    
    public final FramebufferTarget getTarget() {
        return this.target;
    }
    
    public final int getAttachmentTarget() {
        return this.attachmentTarget;
    }
    
    public final TextureTarget getTextureTarget() {
        return this.textureTarget;
    }
}
