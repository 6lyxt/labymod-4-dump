// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL30;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.labymod.api.util.logging.Logging;

public final class VertexArrayObjectTracker
{
    private static final Logging LOGGER;
    private static final int UNSET = -1;
    private static int defaultVao;
    private static int currentVao;
    private static final IntSet VERTEX_ARRAY_OBJECTS;
    
    public static int getCurrentVao() {
        return VertexArrayObjectTracker.currentVao;
    }
    
    public static boolean isVertexArray(final int array) {
        return VertexArrayObjectTracker.VERTEX_ARRAY_OBJECTS.contains(array);
    }
    
    public static void glBindVertexArray(final int array) {
        if (VertexArrayObjectTracker.defaultVao == -1) {
            VertexArrayObjectTracker.defaultVao = GL30.glGetInteger(34229);
            VertexArrayObjectTracker.currentVao = VertexArrayObjectTracker.defaultVao;
            VertexArrayObjectTracker.LOGGER.info("Default VAO: {}", VertexArrayObjectTracker.defaultVao);
        }
        if (VertexArrayObjectTracker.currentVao == array) {
            return;
        }
        GL30.glBindVertexArray(array);
        VertexArrayObjectTracker.currentVao = array;
    }
    
    public static int glGenVertexArrays() {
        final int vao = GL30.glGenVertexArrays();
        addVertexArrayObject(vao);
        return vao;
    }
    
    public static void glGenVertexArrays(final int[] arrays) {
        GL30.glGenVertexArrays(arrays);
        for (final int vaoHandle : arrays) {
            addVertexArrayObject(vaoHandle);
        }
    }
    
    public static void glGenVertexArrays(final IntBuffer arrays) {
        GL30.glGenVertexArrays(arrays);
        for (int capacity = arrays.capacity(), index = 0; index < capacity; ++index) {
            final int vaoHandle = arrays.get(index);
            addVertexArrayObject(vaoHandle);
        }
    }
    
    public static void glDeleteVertexArrays(final int array) {
        removeVertexArrayObject(array);
        GL30.glDeleteVertexArrays(array);
    }
    
    public static void glDeleteVertexArrays(final int[] arrays) {
        for (final int vaoHandle : arrays) {
            removeVertexArrayObject(vaoHandle);
        }
        GL30.glDeleteVertexArrays(arrays);
    }
    
    public static void glDeleteVertexArrays(final IntBuffer arrays) {
        for (int capacity = arrays.capacity(), index = 0; index < capacity; ++index) {
            final int vaoHandle = arrays.get(index);
            removeVertexArrayObject(vaoHandle);
        }
        GL30.glDeleteVertexArrays(arrays);
    }
    
    private static void addVertexArrayObject(final int handle) {
        if (VertexArrayObjectTracker.VERTEX_ARRAY_OBJECTS.contains(handle)) {
            throw new IllegalStateException("The handle is already contained in the list (" + handle);
        }
        VertexArrayObjectTracker.VERTEX_ARRAY_OBJECTS.add(handle);
    }
    
    private static void removeVertexArrayObject(final int handle) {
        if (VertexArrayObjectTracker.currentVao == handle) {
            glBindVertexArray(0);
        }
        VertexArrayObjectTracker.VERTEX_ARRAY_OBJECTS.remove(handle);
    }
    
    static {
        LOGGER = Logging.create("GL30");
        VertexArrayObjectTracker.defaultVao = -1;
        VertexArrayObjectTracker.currentVao = -1;
        VERTEX_ARRAY_OBJECTS = (IntSet)new IntOpenHashSet();
    }
}
