// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.session.model;

public enum MojangTextureState
{
    ACTIVE("ACTIVE"), 
    INACTIVE("INACTIVE");
    
    private static final MojangTextureState[] VALUES;
    private final String id;
    
    private MojangTextureState(final String id) {
        this.id = id;
    }
    
    public static MojangTextureState of(final String id) {
        for (final MojangTextureState state : MojangTextureState.VALUES) {
            if (state.id.equalsIgnoreCase(id)) {
                return state;
            }
        }
        return null;
    }
    
    public String getId() {
        return this.id;
    }
    
    static {
        VALUES = values();
    }
}
