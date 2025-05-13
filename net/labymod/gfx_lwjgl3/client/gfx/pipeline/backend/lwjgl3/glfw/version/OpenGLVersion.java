// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version;

public class OpenGLVersion
{
    private final int major;
    private final int minor;
    
    protected OpenGLVersion(final int major, final int minor) {
        this.major = major;
        this.minor = minor;
    }
    
    public static OpenGLVersion of(final int major, final int minor) {
        return new OpenGLVersion(major, minor);
    }
    
    public static OpenGLVersion error(final String errorMessage) {
        return new InvalidOpenGLVersion(errorMessage);
    }
    
    public int getMajor() {
        return this.major;
    }
    
    public int getMinor() {
        return this.minor;
    }
    
    @Override
    public String toString() {
        return this.major + "." + this.minor;
    }
}
