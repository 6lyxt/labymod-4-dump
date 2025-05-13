// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

public enum ChatTrustLevel
{
    SECURE(10526880), 
    NOT_SECURE(15224664), 
    FILTERED(15386724), 
    MODIFIED(15386724), 
    SYSTEM(13684944);
    
    private final int hexColor;
    
    private ChatTrustLevel(final int hexColor) {
        this.hexColor = hexColor;
    }
    
    public int getHexColor() {
        return this.hexColor;
    }
}
