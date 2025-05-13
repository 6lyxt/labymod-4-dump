// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.font;

import org.joml.Matrix4f;

public class DynamicStringRenderOutput extends fod.b
{
    public DynamicStringRenderOutput(final fod font, final glz bufferSource, final float x, final float y, final int color, final boolean shadow, final Matrix4f modelViewMatrix, final fod.a displayMode, final int packedLightCoords) {
        super(font, bufferSource, x, y, color, shadow, modelViewMatrix, displayMode, packedLightCoords);
    }
    
    public DynamicStringRenderOutput(final fod font, final glz bufferSource, final float x, final float y, final int color, final int backgroundColor, final boolean shadow, final Matrix4f modelViewMatrix, final fod.a displayMode, final int packedLightCoords, final boolean inverseDepth) {
        super(font, bufferSource, x, y, color, backgroundColor, shadow, modelViewMatrix, displayMode, packedLightCoords, inverseDepth);
    }
    
    public void setPosition(final float x, final float y) {
        this.j = x;
        this.k = y;
    }
    
    public void updateModelViewMatrix(final Matrix4f modelViewMatrix) {
        ((StringRenderOutputAccessor)this).setModelViewMatrix(modelViewMatrix);
    }
    
    public void updateColor(final int color, final boolean shadow) {
        ((StringRenderOutputAccessor)this).setColor(color, shadow);
    }
}
