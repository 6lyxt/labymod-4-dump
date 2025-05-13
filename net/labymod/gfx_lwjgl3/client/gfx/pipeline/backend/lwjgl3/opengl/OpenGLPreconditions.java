// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl;

import net.labymod.api.Laby;
import java.util.function.Supplier;
import net.labymod.api.client.gfx.opengl.NamedOpenGLVersion;

public final class OpenGLPreconditions
{
    private OpenGLPreconditions() {
    }
    
    public static boolean isGL30Supported() {
        return NamedOpenGLVersion.GL30.isSupported();
    }
    
    public static boolean isGL31Supported() {
        return NamedOpenGLVersion.GL31.isSupported();
    }
    
    public static void assertValidCode(final Supplier<String> messageSupplier) {
        if (!Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment()) {
            return;
        }
        throw new IllegalStateException(messageSupplier.get());
    }
}
