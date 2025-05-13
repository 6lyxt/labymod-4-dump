// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;

public abstract class RenderParameter
{
    protected final GFXBridge gfx;
    
    public RenderParameter() {
        this.gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
    }
    
    public abstract void apply();
    
    public abstract void clear();
}
