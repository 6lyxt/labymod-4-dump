// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

public class ChangeResponse
{
    private final int done;
    
    public ChangeResponse(final int done) {
        this.done = done;
    }
    
    public int getDone() {
        return this.done;
    }
    
    public boolean isDone() {
        return this.done == 1;
    }
}
