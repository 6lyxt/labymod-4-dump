// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.client.gfx.shader.material.UnknownShaderMaterial;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class ShaderMaterialRenderParameter extends RenderParameter
{
    private final ShaderMaterial shaderMaterial;
    
    private ShaderMaterialRenderParameter(final ShaderMaterial shaderMaterial) {
        this.shaderMaterial = shaderMaterial;
    }
    
    public static ShaderMaterialRenderParameter of(final ShaderMaterial shaderMaterial) {
        return new ShaderMaterialRenderParameter(shaderMaterial);
    }
    
    public static ShaderMaterialRenderParameter unknown(final ShaderProgram shaderProgram) {
        return new ShaderMaterialRenderParameter(new UnknownShaderMaterial(shaderProgram));
    }
    
    @Override
    public void apply() {
        this.shaderMaterial.apply();
    }
    
    @Override
    public void clear() {
        this.shaderMaterial.clear();
    }
    
    public void link(final VertexFormat format) {
        final ShaderProgram program = this.shaderMaterial.program();
        if (program.isLinked()) {
            return;
        }
        program.link(format);
    }
    
    @NotNull
    public ShaderMaterial shaderMaterial() {
        return this.shaderMaterial;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final ShaderMaterialRenderParameter that = (ShaderMaterialRenderParameter)object;
        return Objects.equals(this.shaderMaterial, that.shaderMaterial);
    }
    
    @Override
    public int hashCode() {
        return (this.shaderMaterial != null) ? this.shaderMaterial.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "ShaderMaterialRenderParameter[" + String.valueOf(this.shaderMaterial);
    }
}
