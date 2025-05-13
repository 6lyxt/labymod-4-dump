// 
// Decompiled by Procyon v0.6.0
// 

package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;

public class DisplayFullscreenWindowController implements FullscreenWindowController
{
    @Override
    public boolean isWindowFullscreen() {
        return Display.isFullscreen();
    }
    
    @Override
    public void setWindowFullscreen(final boolean value) {
        try {
            Display.setFullscreen(value);
        }
        catch (final LWJGLException ex) {}
    }
}
