// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.object.fog;

public class DefaultFogSettings implements FogSettings
{
    private boolean fog;
    private float startDistance;
    private float endDistance;
    private float[] colorBuffer;
    
    public DefaultFogSettings() {
        this.colorBuffer = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    }
    
    @Override
    public boolean isFog() {
        return this.fog;
    }
    
    @Override
    public void setFog(final boolean fog) {
        this.fog = fog;
    }
    
    @Override
    public float getStartDistance() {
        return this.startDistance;
    }
    
    @Override
    public void setStartDistance(final float start) {
        this.startDistance = start;
    }
    
    @Override
    public float getEndDistance() {
        return this.endDistance;
    }
    
    @Override
    public void setEndDistance(final float end) {
        this.endDistance = end;
    }
    
    @Override
    public float[] getColorBuffer() {
        return this.colorBuffer;
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        this.colorBuffer[0] = red;
        this.colorBuffer[1] = green;
        this.colorBuffer[2] = blue;
        this.colorBuffer[3] = alpha;
    }
}
