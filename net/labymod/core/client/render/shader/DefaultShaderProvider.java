// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader;

import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.shader.ShaderProgram;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.shader.ShaderProvider;

@Singleton
@Implements(ShaderProvider.class)
public class DefaultShaderProvider implements ShaderProvider
{
    @Inject
    public DefaultShaderProvider() {
    }
    
    @Override
    public ShaderProgram createProgram() {
        return new DefaultShaderProgram(null);
    }
    
    @Override
    public ShaderProgram createProgram(final OldVertexFormat format) {
        return new DefaultShaderProgram(format);
    }
}
