// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.color;

import net.labymod.api.client.gfx.GFXObject;

@Deprecated(forRemoval = true, since = "4.2.41")
public enum GFXAlphaFunction implements GFXObject
{
    NEVER(512), 
    LESS(513), 
    EQUAL(514), 
    LEQUAL(515), 
    GREATER(516), 
    NOTEQUAL(517), 
    GEQUAL(518), 
    ALWAYS(519);
    
    private final int id;
    
    private GFXAlphaFunction(final int id) {
        this.id = id;
    }
    
    @Override
    public int getHandle() {
        return this.id;
    }
}
