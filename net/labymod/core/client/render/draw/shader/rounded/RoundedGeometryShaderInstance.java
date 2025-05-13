// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader.rounded;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.render.shader.program.ShaderInstance;

public class RoundedGeometryShaderInstance extends ShaderInstance
{
    private UniformSampler diffuseSamplerUniform;
    private Uniform4F radiusUniform;
    private Uniform2F sizeRectUniform;
    private Uniform4F verticesUniform;
    private Uniform1F lowerEdgeSoftnessUniform;
    private Uniform1F upperEdgeSoftnessUniform;
    private Uniform1F borderThicknessUniform;
    private Uniform1F borderSoftnessUniform;
    private boolean needsDiffuseSampler;
    private Uniform4F borderColorUniform;
    
    public RoundedGeometryShaderInstance(final ShaderProvider shaderProvider) {
        super(shaderProvider);
    }
    
    public void setDiffuseSamplerTexture() {
        this.needsDiffuseSampler = true;
    }
    
    @Override
    protected void internalPrepare(final OldVertexFormat format) throws ShaderException {
        (this.shaderProgram = this.shaderProvider.createProgram(format).addFragmentShader(this.shader(true)).addVertexShader(this.shader(false))).link();
        if (this.needsDiffuseSampler) {
            this.diffuseSamplerUniform = this.shaderProgram.addUniform(new UniformSampler("DiffuseSampler", 0));
        }
        this.radiusUniform = this.shaderProgram.addUniform(new Uniform4F("Radius"));
        this.sizeRectUniform = this.shaderProgram.addUniform(new Uniform2F("Size"));
        this.verticesUniform = this.shaderProgram.addUniform(new Uniform4F("Vertices"));
        this.lowerEdgeSoftnessUniform = this.shaderProgram.addUniform(new Uniform1F("LowerEdgeSoftness"));
        this.upperEdgeSoftnessUniform = this.shaderProgram.addUniform(new Uniform1F("UpperEdgeSoftness"));
        this.borderThicknessUniform = this.shaderProgram.addUniform(new Uniform1F("BorderThickness"));
        this.borderSoftnessUniform = this.shaderProgram.addUniform(new Uniform1F("BorderSoftness"));
        this.borderColorUniform = this.shaderProgram.addUniform(new Uniform4F("BorderColor"));
    }
    
    public UniformSampler diffuseSamplerUniform() {
        return this.diffuseSamplerUniform;
    }
    
    public Uniform4F radiusUniform() {
        return this.radiusUniform;
    }
    
    public Uniform2F sizeRectUniform() {
        return this.sizeRectUniform;
    }
    
    public Uniform4F verticesUniform() {
        return this.verticesUniform;
    }
    
    public Uniform1F lowerEdgeSoftnessUniform() {
        return this.lowerEdgeSoftnessUniform;
    }
    
    public Uniform1F upperEdgeSoftnessUniform() {
        return this.upperEdgeSoftnessUniform;
    }
    
    public Uniform1F borderThicknessUniform() {
        return this.borderThicknessUniform;
    }
    
    public Uniform1F borderSoftnessUniform() {
        return this.borderSoftnessUniform;
    }
    
    public Uniform4F borderColorUniform() {
        return this.borderColorUniform;
    }
    
    private ResourceLocation shader(final boolean fragment) {
        if (this.needsDiffuseSampler) {
            return ResourceLocation.create("labymod", "shaders/geometry/round_texture." + (fragment ? "fsh" : "vsh"));
        }
        return ResourceLocation.create("labymod", "shaders/geometry/round_rectangle." + (fragment ? "fsh" : "vsh"));
    }
}
