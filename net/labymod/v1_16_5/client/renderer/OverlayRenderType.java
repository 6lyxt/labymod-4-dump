// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Laby;

public class OverlayRenderType extends eao
{
    public static final OverlayRenderType INSTANCE;
    
    public OverlayRenderType() {
        super("labymod_overlay", dfk.l, 7, 256, false, true, () -> {
            Laby.gfx().storeBlaze3DStates();
            RenderSystem.disableTexture();
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
        }, () -> Laby.gfx().restoreBlaze3DStates());
    }
    
    static {
        INSTANCE = new OverlayRenderType();
    }
}
