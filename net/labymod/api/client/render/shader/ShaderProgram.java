// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.shader;

import net.labymod.api.client.gfx.shader.uniform.Uniform;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.shader.UniformContext;

public interface ShaderProgram extends UniformContext
{
    ShaderProgram addVertexShader(final ResourceLocation p0) throws ShaderException;
    
    ShaderProgram addFragmentShader(final ResourceLocation p0) throws ShaderException;
    
    ShaderProgram addVertexShader(final String p0) throws ShaderException;
    
    ShaderProgram addFragmentShader(final String p0) throws ShaderException;
    
    void link() throws ShaderException;
    
    void bind();
    
    void unbind();
    
    void updateProvidedUniforms();
    
    void addProvidedUniform(final Uniform p0);
    
    int getProgramID();
    
    int getVertexShaderID();
    
    int getFragmentShaderID();
    
    boolean isLinked();
    
    void setupVertexAttributeLocations();
    
    ShaderProgram bindAttributeLocation(final int p0, final CharSequence p1);
    
    int getAttributeLocation(final CharSequence p0);
}
