// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import java.util.HashMap;
import net.labymod.api.client.render.shader.MojangShader;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.shader.MojangShaderRegistry;

@Singleton
@Implements(MojangShaderRegistry.class)
public final class DefaultMojangShaderRegistry implements MojangShaderRegistry
{
    private final Map<String, MojangShader> shaders;
    
    @Inject
    public DefaultMojangShaderRegistry() {
        this.shaders = new HashMap<String, MojangShader>();
    }
    
    @Override
    public void addShader(@NotNull final MojangShader shader) {
        this.shaders.put(shader.getName(), shader);
    }
    
    @Nullable
    @Override
    public MojangShader getShader(@NotNull final String name) {
        return this.shaders.get(name);
    }
}
