// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint.marketplace;

public class FlintPermission
{
    private final int id;
    private final String key;
    private final boolean critical;
    
    public FlintPermission(final String key) {
        this.key = key;
        this.id = -1;
        this.critical = true;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public boolean isCritical() {
        return this.critical;
    }
}
