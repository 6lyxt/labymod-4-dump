// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.gl;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Display;
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
    public Monitor[] getMonitors() throws Exception {
        final DisplayMode[] modes = Display.getAvailableDisplayModes();
        final Monitor[] monitors = new Monitor[modes.length];
        for (int i = 0; i < modes.length; ++i) {
            final DisplayMode mode = modes[i];
            monitors[i] = new Monitor(mode.getWidth(), mode.getHeight(), mode.getFrequency());
        }
        return monitors;
    }
}
