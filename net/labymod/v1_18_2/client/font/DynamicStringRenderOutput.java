// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.font;

public class DynamicStringRenderOutput extends dzp.b
{
    public DynamicStringRenderOutput(final dzp font, final eqs bufferSource, final float x, final float y, final int color, final boolean shadow, final d modelViewMatrix, final dzp.a displayMode, final int packedLightCoords) {
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
