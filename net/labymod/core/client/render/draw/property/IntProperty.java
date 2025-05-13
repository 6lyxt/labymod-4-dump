// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.property;

@Deprecated
public final class IntProperty
{
    private int value;
    private boolean set;
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        this.value = value;
        this.set = true;
    }
    
    public boolean isSet() {
        return this.set;
    }
    
    public void reset() {
        this.value = 0;
        this.set = false;
    }
}
