// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gfx.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;

public class VersionedBlaze3DFog implements Blaze3DFog
{
    private float startFog;
    
    @Override
    public void enable() {
        RenderSystem.setShaderFogStart(this.startFog);
    }
    
    @Override
    public void disable() {
        this.startFog = RenderSystem.getShaderFogStart();
        RenderSystem.setShaderFogStart(Float.MAX_VALUE);
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        RenderSystem.setShaderFogColor(red, green, blue, alpha);
    }
    
    @Override
    public void setStartDistance(final float startDistance) {
        RenderSystem.setShaderFogStart(startDistance);
    }
    
    @Override
    public void setEndDistance(final float endDistance) {
        RenderSystem.setShaderFogEnd(endDistance);
    }
    
    @Override
    public void setDensity(final float density) {
    }
}
