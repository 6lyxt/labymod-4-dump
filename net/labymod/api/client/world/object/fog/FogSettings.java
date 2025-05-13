// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.object.fog;

import net.labymod.api.util.color.format.ColorFormat;

public interface FogSettings
{
    public static final FogSettings NO_FOG = new FogSettings() {
        @Override
        public boolean isFog() {
            return false;
        }
        
        @Override
        public void setFog(final boolean fog) {
        }
        
        @Override
        public float getStartDistance() {
            return 0.0f;
        }
        
        @Override
        public void setStartDistance(final float start) {
        }
        
        @Override
        public float getEndDistance() {
            return 0.0f;
        }
        
        @Override
        public void setEndDistance(final float end) {
        }
        
        @Override
        public float[] getColorBuffer() {
            return new float[0];
        }
        
        @Override
        public void setColor(final float red, final float green, final float blue, final float alpha) {
        }
    };
    
    boolean isFog();
    
    void setFog(final boolean p0);
    
    float getStartDistance();
    
    void setStartDistance(final float p0);
    
    float getEndDistance();
    
    void setEndDistance(final float p0);
    
    float[] getColorBuffer();
    
    default void setColor(final int argb) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float red = colorFormat.normalizedRed(argb);
        final float green = colorFormat.normalizedGreen(argb);
        final float blue = colorFormat.normalizedBlue(argb);
        final float alpha = colorFormat.normalizedAlpha(argb);
        this.setColor(red, green, blue, alpha);
    }
    
    void setColor(final float p0, final float p1, final float p2, final float p3);
}
