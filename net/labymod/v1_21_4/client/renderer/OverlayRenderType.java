// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Laby;

public class OverlayRenderType extends gmj
{
    public static final OverlayRenderType INSTANCE;
    
    public OverlayRenderType() {
        super("labymod_overlay", fft.f, fga.c.h, 256, false, true, () -> {
            Laby.gfx().storeBlaze3DStates();
            RenderSystem.setShader(glk.e);
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
        }, () -> Laby.gfx().restoreBlaze3DStates());
    }
    
    static {
        INSTANCE = new OverlayRenderType();
    }
}
