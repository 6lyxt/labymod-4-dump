// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class FontSize
{
    private final PredefinedFontSize predefined;
    private final float customValue;
    
    private FontSize(final PredefinedFontSize predefined, final float customValue) {
        this.predefined = predefined;
        this.customValue = customValue;
    }
    
    @NotNull
    public static FontSize predefined(@NotNull final PredefinedFontSize predefined) {
        return predefined.fontSize();
    }
    
    @NotNull
    public static FontSize custom(final float fontSize) {
        return new FontSize(null, fontSize);
    }
    
    @Nullable
    public static FontSize nextLowerPredefined(final float fontSize) {
        PredefinedFontSize nearest = null;
        float nearestValue = -1.0f;
        final PredefinedFontSize[] values = PredefinedFontSize.VALUES;
        for (int i = values.length - 1; i >= 0; --i) {
            final PredefinedFontSize predefined = values[i];
            final float predefinedValue = predefined.fontSize().getFontSize();
            if (predefinedValue <= fontSize && (nearestValue == -1.0f || nearestValue < predefinedValue)) {
                nearest = predefined;
                nearestValue = predefinedValue;
            }
        }
        return (nearest != null) ? nearest.fontSize() : null;
    }
    
    @Nullable
    public PredefinedFontSize getPredefined() {
        return this.predefined;
    }
    
    public float getFontSize() {
        if (this.predefined != null) {
            float guiScale = Laby.labyAPI().minecraft().minecraftWindow().getScale();
            if (guiScale == 0.0f) {
                guiScale = 1.0f;
            }
            return this.predefined.value((int)guiScale);
        }
        return this.customValue;
    }
    
    public enum PredefinedFontSize
    {
        SMALL(new float[] { 0.75f, 0.5f, 0.67f, 0.75f, 0.6f, 0.67f }), 
        MEDIUM(new float[] { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f }), 
        LARGE(new float[] { 2.0f, 1.5f, 1.67f, 1.5f, 1.67f, 1.5f });
        
        private static final PredefinedFontSize[] VALUES;
        private final FontSize fontSize;
        private final float[] values;
        
        private PredefinedFontSize(final float[] values) {
            this.fontSize = new FontSize(this, -1.0f);
            this.values = values;
        }
        
        @NotNull
        public FontSize fontSize() {
            return this.fontSize;
        }
        
        public float value(final int guiScale) {
            return (guiScale > 0 && this.values.length > guiScale - 1) ? this.values[guiScale - 1] : ((this.values.length > 0) ? this.values[this.values.length - 1] : 1.0f);
        }
        
        @Nullable
        public static PredefinedFontSize of(final String value) {
            if (value == null) {
                return null;
            }
            for (final PredefinedFontSize predefinedFontSize : PredefinedFontSize.VALUES) {
                if (value.equals(predefinedFontSize.name())) {
                    return predefinedFontSize;
                }
            }
            return null;
        }
        
        static {
            VALUES = values();
        }
    }
}
