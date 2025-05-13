// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer;

import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;

public interface BufferBuilder extends BufferConsumer
{
    default void begin(final RenderProgram program) {
        Objects.requireNonNull(program);
        this.begin(program, program::getName);
    }
    
    void begin(final RenderProgram p0, final Supplier<String> p1);
    
    @Nullable
    BufferState end();
    
    boolean building();
    
    int getVertices();
    
    void close();
}
