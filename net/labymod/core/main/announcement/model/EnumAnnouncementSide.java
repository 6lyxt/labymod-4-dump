// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.announcement.model;

public enum EnumAnnouncementSide
{
    LEFT, 
    RIGHT;
    
    public boolean isRight() {
        return this == EnumAnnouncementSide.RIGHT;
    }
    
    public boolean isLeft() {
        return this == EnumAnnouncementSide.LEFT;
    }
}
