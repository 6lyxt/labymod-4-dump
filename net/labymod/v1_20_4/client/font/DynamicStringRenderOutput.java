// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.font;

import org.joml.Matrix4f;

public class DynamicStringRenderOutput extends ews.b
{
    public DynamicStringRenderOutput(final ews font, final fth bufferSource, final float x, final float y, final int color, final boolean shadow, final Matrix4f modelViewMatrix, final ews.a displayMode, final int packedLightCoords) {
        super(font, bufferSource, x, y, color, shadow, modelViewMatrix, displayMode, packedLightCoords);
    }
    
    public void setPosition(final float x, final float y) {
        this.l = x;
        this.m = y;
    }
    
    public void updateModelViewMatrix(final Matrix4f modelViewMatrix) {
        ((StringRenderOutputAccessor)this).setModelViewMatrix(modelViewMatrix);
    }
    
    public void updateColor(final int color, final boolean shadow) {
        ((StringRenderOutputAccessor)this).setColor(color, shadow);
    }
}
