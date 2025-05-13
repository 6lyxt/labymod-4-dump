// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

import net.labymod.api.models.version.Version;

public final class GFXVersion implements Version
{
    private final String name;
    private final Version version;
    private final boolean supported;
    
    public GFXVersion(final Version version, final boolean supported) {
        this.version = version;
        this.supported = supported;
        final int patch = version.getPatch();
        this.name = "OpenGL" + version.getMajor() + version.getMinor() + String.valueOf((patch == 0) ? "" : Integer.valueOf(patch));
    }
    
    public boolean isSupported() {
        return this.supported;
    }
    
    @Override
    public int getMajor() {
        return this.version.getMajor();
    }
    
    @Override
    public int getMinor() {
        return this.version.getMinor();
    }
    
    @Override
    public int getPatch() {
        return this.version.getPatch();
    }
    
    @Override
    public String getExtension() {
        return this.version.getExtension();
    }
    
    @Override
    public boolean isCompatible(final Version version) {
        return this.version.isCompatible(version);
    }
    
    @Override
    public boolean isGreaterThan(final Version version) {
        return this.version.isGreaterThan(version);
    }
    
    @Override
    public boolean isLowerThan(final Version version) {
        return this.version.isLowerThan(version);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
