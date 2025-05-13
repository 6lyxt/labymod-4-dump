// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model;

public enum AttachmentPoint
{
    NONE(false), 
    BODY(false), 
    HEAD(false), 
    ARM(true), 
    LEG(true);
    
    private final boolean canBeMirrored;
    
    private AttachmentPoint(final boolean canBeMirrored) {
        this.canBeMirrored = canBeMirrored;
    }
    
    public boolean canBeMirrored() {
        return this.canBeMirrored;
    }
    
    public boolean isHeadOrBody() {
        return this == AttachmentPoint.HEAD || this == AttachmentPoint.BODY;
    }
    
    public boolean isArmOrLeg() {
        return this == AttachmentPoint.ARM || this == AttachmentPoint.LEG;
    }
}
