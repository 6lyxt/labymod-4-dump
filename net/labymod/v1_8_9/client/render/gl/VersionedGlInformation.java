// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.render.gl;

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
        final Monitor[] monitors = new Monitor[0];
        return monitors;
    }
}
