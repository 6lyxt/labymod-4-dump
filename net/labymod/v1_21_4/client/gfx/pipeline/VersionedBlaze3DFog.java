// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gfx.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;

public class VersionedBlaze3DFog implements Blaze3DFog
{
    private float startFog;
    
    @Override
    public void enable() {
        final glo parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new glo(this.startFog, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void disable() {
        final glo parameters = RenderSystem.getShaderFog();
        this.startFog = parameters.a();
        RenderSystem.setShaderFog(new glo(Float.MAX_VALUE, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        final glo parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new glo(parameters.a(), parameters.b(), parameters.c(), red, green, blue, alpha));
    }
    
    @Override
    public void setStartDistance(final float startDistance) {
        final glo parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new glo(startDistance, parameters.b(), parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setEndDistance(final float endDistance) {
        final glo parameters = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(new glo(parameters.a(), endDistance, parameters.c(), parameters.d(), parameters.e(), parameters.f(), parameters.g()));
    }
    
    @Override
    public void setDensity(final float density) {
    }
}
