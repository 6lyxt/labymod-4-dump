// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.exception;

public class EmoteException extends RuntimeException
{
    private final int emoteId;
    private final String reason;
    
    public EmoteException(final int emoteId, final String reason) {
        this.emoteId = emoteId;
        this.reason = reason;
    }
    
    public EmoteException(final int emoteId, final String message, final String reason) {
        super(message);
        this.emoteId = emoteId;
        this.reason = reason;
    }
    
    public EmoteException(final int emoteId, final String message, final String reason, final Throwable cause) {
        super(message, cause);
        this.emoteId = emoteId;
        this.reason = reason;
    }
    
    public EmoteException(final int emoteId, final String reason, final Throwable cause) {
        super(cause);
        this.emoteId = emoteId;
        this.reason = reason;
    }
    
    public int getEmoteId() {
        return this.emoteId;
    }
    
    public String getReason() {
        return this.reason;
    }
}
