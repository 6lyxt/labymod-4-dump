// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateDrawPhase;
import net.labymod.api.client.gfx.target.RenderTarget;

public final class RenderUtil
{
    private static boolean customRenderType;
    private static boolean stencilPass;
    
    private RenderUtil() {
    }
    
    public static void enableCustomRenderType() {
        RenderUtil.customRenderType = true;
    }
    
    public static void disableCustomRenderType() {
        RenderUtil.customRenderType = false;
    }
    
    public static boolean isCustomRenderType() {
        return RenderUtil.customRenderType;
    }
    
    public static void enableStencilPass() {
        RenderUtil.stencilPass = true;
    }
    
    public static void disableStencilPass() {
        RenderUtil.stencilPass = false;
    }
    
    public static boolean isStencilPass() {
        return RenderUtil.stencilPass;
    }
    
    public static RenderTarget bindMainTarget() {
        if (!ImmediateDrawPhase.RENDER_TARGET_RENDER_PASS || Laby.gfx().getBindingFramebuffer() != 0) {
            return null;
        }
        final RenderTarget target = Laby.labyAPI().minecraft().mainTarget();
        target.bind(true);
        return target;
    }
}
