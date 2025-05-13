// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version;

import java.util.function.Consumer;
import net.labymod.api.util.logging.Logging;

public class OpenGLVersionRequester
{
    static final Logging LOGGER;
    private static final int DEFAULT_MAJOR_VERSION = 3;
    private static final int DEFAULT_MINOR_VERSION = 2;
    private static OpenGLVersion version;
    
    public static void requestOpenGLVersion() {
        final OpenGLVersionRequesterThread thread = new OpenGLVersionRequesterThread();
        thread.start();
        OpenGLVersion version;
        do {
            version = thread.getVersion();
            try {
                Thread.sleep(50L);
            }
            catch (final InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        } while (version == null);
        thread.interrupt();
        if (version instanceof InvalidOpenGLVersion) {
            version = OpenGLVersion.of(3, 2);
        }
        if (version.getMajor() < 3 && version.getMinor() < 2) {
            version = OpenGLVersion.of(3, 2);
        }
        OpenGLVersionRequester.version = version;
        OpenGLVersionRequester.LOGGER.info("OpenGL context will now use " + String.valueOf(OpenGLVersionRequester.version), new Object[0]);
    }
    
    public static void consumeOpenGLVersion(final Consumer<OpenGLVersion> consumer) {
        if (consumer == null) {
            return;
        }
        consumer.accept(OpenGLVersionRequester.version);
    }
    
    static {
        LOGGER = Logging.create(OpenGLVersionRequester.class);
    }
}
