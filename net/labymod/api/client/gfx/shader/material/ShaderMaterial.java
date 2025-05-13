// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import java.util.function.Function;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.gfx.shader.UniformContext;
import java.util.function.Consumer;
import net.labymod.api.client.gfx.shader.ShaderProgram;

public abstract class ShaderMaterial
{
    private final ShaderProgram program;
    
    protected ShaderMaterial(final ShaderProgram program) {
        this.program = program;
    }
    
    @Contract("_ -> new")
    @NotNull
    public static Builder builder(final Consumer<UniformContext> uniformContext) {
        return new Builder(uniformContext);
    }
    
    public void apply() {
        this.program.apply();
    }
    
    public void clear() {
        this.program.clear();
    }
    
    @NotNull
    public ShaderProgram program() {
        return this.program;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final ShaderMaterial that = (ShaderMaterial)object;
        return Objects.equals(this.program, that.program);
    }
    
    @Override
    public int hashCode() {
        return (this.program != null) ? this.program.hashCode() : 0;
    }
    
    public static class Builder
    {
        private final ShaderProgram program;
        
        Builder(final Consumer<UniformContext> uniformContext) {
            this.program = Laby.references().shaderProgramFactory().create(uniformContext);
        }
        
        public Builder addVertexShader(final ResourceLocation location) {
            return this.addShader(location, Shader.Type.VERTEX);
        }
        
        public Builder addFragmentShader(final ResourceLocation location) {
            return this.addShader(location, Shader.Type.FRAGMENT);
        }
        
        public Builder addShader(final ResourceLocation location, final Shader.Type type) {
            this.program.addShader(location, type);
            return this;
        }
        
        public Builder addVertexShader(final ResourceLocation location, final ShaderConstants constants) {
            return this.addShader(location, Shader.Type.VERTEX, constants);
        }
        
        public Builder addFragmentShader(final ResourceLocation location, final ShaderConstants constants) {
            return this.addShader(location, Shader.Type.FRAGMENT, constants);
        }
        
        public Builder addShader(final ResourceLocation location, final Shader.Type type, final ShaderConstants constants) {
            this.program.addShader(location, type, constants);
            return this;
        }
        
        public Builder withDebugName(final String name) {
            this.program.setDebugName(name);
            return this;
        }
        
        public <T extends ShaderMaterial> T build(final Function<ShaderProgram, ShaderMaterial> shaderMaterialFactory) {
            return (T)shaderMaterialFactory.apply(this.program);
        }
    }
}
