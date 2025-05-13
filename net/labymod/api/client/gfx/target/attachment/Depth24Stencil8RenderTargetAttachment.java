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

public class Depth24Stencil8RenderTargetAttachment extends RenderTargetAttachment
{
    public Depth24Stencil8RenderTargetAttachment() {
        super(FramebufferTarget.BOTH, AttachmentTarget.DEPTH_STENCIL_ATTACHMENT.getId(), TextureTarget.TEXTURE_2D);
    }
    
    @Override
    public void configureFramebufferTexture(final int width, final int height) {
        final TextureTarget textureTarget = this.getTextureTarget();
        this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_WRAP_S, TextureWrapMode.CLAMP_TO_EDGE);
        this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_WRAP_T, TextureWrapMode.CLAMP_TO_EDGE);
        final int level = 0;
        if (level >= 0) {
            this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_MAX_LEVEL, level);
            this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_MIN_LOD, 0.0f);
            this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_MAX_LOD, (float)level);
            this.gfx.texParameter(textureTarget, TextureParameterName.TEXTURE_LOD_BIAS, 0.0f);
        }
        this.gfx.texImage2D(textureTarget, 0, 35056, width, height, 0, 34041, 34042, null);
    }
}
