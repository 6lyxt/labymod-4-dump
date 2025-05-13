// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.pass.passes;

import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.client.gfx.pipeline.pass.RenderPass;

public class StencilRenderPass extends RenderPass
{
    public StencilRenderPass() {
        super("stencil");
    }
    
    @Override
    public void begin() {
        RenderUtil.enableStencilPass();
        this.gfx.colorMask(false, false, false, false);
        this.gfx.stencilFunc(519, 1, 255);
        this.gfx.stencilMask(255);
        this.gfx.stencilOp(StencilOperation.KEEP, StencilOperation.KEEP, StencilOperation.REPLACE);
    }
    
    @Override
    public void end() {
        this.gfx.stencilOp(StencilOperation.KEEP, StencilOperation.KEEP, StencilOperation.KEEP);
        this.gfx.colorMask(true, true, true, true);
        this.gfx.stencilFunc(514, 1, 255);
        this.gfx.stencilMask(255);
        RenderUtil.disableStencilPass();
    }
}
