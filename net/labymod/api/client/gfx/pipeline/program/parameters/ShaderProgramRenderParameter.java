// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

@Deprecated(since = "4.1.5", forRemoval = true)
public class ShaderProgramRenderParameter extends RenderParameter
{
    private final ShaderProgram shaderProgram;
    
    public ShaderProgramRenderParameter(final ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
    
    @Override
    public void apply() {
        this.shaderProgram.apply();
    }
    
    @Override
    public void clear() {
        this.shaderProgram.clear();
    }
    
    @NotNull
    public ShaderProgram shaderProgram() {
        return this.shaderProgram;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ShaderProgramRenderParameter that = (ShaderProgramRenderParameter)o;
        return Objects.equals(this.shaderProgram, that.shaderProgram);
    }
    
    @Override
    public int hashCode() {
        return (this.shaderProgram != null) ? this.shaderProgram.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "ShaderProgramRenderParameter[" + String.valueOf(this.shaderProgram);
    }
}
