// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.client.render.shader.MojangShader;
import net.labymod.api.event.Event;

public class ConfigureMojangShaderEvent implements Event
{
    private final MojangShader shader;
    
    public ConfigureMojangShaderEvent(final MojangShader shader) {
        this.shader = shader;
    }
    
    public MojangShader shader() {
        return this.shader;
    }
}
