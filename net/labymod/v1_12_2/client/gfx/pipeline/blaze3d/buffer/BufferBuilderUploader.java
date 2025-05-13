// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline.blaze3d.buffer;

import net.labymod.v1_12_2.client.renderer.WorldRendererAccessor;

public final class BufferBuilderUploader
{
    private static final bul UPLOADER;
    
    public static void upload(final buk renderer) {
        if (!((WorldRendererAccessor)renderer).building()) {
            return;
        }
        renderer.e();
        BufferBuilderUploader.UPLOADER.a(renderer);
    }
    
    public static void upload(final WrappedBufferBuilder bufferBuilder) {
        upload(bufferBuilder.getBufferBuilder());
    }
    
    static {
        UPLOADER = new bul();
    }
}
