// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.opengl;

import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.Laby;
import net.labymod.api.models.version.Version;

public enum NamedOpenGLVersion implements Version
{
    GL11((Version)new SemanticVersion(1, 1)), 
    GL12((Version)new SemanticVersion(1, 2)), 
    GL13((Version)new SemanticVersion(1, 3)), 
    GL14((Version)new SemanticVersion(1, 4)), 
    GL15((Version)new SemanticVersion(1, 5)), 
    GL20((Version)new SemanticVersion(2, 0)), 
    GL21((Version)new SemanticVersion(2, 1)), 
    GL30((Version)new SemanticVersion(3, 0)), 
    GL31((Version)new SemanticVersion(3, 1)), 
    GL32((Version)new SemanticVersion(3, 2)), 
    GL33((Version)new SemanticVersion(3, 3)), 
    GL40((Version)new SemanticVersion(4, 0)), 
    GL41((Version)new SemanticVersion(4, 1)), 
    GL42((Version)new SemanticVersion(4, 2)), 
    GL43((Version)new SemanticVersion(4, 3)), 
    GL44((Version)new SemanticVersion(4, 4)), 
    GL45((Version)new SemanticVersion(4, 5)), 
    GL46((Version)new SemanticVersion(4, 6));
    
    private final Version version;
    private final boolean supported;
    
    private NamedOpenGLVersion(final Version version) {
        this.version = version;
        this.supported = Laby.gfx().capabilities().isSupported(version);
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
    public Class<?> getFormat() {
        return this.version.getFormat();
    }
    
    @Override
    public boolean compareExtension(final Version version) {
        return this.version.compareExtension(version);
    }
    
    @Override
    public boolean compareFormat(final Version version) {
        return this.version.compareFormat(version);
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
}
