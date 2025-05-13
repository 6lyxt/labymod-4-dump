// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import net.labymod.api.client.gfx.pipeline.backend.natives.GLFWNatives;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.window.WindowShowEvent;
import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.core.client.os.windows.window.WindowManagement;
import org.lwjgl.glfw.GLFW;

public final class GLFWRedirector
{
    private GLFWRedirector() {
    }
    
    public static long nglfwCreateWindow(final int width, final int height, final long title, final long monitor, final long share) {
        final long window = GLFW.nglfwCreateWindow(width, height, title, monitor, share);
        if (window == 0L) {
            return window;
        }
        final int visible = GLFW.glfwGetWindowAttrib(window, 131076);
        if (visible == 1) {
            dispatchWindowShowEvent(window);
        }
        WindowManagement.update(window);
        glfwNatives().getWindows().add(window);
        return window;
    }
    
    public static void glfwDestroyWindow(final long window) {
        GLFW.glfwDestroyWindow(window);
        final LongListIterator iterator = glfwNatives().getWindows().iterator();
        while (iterator.hasNext()) {
            final long windowHandle = iterator.nextLong();
            if (window == windowHandle) {
                iterator.remove();
            }
        }
    }
    
    public static void glfwShowWindow(final long window) {
        GLFW.glfwShowWindow(window);
        dispatchWindowShowEvent(window);
    }
    
    private static void dispatchWindowShowEvent(final long window) {
        Laby.fireEvent(new WindowShowEvent(window));
    }
    
    private static GLFWNatives glfwNatives() {
        return Laby.gfx().backend().glfwNatives();
    }
}
