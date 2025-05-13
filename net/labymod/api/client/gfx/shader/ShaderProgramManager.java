// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ShaderProgramManager
{
    ShaderProgram addShaderProgram(final ShaderProgram p0);
    
    boolean removeShaderProgram(final ShaderProgram p0);
    
    List<ShaderProgram> getShaders();
}
