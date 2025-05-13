// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.attachment;

import java.util.function.IntSupplier;

public class CustomColorRenderTargetAttachment extends ColorRenderTargetAttachment
{
    private final IntSupplier customTextureId;
    
    public CustomColorRenderTargetAttachment(final int attachmentTarget, final IntSupplier customTextureId) {
        super(attachmentTarget);
        this.customTextureId = customTextureId;
    }
    
    @Override
    public void configureFramebufferTexture(final int width, final int height) {
    }
    
    @Override
    public int getId() {
        return this.customTextureId.getAsInt();
    }
}
