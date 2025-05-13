// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.color.blend;

public enum BlendParameter
{
    ZERO(0), 
    ONE(1), 
    SOURCE_COLOR(768), 
    ONE_MINUS_SOURCE_COLOR(769), 
    SOURCE_ALPHA(770), 
    ONE_MINUS_SOURCE_ALPHA(771), 
    DESTINATION_ALPHA(772), 
    ONE_MINUS_DESTINATION_ALPHA(773), 
    DESTINATION_COLOR(774), 
    ONE_MINUS_DESTINATION_COLOR(775), 
    SOURCE_ALPHA_SATURATE(776);
    
    private final int id;
    
    private BlendParameter(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
