// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard.shards;

import java.util.Objects;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.shader.ShaderProgram;
import net.labymod.api.client.render.vertex.shard.RenderShard;

public class ShaderProgramRenderShard extends RenderShard
{
    private final ShaderProgram shaderProgram;
    
    public ShaderProgramRenderShard(final ShaderProgram shaderProgram) {
        super("shader_program", shard -> shard.bridge().bindShader(shaderProgram), shard -> shard.bridge().unbindShader());
        this.shaderProgram = shaderProgram;
    }
    
    public ShaderProgramRenderShard(final String name, final OldVertexFormat format) {
        this(Laby.references().shaderInstance(name), format);
    }
    
    public ShaderProgramRenderShard(final ShaderInstance program, final OldVertexFormat format) {
        super("shader_program", shard -> {
            program.prepare(format);
            if (program.complied()) {
                shard.bridge().bindShader(program.shaderProgram());
            }
            return;
        }, shard -> {
            if (program.complied()) {
                shard.bridge().unbindShader();
            }
            return;
        });
        this.shaderProgram = program.shaderProgram();
    }
    
    public ShaderProgram getShaderProgram() {
        return this.shaderProgram;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ShaderProgramRenderShard that = (ShaderProgramRenderShard)o;
        return Objects.equals(this.shaderProgram, that.shaderProgram);
    }
    
    @Override
    public int hashCode() {
        return (this.shaderProgram == null) ? 0 : this.shaderProgram.hashCode();
    }
    
    @Override
    public String toString() {
        return super.toString() + "[" + String.valueOf(this.shaderProgram);
    }
}
