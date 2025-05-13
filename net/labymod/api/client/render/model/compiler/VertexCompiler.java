// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.compiler;

import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;

public interface VertexCompiler
{
    public static final VertexCompiler DEFAULT = BufferConsumer::vertex;
    
    void compile(final BufferConsumer p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final int p10, final int p11, final float p12, final float p13, final float p14);
}
