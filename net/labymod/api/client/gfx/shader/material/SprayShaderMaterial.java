// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;

public class SprayShaderMaterial extends GameShaderMaterial
{
    private final Uniform1F wear;
    private final Uniform2I lightCoords;
    
    public SprayShaderMaterial(final ShaderProgram program) {
        super(program);
        this.wear = program.getUniform("Wear");
        this.lightCoords = program.getUniform("LightCoords");
    }
    
    public Uniform1F getWear() {
        return this.wear;
    }
    
    public Uniform2I getLightCoords() {
        return this.lightCoords;
    }
}
