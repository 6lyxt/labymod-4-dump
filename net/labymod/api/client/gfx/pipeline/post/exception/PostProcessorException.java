// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post.exception;

public class PostProcessorException extends RuntimeException
{
    public PostProcessorException() {
    }
    
    public PostProcessorException(final String message) {
        super(message);
    }
    
    public PostProcessorException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PostProcessorException(final Throwable cause) {
        super(cause);
    }
}
