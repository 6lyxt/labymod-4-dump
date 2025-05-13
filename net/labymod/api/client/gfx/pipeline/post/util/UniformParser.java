// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.util;

import net.labymod.api.client.gfx.shader.uniform.AbstractUniform;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix3;
import net.labymod.api.client.gfx.shader.uniform.Uniform4I;
import net.labymod.api.client.gfx.shader.uniform.Uniform3I;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.Uniform;

public final class UniformParser
{
    private int currentSamplerIndex;
    
    public void begin() {
        this.currentSamplerIndex = 1;
    }
    
    public Uniform parse(final String type, final String name) {
        return switch (type) {
            case "float" -> new Uniform1F(name);
            case "vec2" -> new Uniform2F(name);
            case "vec3" -> new Uniform3F(name);
            case "vec4" -> new Uniform4F(name);
            case "int" -> new Uniform1I(name);
            case "ivec2" -> new Uniform2I(name);
            case "ivec3" -> new Uniform3I(name);
            case "ivec4" -> new Uniform4I(name);
            case "mat3" -> new UniformMatrix3(name);
            case "mat4" -> new UniformMatrix4(name);
            case "sampler2D" -> {
                final UniformSampler sampler = new UniformSampler(name, this.currentSamplerIndex);
                ++this.currentSamplerIndex;
                yield sampler;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
