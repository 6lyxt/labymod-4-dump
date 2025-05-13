// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.IntBuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import java.nio.FloatBuffer;
import net.labymod.api.Laby;
import java.nio.ByteBuffer;

public final class ShaderFixer
{
    public static void glShaderSource(final int shader, final ByteBuffer buffer) {
        final byte[] data = new byte[buffer.limit()];
        buffer.position(0);
        buffer.get(data);
        buffer.position(0);
        Laby.gfx().shaderSource(shader, new String(data));
    }
    
    public static void glUniform1fv(final boolean arbShaders, final int location, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1fvARB(location, buffer);
        }
        else {
            Laby.gfx().uniform1fv(location, buffer);
        }
    }
    
    public static void glUniform1iv(final boolean arbShaders, final int location, final IntBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform1ivARB(location, buffer);
        }
        else {
            Laby.gfx().uniform1iv(location, buffer);
        }
    }
    
    public static void glUniform2fv(final boolean arbShaders, final int location, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2fvARB(location, buffer);
        }
        else {
            Laby.gfx().uniform2fv(location, buffer);
        }
    }
    
    public static void glUniform2iv(final boolean arbShaders, final int location, final IntBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform2ivARB(location, buffer);
        }
        else {
            Laby.gfx().uniform2iv(location, buffer);
        }
    }
    
    public static void glUniform3fv(final boolean arbShaders, final int location, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3fvARB(location, buffer);
        }
        else {
            Laby.gfx().uniform3fv(location, buffer);
        }
    }
    
    public static void glUniform3iv(final boolean arbShaders, final int location, final IntBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform3ivARB(location, buffer);
        }
        else {
            Laby.gfx().uniform3iv(location, buffer);
        }
    }
    
    public static void glUniform4fv(final boolean arbShaders, final int location, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4fvARB(location, buffer);
        }
        else {
            Laby.gfx().uniform4fv(location, buffer);
        }
    }
    
    public static void glUniform4iv(final boolean arbShaders, final int location, final IntBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniform4ivARB(location, buffer);
        }
        else {
            Laby.gfx().uniform4iv(location, buffer);
        }
    }
    
    public static void glUniformMatrix2fv(final boolean arbShaders, final int location, final boolean transpose, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix2fvARB(location, transpose, buffer);
        }
        else {
            Laby.gfx().uniformMatrix2fv(location, transpose, buffer);
        }
    }
    
    public static void glUniformMatrix3fv(final boolean arbShaders, final int location, final boolean transpose, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix3fvARB(location, transpose, buffer);
        }
        else {
            Laby.gfx().uniformMatrix3fv(location, transpose, buffer);
        }
    }
    
    public static void glUniformMatrix4fv(final boolean arbShaders, final int location, final boolean transpose, final FloatBuffer buffer) {
        if (arbShaders) {
            ARBShaderObjects.glUniformMatrix4fvARB(location, transpose, buffer);
        }
        else {
            Laby.gfx().uniformMatrix4fv(location, transpose, buffer);
        }
    }
}
