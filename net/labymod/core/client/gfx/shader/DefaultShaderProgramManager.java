// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.shader;

import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.shader.ShaderProgramManager;

@Singleton
@Implements(ShaderProgramManager.class)
public class DefaultShaderProgramManager implements ShaderProgramManager
{
    private final List<ShaderProgram> shaderPrograms;
    
    @Inject
    public DefaultShaderProgramManager() {
        this.shaderPrograms = new ArrayList<ShaderProgram>();
    }
    
    @Override
    public ShaderProgram addShaderProgram(final ShaderProgram shaderProgram) {
        this.shaderPrograms.add(shaderProgram);
        return shaderProgram;
    }
    
    @Override
    public boolean removeShaderProgram(final ShaderProgram shaderProgram) {
        return this.shaderPrograms.remove(shaderProgram);
    }
    
    @Override
    public List<ShaderProgram> getShaders() {
        return this.shaderPrograms;
    }
}
