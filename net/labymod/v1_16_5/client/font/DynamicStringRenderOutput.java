// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.font;

public class DynamicStringRenderOutput extends dku.a
{
    public DynamicStringRenderOutput(final dku font, final eag bufferSource, final float x, final float y, final int color, final boolean shadow, final b modelViewMatrix, final boolean displayMode, final int packedLightCoords) {
        super(font, bufferSource, x, y, color, shadow, modelViewMatrix, displayMode, packedLightCoords);
    }
    
    public void setPosition(final float x, final float y) {
        this.l = x;
        this.m = y;
    }
    
    public void updateModelViewMatrix(final b modelViewMatrix) {
        ((StringRenderOutputAccessor)this).setModelViewMatrix(modelViewMatrix);
    }
    
    public void updateColor(final int color, final boolean shadow) {
        ((StringRenderOutputAccessor)this).setColor(color, shadow);
    }
}
