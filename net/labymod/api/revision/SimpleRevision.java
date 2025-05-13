// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.revision;

import net.labymod.api.util.version.SemanticVersion;
import java.text.SimpleDateFormat;

public class SimpleRevision implements Revision
{
    private static final SimpleDateFormat DATE_FORMAT;
    private final String namespace;
    private final SemanticVersion version;
    private long releaseDate;
    
    public SimpleRevision(final String namespace, final SemanticVersion version, final String releaseDate) {
        this.releaseDate = 0L;
        this.namespace = namespace;
        this.version = version;
        try {
            this.releaseDate = SimpleRevision.DATE_FORMAT.parse(releaseDate).getTime();
        }
        catch (final Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getNamespace() {
        return this.namespace;
    }
    
    @Override
    public SemanticVersion version() {
        return this.version;
    }
    
    @Override
    public long getReleaseDate() {
        return this.releaseDate;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    }
}
