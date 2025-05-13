// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.shader.program;

import net.labymod.api.client.render.shader.ShaderException;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.shader.ShaderProgram;
import net.labymod.api.client.render.shader.ShaderProvider;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable(named = true)
public abstract class ShaderInstance
{
    protected final Logging logging;
    protected final ShaderProvider shaderProvider;
    protected ShaderProgram shaderProgram;
    private boolean complied;
    
    protected ShaderInstance(final ShaderProvider shaderProvider) {
        this.logging = Logging.create("Shader Instance");
        this.shaderProvider = shaderProvider;
    }
    
    public void prepare(final OldVertexFormat format) {
        if (this.complied) {
            return;
        }
        try {
            this.internalPrepare(format);
            this.complied = true;
        }
        catch (final ShaderException exception) {
            this.complied = false;
            this.logging.error("The shader could not be compiled", exception);
        }
    }
    
    protected abstract void internalPrepare(final OldVertexFormat p0) throws ShaderException;
    
    public ShaderProgram shaderProgram() {
        return this.shaderProgram;
    }
    
    public boolean complied() {
        return this.complied;
    }
}
