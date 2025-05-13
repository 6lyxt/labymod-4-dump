// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.render.gl;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.gl.GlInformation;

@Singleton
@Implements(GlInformation.class)
public class VersionedGlInformation implements GlInformation
{
    @Override
    public String getGlVersion() {
        return GL11.glGetString(7938);
    }
    
    @Override
    public String getGlVendor() {
        return GL11.glGetString(7936);
    }
    
    @Override
    public String getGlRenderer() {
        return GL11.glGetString(7937);
    }
    
    @Override
    public Monitor[] getMonitors() {
        final PointerBuffer glfwMonitors = GLFW.glfwGetMonitors();
        if (glfwMonitors == null) {
            return new Monitor[0];
        }
        final Monitor[] monitors = new Monitor[glfwMonitors.limit()];
        for (int i = 0; i < glfwMonitors.limit(); ++i) {
            final long monitor = glfwMonitors.get(i);
            final GLFWVidMode mode = GLFW.glfwGetVideoMode(monitor);
            if (mode == null) {
                return new Monitor[0];
            }
            monitors[i] = new Monitor(mode.width(), mode.height(), mode.refreshRate());
        }
        return monitors;
    }
}
