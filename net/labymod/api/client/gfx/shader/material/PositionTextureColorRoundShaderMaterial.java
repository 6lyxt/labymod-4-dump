// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;

public class PositionTextureColorRoundShaderMaterial extends GameShaderMaterial
{
    private final Uniform1F borderThickness;
    private final Uniform1F borderSoftness;
    private final Uniform4F borderColor;
    private final Uniform4F radius;
    private final Uniform2F size;
    private final Uniform4F vertices;
    private final Uniform1F lowerEdgeSoftness;
    private final Uniform1F upperEdgeSoftness;
    
    public PositionTextureColorRoundShaderMaterial(final ShaderProgram program) {
        super(program);
        this.borderThickness = program.getUniform("BorderThickness");
        this.borderSoftness = program.getUniform("BorderSoftness");
        this.borderColor = program.getUniform("BorderColor");
        this.radius = program.getUniform("Radius");
        this.size = program.getUniform("Size");
        this.vertices = program.getUniform("Vertices");
        this.lowerEdgeSoftness = program.getUniform("LowerEdgeSoftness");
        this.upperEdgeSoftness = program.getUniform("UpperEdgeSoftness");
    }
    
    public Uniform1F borderThickness() {
        return this.borderThickness;
    }
    
    public Uniform1F borderSoftness() {
        return this.borderSoftness;
    }
    
    public Uniform4F borderColor() {
        return this.borderColor;
    }
    
    public Uniform4F radius() {
        return this.radius;
    }
    
    public Uniform2F size() {
        return this.size;
    }
    
    public Uniform4F vertices() {
        return this.vertices;
    }
    
    public Uniform1F lowerEdgeSoftness() {
        return this.lowerEdgeSoftness;
    }
    
    public Uniform1F upperEdgeSoftness() {
        return this.upperEdgeSoftness;
    }
}
