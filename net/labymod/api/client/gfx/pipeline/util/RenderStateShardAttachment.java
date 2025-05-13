// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

public interface RenderStateShardAttachment
{
    default RenderStateShardAttachment self(final Object obj) {
        if (!(obj instanceof RenderStateShardAttachment)) {
            return null;
        }
        return (RenderStateShardAttachment)obj;
    }
    
    default void addAttachment(final Object obj, final Runnable renderer) {
        final RenderStateShardAttachment self = self(obj);
        if (self == null) {
            renderer.run();
        }
        else {
            self.addAttachment(renderer);
        }
    }
    
    void addAttachment(final Runnable p0);
}
