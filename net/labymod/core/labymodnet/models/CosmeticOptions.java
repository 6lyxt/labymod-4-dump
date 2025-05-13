// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import java.util.HashMap;
import java.util.Map;

public class CosmeticOptions
{
    private final Map<String, CosmeticOption> options;
    
    public CosmeticOptions() {
        this.options = new HashMap<String, CosmeticOption>();
    }
    
    public Map<String, CosmeticOption> getMap() {
        return this.options;
    }
    
    public CosmeticOption getOptions(final String id) {
        return this.options.get(id);
    }
}
