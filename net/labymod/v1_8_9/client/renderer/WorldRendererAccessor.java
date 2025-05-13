// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.renderer;

import java.nio.ByteBuffer;

public interface WorldRendererAccessor
{
    ByteBuffer getBuffer();
    
    int getNextElementPosition();
    
    boolean building();
    
    void updateVertexFormatIndex();
}
