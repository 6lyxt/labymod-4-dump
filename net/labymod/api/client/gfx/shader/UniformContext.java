// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader;

import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix3;
import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gfx.shader.uniform.Uniform4I;
import net.labymod.api.client.gfx.shader.uniform.Uniform3I;
import net.labymod.api.client.gfx.shader.uniform.Uniform2I;
import net.labymod.api.client.gfx.shader.uniform.Uniform1I;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import net.labymod.api.client.gfx.shader.uniform.Uniform3F;
import net.labymod.api.client.gfx.shader.uniform.Uniform2F;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.uniform.UniformSampler;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import net.labymod.api.client.gfx.shader.uniform.UniformBuffer;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.shader.uniform.Uniform;

public interface UniformContext
{
     <T extends Uniform> T addUniform(final T p0);
    
     <T extends UniformBuffer> T addUniformBuffer(final Supplier<T> p0);
    
     <T extends Uniform> T getUniform(final String p0);
    
    Collection<Uniform> getUniforms();
    
    Collection<UniformBuffer> getUniformBuffers();
    
    void setUniformApplier(@Nullable final UniformApplier p0);
    
    default void setSampler(final String name, final int textureId) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final UniformSampler uniformSampler) {
            uniformSampler.set(textureId);
            ShaderTextures.setShaderTexture(uniformSampler.getSamplerIndex(), textureId);
        }
    }
    
    default void setFloat(final String name, final float value) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform1F uniform1F) {
            uniform1F.set(value);
        }
    }
    
    default void setVec2(final String name, final float x, final float y) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform2F uniform2F) {
            uniform2F.set(x, y);
        }
    }
    
    default void setVec3(final String name, final float x, final float y, final float z) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform3F uniform3F) {
            uniform3F.set(x, y, z);
        }
    }
    
    default void setVec4(final String name, final float x, final float y, final float z, final float w) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform4F uniform4F) {
            uniform4F.set(x, y, z, w);
        }
    }
    
    default void setInt(final String name, final int value) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform1I uniform1I) {
            uniform1I.set(value);
        }
    }
    
    default void setIntVec2(final String name, final int x, final int y) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform2I uniform2I) {
            uniform2I.set(x, y);
        }
    }
    
    default void setIntVec3(final String name, final int x, final int y, final int z) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform3I uniform3I) {
            uniform3I.set(x, y, z);
        }
    }
    
    default void setIntVec4(final String name, final int x, final int y, final int z, final int w) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final Uniform4I uniform4I) {
            uniform4I.set(x, y, z, w);
        }
    }
    
    default void setMatrix4x4(final String name, final FloatMatrix4 matrix) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final UniformMatrix4 uniformMatrix4) {
            uniformMatrix4.set(matrix);
        }
    }
    
    default void setMatrix4x4(final String name, final FloatBuffer matrix) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final UniformMatrix4 uniformMatrix4) {
            uniformMatrix4.set(matrix);
        }
    }
    
    default void setMatrix3x3(final String name, final FloatMatrix3 matrix) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final UniformMatrix3 uniformMatrix3) {
            uniformMatrix3.set(matrix);
        }
    }
    
    default void setMatrix3x3(final String name, final FloatBuffer matrix) {
        final Uniform uniform = this.getUniform(name);
        if (uniform instanceof final UniformMatrix3 uniformMatrix3) {
            uniformMatrix3.set(matrix);
        }
    }
    
    @FunctionalInterface
    public interface UniformApplier
    {
        void apply(final GFXRenderPipeline p0);
    }
}
