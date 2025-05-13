// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.index;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface IndexGenerator
{
    void accept(final IntConsumer p0, final int p1);
}
