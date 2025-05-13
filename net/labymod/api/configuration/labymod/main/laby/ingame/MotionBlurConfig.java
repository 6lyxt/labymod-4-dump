// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface MotionBlurConfig
{
    ConfigProperty<MotionBlurType> motionBlurType();
    
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Integer> blurQuality();
    
    ConfigProperty<Float> blurStrength();
    
    public enum MotionBlurType
    {
        LABYMOD, 
        MIX(true, 0.0f, 0.9f), 
        MAX(true, 0.0f, 0.9f);
        
        private final boolean old;
        private final float min;
        private final float max;
        
        private MotionBlurType() {
            this(false, 0.0f, 1.0f);
        }
        
        private MotionBlurType(final boolean old, final float min, final float max) {
            this.old = old;
            this.min = min;
            this.max = max;
        }
        
        public boolean isOld() {
            return this.old;
        }
        
        public float clamp(final float value) {
            return MathHelper.clamp(value, this.min, this.max);
        }
    }
}
