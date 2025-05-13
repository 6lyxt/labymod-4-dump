// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

public final class GlColorAlphaModifier
{
    private static boolean modifiedAlpha;
    private static float alpha;
    
    private GlColorAlphaModifier() {
    }
    
    public static boolean isModifiedAlpha() {
        return GlColorAlphaModifier.modifiedAlpha;
    }
    
    public static float getAlpha() {
        return GlColorAlphaModifier.alpha;
    }
    
    public static void setAlpha(final float alpha) {
        GlColorAlphaModifier.modifiedAlpha = (alpha < 1.0f);
        GlColorAlphaModifier.alpha = alpha;
    }
}
