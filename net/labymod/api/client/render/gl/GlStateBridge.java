// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.gl;

import net.labymod.api.client.render.shader.ShaderProgram;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GlStateBridge
{
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void lineWidth(final float width) {
        Laby.gfx().blaze3DGlStatePipeline().setLineWidth(width);
    }
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void color4f(final float red, final float green, final float blue, final float alpha) {
        Laby.gfx().blaze3DGlStatePipeline().color4f(red, green, blue, alpha);
    }
    
    ShaderProgram shaderProgram();
    
    default void applyShader(final Runnable fallback) {
        final ShaderProgram shaderProgram = this.shaderProgram();
        if (shaderProgram != null) {
            shaderProgram.bind();
            return;
        }
        if (fallback == null) {
            return;
        }
        fallback.run();
    }
    
    default void clearShader(final Runnable fallback) {
        final ShaderProgram shaderProgram = this.shaderProgram();
        if (shaderProgram != null) {
            shaderProgram.unbind();
            return;
        }
        if (fallback == null) {
            return;
        }
        fallback.run();
    }
    
    void bindShader(final ShaderProgram p0);
    
    default void unbindShader() {
        this.bindShader(null);
    }
}
