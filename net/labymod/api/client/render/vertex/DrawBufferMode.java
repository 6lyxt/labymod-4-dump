// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

public enum DrawBufferMode
{
    NONE(0), 
    FRONT_LEFT(1024), 
    FRONT_RIGHT(1025), 
    BACK_LEFT(1026), 
    BACK_RIGHT(1027), 
    FRONT(1028), 
    BACK(1029), 
    LEFT(1030), 
    RIGHT(1031), 
    FRONT_AND_BACK(1032), 
    AUX0(1033), 
    AUX1(1034), 
    AUX2(1035), 
    AUX3(1036);
    
    private final int type;
    
    private DrawBufferMode(final int type) {
        this.type = type;
    }
    
    public int type() {
        return this.type;
    }
}
