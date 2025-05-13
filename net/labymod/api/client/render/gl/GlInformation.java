// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.gl;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GlInformation
{
    String getGlVersion();
    
    String getGlVendor();
    
    String getGlRenderer();
    
    Monitor[] getMonitors() throws Exception;
    
    public static class Monitor
    {
        private final int width;
        private final int height;
        private final int refreshRate;
        
        public Monitor(final int width, final int height, final int refreshRate) {
            this.width = width;
            this.height = height;
            this.refreshRate = refreshRate;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public int getRefreshRate() {
            return this.refreshRate;
        }
    }
}
