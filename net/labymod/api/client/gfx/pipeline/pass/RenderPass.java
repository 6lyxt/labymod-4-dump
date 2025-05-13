// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.pass;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;

public abstract class RenderPass
{
    private final String name;
    protected final GFXRenderPipeline GFXRenderPipeline;
    protected final GFXBridge gfx;
    
    public RenderPass(final String name) {
        this.name = name;
        this.GFXRenderPipeline = Laby.labyAPI().gfxRenderPipeline();
        this.gfx = this.GFXRenderPipeline.gfx();
    }
    
    public abstract void begin();
    
    public abstract void end();
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
