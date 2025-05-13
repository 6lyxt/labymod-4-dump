// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.box;

public interface ModelBox
{
    float getMinX();
    
    float getMinY();
    
    float getMinZ();
    
    float getMaxX();
    
    float getMaxY();
    
    float getMaxZ();
    
    float getWidth();
    
    float getHeight();
    
    float getDepth();
    
    boolean isMirror();
    
    ModelBoxQuad[] getQuads();
}
