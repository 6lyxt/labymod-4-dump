// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.renderer;

import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Laby;

public class OverlayRenderType extends gfh
{
    public static final OverlayRenderType INSTANCE;
    
    public OverlayRenderType() {
        super("labymod_overlay", fbg.f, fbn.c.h, 256, false, true, () -> {
            Laby.gfx().storeBlaze3DStates();
            RenderSystem.setShader((Supplier)ges::p);
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
        }, () -> Laby.gfx().restoreBlaze3DStates());
    }
    
    static {
        INSTANCE = new OverlayRenderType();
    }
}
