// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

public class LootBoxException extends RuntimeException
{
    public LootBoxException() {
    }
    
    public LootBoxException(final String message) {
        super(message);
    }
    
    public LootBoxException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public LootBoxException(final Throwable cause) {
        super(cause);
    }
    
    public LootBoxException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
