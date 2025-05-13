// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.shader.rounded;

import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.client.render.shader.ShaderProvider;
import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class RoundedGeometryShaderInstanceHolder
{
    private final Map<String, RoundedGeometryShaderInstance> shaders;
    
    @Inject
    public RoundedGeometryShaderInstanceHolder(final ShaderProvider shaderProvider) {
        (this.shaders = new HashMap<String, RoundedGeometryShaderInstance>(2)).put("rectangle", new RoundedGeometryShaderInstance(shaderProvider));
        this.shaders.put("texture", new RoundedGeometryShaderInstance(shaderProvider));
        this.shaders.put("framebuffer", new RoundedGeometryShaderInstance(shaderProvider));
    }
    
    public RoundedGeometryShaderInstance get(final String name) {
        return this.shaders.get(name);
    }
}
