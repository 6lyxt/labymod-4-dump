// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol;

public enum LabyConnectState
{
    HELLO(-1), 
    LOGIN(0), 
    PLAY(1), 
    ALL(2), 
    OFFLINE(3);
    
    private final int id;
    
    private LabyConnectState(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
