// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.renderer;

import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Laby;

public class OverlayRenderType extends gdy
{
    public static final OverlayRenderType INSTANCE;
    
    public OverlayRenderType() {
        super("labymod_overlay", ezy.n, faf.b.h, 256, false, true, () -> {
            Laby.gfx().storeBlaze3DStates();
            RenderSystem.setShader((Supplier)gdj::p);
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
        }, () -> Laby.gfx().restoreBlaze3DStates());
    }
    
    static {
        INSTANCE = new OverlayRenderType();
    }
}
