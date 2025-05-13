// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.material;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;

public class KawaseShaderMaterial extends GameShaderMaterial
{
    private final Uniform2F size;
    private final Uniform2F offset;
    
    public KawaseShaderMaterial(final ShaderProgram program) {
        super(program);
        this.size = program.getUniform("Size");
        this.offset = program.getUniform("Offset");
    }
    
    public Uniform2F size() {
        return this.size;
    }
    
    public Uniform2F offset() {
        return this.offset;
    }
}
