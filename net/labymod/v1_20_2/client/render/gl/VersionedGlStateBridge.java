// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.render.gl;

import javax.inject.Inject;
import net.labymod.api.client.render.shader.ShaderProgram;
import net.labymod.api.client.render.gl.GlStateBridge;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.gl.AbstractGlStateBridge;

@Singleton
@Implements(GlStateBridge.class)
public class VersionedGlStateBridge extends AbstractGlStateBridge
{
    private ShaderProgram shaderProgram;
    
    @Inject
    public VersionedGlStateBridge() {
    }
    
    @Override
    public ShaderProgram shaderProgram() {
        return (this.shaderProgram == null) ? null : this.shaderProgram;
    }
    
    @Override
    public void bindShader(final ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }
}
