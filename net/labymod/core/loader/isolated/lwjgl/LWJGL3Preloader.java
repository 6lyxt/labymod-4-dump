// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.lwjgl;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.net.URL;
import java.util.Iterator;
import net.labymod.api.loader.platform.PlatformClassloader;
import java.nio.file.Path;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.isolated.IsolatedLibraryFinishHandler;

public class LWJGL3Preloader implements IsolatedLibraryFinishHandler
{
    private static final Logging LOGGER;
    private static final String GROUP = "org.lwjgl";
    private static final String[] CLASSES;
    
    @Override
    public void onAccept(final IsolatedLibrary library) {
    }
    
    @Override
    public void onFinish() {
        final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();
        for (final Path file : IsolatedClassLoaders.LWJGL_CLASS_LOADER.getFiles()) {
            platformClassloader.addPath(file);
        }
        final ClassLoader classLoader = platformClassloader.getPlatformClassloader();
        final String[] classes = LWJGL3Preloader.CLASSES;
        for (int length = classes.length, i = 0; i < length; ++i) {
            final String cls = classes[i];
            final String finalClass = "org.lwjgl" + cls;
            try {
                classLoader.loadClass(finalClass);
                LWJGL3Preloader.LOGGER.info("Preloaded " + finalClass, new Object[0]);
            }
            catch (final ClassNotFoundException exception) {
                LWJGL3Preloader.LOGGER.error(this.dumpClassPath(platformClassloader), new Object[0]);
                throw new RuntimeException("Failed to preload " + finalClass);
            }
        }
    }
    
    private String dumpClassPath(final PlatformClassloader platformClassloader) {
        final StringBuilder builder = new StringBuilder();
        builder.append("ClassPath:").append(System.lineSeparator());
        for (final URL url : platformClassloader.getClasspath()) {
            try {
                final Path path = Paths.get(url.toURI());
                builder.append("\t").append("- ");
                if (Files.isDirectory(path, new LinkOption[0])) {
                    builder.append(path).append("[DIRECTORY]");
                }
                else {
                    final long bytes = this.getFileSize(path);
                    builder.append(path).append("(File Size: ").append(this.convertBytes(bytes));
                    if (IOUtil.isCorrupted(path)) {
                        builder.append(" , File is corrupted");
                    }
                    builder.append(")");
                }
            }
            catch (final Exception exception) {
                builder.append("\t").append("- ").append(url);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
    
    private String convertBytes(final long bytes) {
        if (bytes <= 1024L) {
            return "" + bytes;
        }
        if (bytes <= 1048576L) {
            return bytes / 1024L + "KB";
        }
        return bytes / 1048576L + "MB";
    }
    
    private long getFileSize(final Path file) {
        try {
            return Files.size(file);
        }
        catch (final Throwable exception) {
            return -1L;
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("LWJGL3 Preloader");
        CLASSES = new String[] { ".opengl.GL", ".system.MemoryStack", ".system.macosx.MacOSXLibraryBundle", ".glfw.GLFWCursorPosCallback", ".glfw.GLFWKeyCallback", ".glfw.GLFWCharModsCallback", ".glfw.GLFWNativeNSGL", ".glfw.GLFWMouseButtonCallback", ".glfw.GLFWNativeWGL", ".glfw.GLFWMonitorCallback", ".glfw.GLFWErrorCallback", ".glfw.GLFWCursorEnterCallback", ".glfw.package-info", ".glfw.GLFWNativeWin32", ".glfw.GLFWWindowRefreshCallback", ".glfw.GLFW", ".glfw.GLFWWindowContentScaleCallback", ".glfw.GLFWVulkan", ".glfw.GLFWNativeGLX", ".glfw.GLFWGammaRamp", ".glfw.GLFWWindowIconifyCallback", ".glfw.GLFWScrollCallback", ".glfw.GLFWNativeEGL", ".glfw.EventLoop", ".glfw.GLFWNativeCocoa", ".glfw.GLFWWindowSizeCallback", ".glfw.GLFWWindowPosCallback", ".glfw.GLFWWindowMaximizeCallback", ".glfw.GLFWFramebufferSizeCallback", ".glfw.GLFWImage", ".glfw.GLFWJoystickCallback", ".glfw.GLFWWindowCloseCallback", ".glfw.GLFWDropCallback", ".glfw.GLFWWindowFocusCallback", ".glfw.GLFWVidMode", ".glfw.Callbacks", ".glfw.GLFWCharCallback", ".glfw.GLFWNativeWayland", ".glfw.GLFWNativeX11", ".glfw.GLFWGamepadState" };
    }
}
