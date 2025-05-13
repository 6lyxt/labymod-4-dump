// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.attachment;

import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import java.util.function.IntSupplier;
import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;

public class ColorRenderTargetAttachment extends RenderTargetAttachment
{
    public ColorRenderTargetAttachment(final int attachmentTarget) {
        super(FramebufferTarget.BOTH, attachmentTarget, TextureTarget.TEXTURE_2D);
    }
    
    public ColorRenderTargetAttachment(final int attachmentTarget, final TextureFilter defaultFilterMode) {
        super(FramebufferTarget.BOTH, attachmentTarget, TextureTarget.TEXTURE_2D, defaultFilterMode);
    }
    
    public static RenderTargetAttachment create(final int index) {
        return new ColorRenderTargetAttachment(AttachmentTarget.COLOR_ATTACHMENT.getId() + index);
    }
    
    public static RenderTargetAttachment createWithFilter(final int index, final TextureFilter defaultFilterMode) {
        return new ColorRenderTargetAttachment(AttachmentTarget.COLOR_ATTACHMENT.getId() + index, defaultFilterMode);
    }
    
    public static RenderTargetAttachment create(final int index, final IntSupplier customTextureId) {
        return new CustomColorRenderTargetAttachment(AttachmentTarget.COLOR_ATTACHMENT.getId() + index, customTextureId);
    }
    
    @Override
    public void configureFramebufferTexture(final int width, final int height) {
        this.applyDefaultFilter();
        this.gfx.texParameter(this.getTextureTarget(), TextureParameterName.TEXTURE_WRAP_S, TextureWrapMode.CLAMP_TO_EDGE);
        this.gfx.texParameter(this.getTextureTarget(), TextureParameterName.TEXTURE_WRAP_T, TextureWrapMode.CLAMP_TO_EDGE);
        this.gfx.texImage2D(this.getTextureTarget(), 0, 32856, width, height, 0, 6408, 5121, null);
    }
}
