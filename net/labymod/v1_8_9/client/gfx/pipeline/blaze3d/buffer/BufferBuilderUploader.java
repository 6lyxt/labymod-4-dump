// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline.blaze3d.buffer;

import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.v1_8_9.client.renderer.WorldRendererAccessor;

public final class BufferBuilderUploader
{
    private static final bfe UPLOADER;
    
    public static void upload(final bfd renderer) {
        if (!((WorldRendererAccessor)renderer).building()) {
            return;
        }
        renderer.e();
        BufferBuilderUploader.UPLOADER.a(renderer);
        RenderProfiler.increaseRenderCall();
    }
    
    public static void upload(final WrappedBufferBuilder bufferBuilder) {
        upload(bufferBuilder.getWorldRenderer());
    }
    
    static {
        UPLOADER = new bfe();
    }
}
