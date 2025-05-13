// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.version;

public class InvalidOpenGLVersion extends OpenGLVersion
{
    private final String errorMessage;
    
    protected InvalidOpenGLVersion(final String errorMessage) {
        super(-1, -1);
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        return this.errorMessage;
    }
}
