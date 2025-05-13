// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;

public abstract class GameShaderMaterial extends ShaderMaterial
{
    @Nullable
    private final UniformMatrix4 projectionMatrix;
    @Nullable
    private final UniformMatrix4 modelViewMatrix;
    @Nullable
    private final Uniform4F colorModulator;
    private final UniformSampler diffuseSampler;
    
    public GameShaderMaterial(final ShaderProgram program) {
        super(program);
        this.projectionMatrix = program.getUniform("ProjectionMatrix");
        this.modelViewMatrix = program.getUniform("ModelViewMatrix");
        this.colorModulator = program.getUniform("ColorModulator");
        this.diffuseSampler = program.getUniform("DiffuseSampler");
    }
    
    @Nullable
    public UniformMatrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }
    
    @Nullable
    public UniformMatrix4 getModelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    @Nullable
    public Uniform4F getColorModulator() {
        return this.colorModulator;
    }
    
    @Nullable
    public UniformSampler getDiffuseSampler() {
        return this.diffuseSampler;
    }
}
