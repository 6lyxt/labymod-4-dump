// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.lighting;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;

public final class LightmapController
{
    private static boolean cachedLightmapState;
    
    public static void apply(final Stack stack) {
        if (Laby.references().renderEnvironmentContext().isScreenContext()) {
            DummyLightTexture.getInstance().apply();
        }
        else {
            final buq entityRenderer = bib.z().o;
            LightmapController.cachedLightmapState = ((LightmapState)entityRenderer).isEnabled();
            entityRenderer.i();
        }
    }
    
    public static void clear() {
        DummyLightTexture.getInstance().clear();
        final buq entityRenderer = bib.z().o;
        entityRenderer.h();
        if (LightmapController.cachedLightmapState) {
            Laby.gfx().blaze3DGlStatePipeline().enableLightMap();
            LightmapController.cachedLightmapState = false;
        }
    }
    
    static {
        LightmapController.cachedLightmapState = false;
    }
}
