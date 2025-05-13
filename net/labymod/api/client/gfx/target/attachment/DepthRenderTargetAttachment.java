// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.attachment;

import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;

public class DepthRenderTargetAttachment extends RenderTargetAttachment
{
    public DepthRenderTargetAttachment() {
        super(FramebufferTarget.BOTH, AttachmentTarget.DEPTH_ATTACHMENT.getId(), TextureTarget.TEXTURE_2D);
    }
    
    @Override
    public void configureFramebufferTexture(final int width, final int height) {
        this.gfx.texParameter(this.getTextureTarget(), TextureParameterName.TEXTURE_WRAP_S, TextureWrapMode.CLAMP_TO_EDGE);
        this.gfx.texParameter(this.getTextureTarget(), TextureParameterName.TEXTURE_WRAP_T, TextureWrapMode.CLAMP_TO_EDGE);
        this.gfx.texImage2D(this.getTextureTarget(), 0, 6402, width, height, 0, 6402, 5126, null);
    }
}
