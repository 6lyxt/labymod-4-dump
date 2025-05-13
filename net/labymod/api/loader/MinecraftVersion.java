// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.loader;

import java.util.Objects;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionComparison;

public final class MinecraftVersion implements VersionComparison<MinecraftVersion>
{
    private final Version version;
    private final long releaseTime;
    private final long time;
    private final long complianceLevel;
    private boolean current;
    private boolean older;
    private boolean newer;
    
    public MinecraftVersion(final Version version, final long releaseTime, final long time, final long complianceLevel) {
        this.version = version;
        this.releaseTime = releaseTime;
        this.time = time;
        this.complianceLevel = complianceLevel;
    }
    
    public void initialize(final MinecraftVersion current) {
        final long thisReleaseTime = this.getReleaseTime();
        final long currentReleaseTime = current.getReleaseTime();
        this.current = (thisReleaseTime == currentReleaseTime);
        this.older = (currentReleaseTime <= thisReleaseTime);
        this.newer = (currentReleaseTime >= thisReleaseTime);
    }
    
    public boolean orNewer() {
        return this.newer;
    }
    
    public boolean orOlder() {
        return this.older;
    }
    
    public boolean isCurrent() {
        return this.current;
    }
    
    public static MinecraftVersion of(final String version, final long releaseTime, final long time, final long complianceLevel) {
        return new MinecraftVersion(VersionDeserializer.from(version), releaseTime, time, complianceLevel);
    }
    
    public Version version() {
        return this.version;
    }
    
    public long getReleaseTime() {
        return this.releaseTime;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public long getComplianceLevel() {
        return this.complianceLevel;
    }
    
    @Override
    public boolean isCompatible(final MinecraftVersion other) {
        return this.releaseTime == other.releaseTime;
    }
    
    @Override
    public boolean isGreaterThan(final MinecraftVersion other) {
        return this.releaseTime > other.releaseTime;
    }
    
    @Override
    public boolean isLowerThan(final MinecraftVersion other) {
        return this.releaseTime < other.releaseTime;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final MinecraftVersion that = (MinecraftVersion)obj;
        return Objects.equals(this.version, that.version) && Objects.equals(this.releaseTime, that.releaseTime) && Objects.equals(this.time, that.time) && this.complianceLevel == that.complianceLevel;
    }
    
    @Override
    public int hashCode() {
        int result = (this.version != null) ? this.version.hashCode() : 0;
        result = 31 * result + (int)(this.releaseTime ^ this.releaseTime >>> 32);
        result = 31 * result + (int)(this.time ^ this.time >>> 32);
        result = 31 * result + (int)(this.complianceLevel ^ this.complianceLevel >>> 32);
        return result;
    }
    
    @Override
    public String toString() {
        return "MinecraftVersion[version=" + String.valueOf(this.version) + ", releaseTime=" + this.releaseTime + ", time=" + this.time + ", complianceLevel=" + this.complianceLevel;
    }
}
