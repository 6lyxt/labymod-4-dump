// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.target.attachment;

public enum AttachmentTarget
{
    COLOR_ATTACHMENT(36064), 
    DEPTH_ATTACHMENT(36096), 
    STENCIL_ATTACHMENT(36128), 
    DEPTH_STENCIL_ATTACHMENT(33306);
    
    private final int id;
    
    private AttachmentTarget(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
