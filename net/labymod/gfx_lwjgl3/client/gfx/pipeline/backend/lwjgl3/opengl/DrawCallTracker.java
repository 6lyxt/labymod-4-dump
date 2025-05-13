// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import org.lwjgl.opengl.GL11;
import net.labymod.api.util.logging.Logging;

public final class DrawCallTracker
{
    private static final Logging LOGGER;
    
    public static void glDrawElements(final int mode, final int count, final int type, final long pointer) {
        final int ebo = GL11.glGetInteger(34965);
        if (ebo == 0 && isInvalidPointer(pointer)) {
            DrawCallTracker.LOGGER.warn("A native crash could be avoided. Vertex array object {} has no EBO/IBO bound.", VertexArrayObjectTracker.getCurrentVao());
            return;
        }
        GL11.glDrawElements(mode, count, type, pointer);
    }
    
    private static boolean isInvalidPointer(final long pointer) {
        return pointer >= 0L && pointer < 1024L;
    }
    
    static {
        LOGGER = Logging.create(DrawCallTracker.class);
    }
}
