// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

import java.util.function.Consumer;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.api.util.Disposable;

public interface ShaderProgram extends UniformContext, Disposable
{
    int getId();
    
    void bind();
    
    void unbind();
    
    void apply();
    
    void clear();
    
    void link(final VertexFormat p0);
    
    boolean isLinked();
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default Shader addShader(final ResourceLocation location, final ShaderType type) {
        final int handle = type.getHandle();
        final Shader.Type shaderType = switch (handle) {
            case 35633 -> Shader.Type.VERTEX;
            case 35632 -> Shader.Type.FRAGMENT;
            case 36313 -> Shader.Type.GEOMETRY;
            case 36488 -> Shader.Type.TESS_CONTROL;
            case 36487 -> Shader.Type.TESS_EVALUATION;
            case 37305 -> Shader.Type.COMPUTE;
            default -> throw new IllegalStateException("Unexpected value: " + handle);
        };
        return this.addShader(location, shaderType, ShaderConstants.EMPTY);
    }
    
    default Shader addShader(final ResourceLocation location, final Shader.Type type) {
        return this.addShader(location, type, ShaderConstants.EMPTY);
    }
    
    Shader addShader(final ResourceLocation p0, final Shader.Type p1, final ShaderConstants p2);
    
    VertexFormat vertexFormat();
    
    void setDebugName(final String p0);
    
    void recompile();
    
    void disposeAndDelete();
    
    @Referenceable
    public interface Factory
    {
        ShaderProgram create(final Consumer<UniformContext> p0);
    }
}
