// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;

public class VersionedBlaze3DFog implements Blaze3DFog
{
    private float startFog;
    
    @Override
    public void enable() {
        final grb parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new grb(this.startFog, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void disable() {
        final grb parameters = RenderSystem.getShaderFog();
        this.startFog = parameters.a();
        RenderSystem.setShaderFog(new grb(Float.MAX_VALUE, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        final grb parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new grb(parameters.a(), parameters.b(), parameters.c(), red, green, blue, alpha));
    }
    
    @Override
    public void setStartDistance(final float startDistance) {
        final grb parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new grb(startDistance, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setEndDistance(final float endDistance) {
        final grb parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new grb(parameters.a(), endDistance, parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setDensity(final float density) {
    }
}
