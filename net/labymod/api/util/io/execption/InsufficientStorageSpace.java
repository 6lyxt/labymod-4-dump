// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.execption;

import java.io.IOException;

public class InsufficientStorageSpace extends IOException
{
    private final long availableSpace;
    private final long requiredSpace;
    
    public InsufficientStorageSpace(final long availableSpace, final long requiredSpace) {
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }
    
    public InsufficientStorageSpace(final String message, final long availableSpace, final long requiredSpace) {
        super(message);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }
    
    public InsufficientStorageSpace(final String message, final Throwable cause, final long availableSpace, final long requiredSpace) {
        super(message, cause);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }
    
    public InsufficientStorageSpace(final Throwable cause, final long availableSpace, final long requiredSpace) {
        super(cause);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }
    
    public long getAvailableSpace() {
        return this.availableSpace;
    }
    
    public long getRequiredSpace() {
        return this.requiredSpace;
    }
}
