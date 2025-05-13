// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import org.lwjgl.opengl.GL30;
import net.labymod.api.client.gfx.buffer.BufferTarget;

public final class OpenGLBufferBinder
{
    public static void bindBufferBase(final BufferTarget target, final int index, final int buffer) {
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glBindBufferBase function is not available in the current context.");
            return;
        }
        GL30.glBindBufferBase(target.getId(), index, buffer);
    }
    
    public static void bindBufferRange(final BufferTarget target, final int index, final int buffer, final long offset, final long size) {
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glBindBufferRange function is not available in the current context.");
            return;
        }
        GL30.glBindBufferRange(target.getId(), index, buffer, offset, size);
    }
}
