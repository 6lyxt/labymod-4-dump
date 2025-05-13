// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.font;

public class DynamicStringRenderOutput extends eha.b
{
    public DynamicStringRenderOutput(final eha font, final ezs bufferSource, final float x, final float y, final int color, final boolean shadow, final d modelViewMatrix, final eha.a displayMode, final int packedLightCoords) {
        super(font, bufferSource, x, y, color, shadow, modelViewMatrix, displayMode, packedLightCoords);
    }
    
    public void setPosition(final float x, final float y) {
        this.l = x;
        this.m = y;
    }
    
    public void updateModelViewMatrix(final d modelViewMatrix) {
        ((StringRenderOutputAccessor)this).setModelViewMatrix(modelViewMatrix);
    }
    
    public void updateColor(final int color, final boolean shadow) {
        ((StringRenderOutputAccessor)this).setColor(color, shadow);
    }
}
