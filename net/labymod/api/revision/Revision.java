// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.revision;

import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.version.SemanticVersion;

public interface Revision
{
    String getNamespace();
    
    SemanticVersion version();
    
    long getReleaseDate();
    
    default long getRelevanceDuration() {
        return 1036800000L;
    }
    
    default boolean isRelevant() {
        return TimeUtil.getCurrentTimeMillis() - this.getReleaseDate() < this.getRelevanceDuration();
    }
    
    default String getDisplayName() {
        final String niceNamespace = Laby.labyAPI().getNiceNamespace(this.getNamespace());
        return "%s v%s".formatted(niceNamespace, this.version().toString());
    }
}
