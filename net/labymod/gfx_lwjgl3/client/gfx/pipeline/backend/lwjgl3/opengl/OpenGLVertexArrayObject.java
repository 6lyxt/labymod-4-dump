// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import net.labymod.api.Laby;
import java.util.function.IntConsumer;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.jetbrains.annotations.Nullable;
import java.util.function.IntSupplier;

public final class OpenGLVertexArrayObject
{
    public static int generate() {
        return generate(null);
    }
    
    public static int generate(@Nullable final IntSupplier otherMethod) {
        if (otherMethod != null) {
            return otherMethod.getAsInt();
        }
        if (isArbVertexArrayObjectSupported()) {
            return ARBVertexArrayObject.glGenVertexArrays();
        }
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glGenVertexArrays function is not available in the current context.");
            return -1;
        }
        return GL30.glGenVertexArrays();
    }
    
    public static void bind(final int id) {
        bind(id, null);
    }
    
    public static void bind(final int id, @Nullable final IntConsumer otherMethod) {
        if (otherMethod != null) {
            otherMethod.accept(id);
            return;
        }
        if (isArbVertexArrayObjectSupported()) {
            ARBVertexArrayObject.glBindVertexArray(id);
            return;
        }
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glBindVertexArray function is not available in the current context.");
            return;
        }
        GL30.glBindVertexArray(id);
    }
    
    public static void delete(final int id) {
        delete(id, null);
    }
    
    public static void delete(final int id, @Nullable final IntConsumer otherMethod) {
        if (otherMethod != null) {
            otherMethod.accept(id);
            return;
        }
        if (isArbVertexArrayObjectSupported()) {
            ARBVertexArrayObject.glDeleteVertexArrays(id);
            return;
        }
        if (!OpenGLPreconditions.isGL30Supported()) {
            OpenGLPreconditions.assertValidCode(() -> "The glDeleteVertexArrays function is not available in the current context.");
            return;
        }
        GL30.glDeleteVertexArrays(id);
    }
    
    private static boolean isArbVertexArrayObjectSupported() {
        return Laby.gfx().capabilities().isArbVertexArrayObjectSupported();
    }
}
