// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.gfx.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;

public class VersionedBlaze3DFog implements Blaze3DFog
{
    private float startFog;
    
    @Override
    public void enable() {
        final gkz parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new gkz(this.startFog, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void disable() {
        final gkz parameters = RenderSystem.getShaderFog();
        this.startFog = parameters.a();
        RenderSystem.setShaderFog(new gkz(Float.MAX_VALUE, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        final gkz parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new gkz(parameters.a(), parameters.b(), parameters.c(), red, green, blue, alpha));
    }
    
    @Override
    public void setStartDistance(final float startDistance) {
        final gkz parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new gkz(startDistance, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setEndDistance(final float endDistance) {
        final gkz parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new gkz(parameters.a(), endDistance, parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setDensity(final float density) {
    }
}
