// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version;

import java.util.Locale;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFW;
import net.labymod.api.models.OperatingSystem;

public class OpenGLVersionRequesterThread extends Thread
{
    private static final String NAME = "OpenGL Version Requester";
    private OpenGLVersion version;
    
    public OpenGLVersionRequesterThread() {
        super("OpenGL Version Requester");
        this.setUncaughtExceptionHandler((t, e) -> {
            if (e != null) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public void run() {
        if (OperatingSystem.isOSX()) {
            this.version = OpenGLVersion.error("unsupported_platform");
            return;
        }
        if (!GLFW.glfwInit()) {
            this.version = OpenGLVersion.error("glfw_error");
            return;
        }
        final GLFWErrorCallback errorCallback = GLFW.glfwSetErrorCallback(this::handleErrorCallback);
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(131076, 0);
        final long handle = GLFW.glfwCreateWindow(1, 1, (CharSequence)"OpenGL Version Requester", 0L, 0L);
        if (handle == 0L) {
            this.version = OpenGLVersion.error("glfw_window_error");
            return;
        }
        final long currentContext = GLFW.glfwGetCurrentContext();
        GLFW.glfwMakeContextCurrent(handle);
        GL.createCapabilities();
        final String glVersion = GL11.glGetString(7938);
        if (glVersion == null) {
            this.version = OpenGLVersion.error("opengl_version_error");
            return;
        }
        final APIUtil.APIVersion version = APIUtil.apiParseVersion(glVersion);
        this.version = OpenGLVersion.of(version.major, version.minor);
        GLFW.glfwDestroyWindow(handle);
        if (errorCallback != null) {
            errorCallback.free();
        }
        if (currentContext != 0L) {
            GLFW.glfwMakeContextCurrent(currentContext);
        }
    }
    
    public OpenGLVersion getVersion() {
        return this.version;
    }
    
    private void handleErrorCallback(final int error, final long description) {
        OpenGLVersionRequester.LOGGER.info(String.format(Locale.ROOT, "GLFW error during init: 0x%X", error), new Object[0]);
    }
}
