// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;

public final class TranslucentSkins
{
    private static final GFXBridge GFX;
    
    public static void enableBlend() {
        if (!isEnabled()) {
            return;
        }
        TranslucentSkins.GFX.enableBlend();
        TranslucentSkins.GFX.blendSeparate(770, 771, 1, 0);
    }
    
    public static void disableBlend() {
        if (!isEnabled()) {
            return;
        }
        TranslucentSkins.GFX.disableBlend();
    }
    
    private static boolean isEnabled() {
        return Laby.labyAPI().config().ingame().translucentSkins().get();
    }
    
    static {
        GFX = Laby.gfx();
    }
}
