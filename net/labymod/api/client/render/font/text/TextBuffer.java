// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

public interface TextBuffer
{
    float getWidth();
    
    boolean needsVanilla();
    
    void uploadToBuffer();
}
