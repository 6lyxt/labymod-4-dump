// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.renderer;

import net.labymod.api.event.client.render.ConfigureMojangShaderEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.render.shader.MojangShader;
import net.labymod.api.client.render.shader.MojangShaderRegistry;

public class ShaderInstanceExtension
{
    private final MojangShaderRegistry mojangShaderRegistry;
    private MojangShader shader;
    
    public ShaderInstanceExtension() {
        this.mojangShaderRegistry = Laby.references().mojangShaderRegistry();
    }
    
    public void registerCustomUniform(final ezp shader, final ged shaderInstance) {
        ezo.b(shader);
        this.shader = new MojangShader(shaderInstance.i(), shaderInstance.a());
        this.mojangShaderRegistry.addShader(this.shader);
        Laby.fireEvent(new ConfigureMojangShaderEvent(this.shader));
    }
    
    public void applyCustomUniforms() {
        this.shader.uploadUniforms();
    }
}
