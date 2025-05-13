// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gfx.pipeline;

import org.lwjgl.opengl.GL11;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;

public class VersionedBlaze3DFog implements Blaze3DFog
{
    private static final float[] COLOR_BUFFER;
    
    @Override
    public void enable() {
        dem.A();
    }
    
    @Override
    public void disable() {
        dem.B();
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        VersionedBlaze3DFog.COLOR_BUFFER[0] = red;
        VersionedBlaze3DFog.COLOR_BUFFER[1] = green;
        VersionedBlaze3DFog.COLOR_BUFFER[2] = blue;
        VersionedBlaze3DFog.COLOR_BUFFER[3] = alpha;
        GL11.glFogfv(2918, VersionedBlaze3DFog.COLOR_BUFFER);
    }
    
    @Override
    public void setStartDistance(final float startDistance) {
        dem.b(startDistance);
    }
    
    @Override
    public void setEndDistance(final float endDistance) {
        dem.c(endDistance);
    }
    
    @Override
    public void setDensity(final float density) {
        dem.a(density);
    }
    
    static {
        COLOR_BUFFER = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    }
}
