// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.event.Event;

public class SetupVertexFormatShaderConstantsEvent implements Event
{
    private final String name;
    private final Shader.Type shaderType;
    private ShaderConstants shaderConstants;
    
    public SetupVertexFormatShaderConstantsEvent(final String name, final Shader.Type type) {
        this.shaderConstants = ShaderConstants.EMPTY;
        this.name = name;
        this.shaderType = type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Shader.Type shaderType() {
        return this.shaderType;
    }
    
    public ShaderConstants shaderConstants() {
        return this.shaderConstants;
    }
    
    public void setShaderConstants(@NotNull final ShaderConstants shaderConstants) {
        Objects.requireNonNull(shaderConstants);
        this.shaderConstants = shaderConstants;
    }
}
