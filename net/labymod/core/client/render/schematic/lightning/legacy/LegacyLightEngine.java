// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning.legacy;

import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface LegacyLightEngine
{
    public static final ConfigProperty<Float> brightnessSetting = Laby.labyAPI().config().appearance().dynamicBackground().brightness();
    
    int getLightAt(final int p0, final int p1, final int p2);
    
    void handleLightUpdates();
    
    boolean isInProgress();
    
    boolean isDirty();
    
    void reset();
    
    float getAverageLightLevelAt(final int p0, final int p1, final int p2);
    
    byte[] getData();
    
    void setData(final byte[] p0);
    
    default boolean isFullyCalculated() {
        return !this.isInProgress() && !this.isDirty();
    }
    
    default float getRedStrengthAt(final int x, final int y, final int z) {
        return this.getStrengthAt(x, y, z);
    }
    
    default float getGreenStrengthAt(final int x, final int y, final int z) {
        return this.getStrengthAt(x, y, z);
    }
    
    default float getBlueStrengthAt(final int x, final int y, final int z) {
        return this.getStrengthAt(x, y, z);
    }
    
    default float getStrengthAt(final int x, final int y, final int z) {
        return this.getStrengthFrom(this.getAverageLightLevelAt(x, y, z));
    }
    
    default float getStrengthFrom(final float level) {
        return this.applyBrightness(0.06f * level + 0.1f);
    }
    
    default float applyBrightness(final float value) {
        return Math.min(value * LegacyLightEngine.brightnessSetting.get(), 1.0f);
    }
}
