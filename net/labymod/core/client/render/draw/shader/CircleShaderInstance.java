// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import javax.inject.Inject;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.shader.program.ShaderInstance;

@Singleton
@Implements(value = ShaderInstance.class, key = "circle_shader_instance")
public class CircleShaderInstance extends ShaderInstance
{
    private Uniform2F circleCenterUniform;
    private Uniform1F circleRadiusUniform;
    private Uniform1F circleInnerRadiusUniform;
    private Uniform1F circleStartAngleUniform;
    private Uniform1F circleEndAngleUniform;
    
    @Inject
    public CircleShaderInstance(final ShaderProvider shaderProvider) {
        super(shaderProvider);
    }
    
    @Override
    protected void internalPrepare(final OldVertexFormat format) throws ShaderException {
        (this.shaderProgram = this.shaderProvider.createProgram(format).addFragmentShader(this.shader(true)).addVertexShader(this.shader(false))).link();
        this.circleCenterUniform = this.shaderProgram.addUniform(new Uniform2F("circleCenter"));
        this.circleRadiusUniform = this.shaderProgram.addUniform(new Uniform1F("circleRadius"));
        this.circleInnerRadiusUniform = this.shaderProgram.addUniform(new Uniform1F("circleInnerRadius"));
        this.circleStartAngleUniform = this.shaderProgram.addUniform(new Uniform1F("circleStartAngle"));
        this.circleEndAngleUniform = this.shaderProgram.addUniform(new Uniform1F("circleEndAngle"));
    }
    
    public Uniform2F circleCenterUniform() {
        return this.circleCenterUniform;
    }
    
    public Uniform1F circleRadiusUniform() {
        return this.circleRadiusUniform;
    }
    
    public Uniform1F circleInnerRadiusUniform() {
        return this.circleInnerRadiusUniform;
    }
    
    public Uniform1F circleStartAngleUniform() {
        return this.circleStartAngleUniform;
    }
    
    public Uniform1F circleEndAngleUniform() {
        return this.circleEndAngleUniform;
    }
    
    private ResourceLocation shader(final boolean fragment) {
        return ResourceLocation.create("labymod", "shaders/geometry/circle." + (fragment ? "fsh" : "vsh"));
    }
}
