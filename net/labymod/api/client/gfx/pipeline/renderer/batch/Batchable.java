// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.batch;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.pipeline.renderer.Renderer;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;

public interface Batchable
{
    void begin(final RenderProgram p0);
    
    void changeRenderProgram(final RenderProgram p0);
    
    @NotNull
     <T extends Renderer> T getRenderer(final Class<T> p0);
    
    default <T extends Renderer> void useRenderer(final Class<T> rendererClass, final Consumer<T> rendererConsumer) {
        if (rendererConsumer == null) {
            return;
        }
        rendererConsumer.accept(this.getRenderer(rendererClass));
    }
    
    void flush();
    
    void flush(final FloatMatrix4 p0);
    
    default void flush(final Stack stack) {
        this.flush(stack.getProvider().getPosition());
    }
    
    void end();
    
    void end(final FloatMatrix4 p0);
    
    default void end(final Stack stack) {
        this.end(stack.getProvider().getPosition());
    }
    
    float getZOffset();
    
    void setZOffset(final float p0);
    
    void close();
}
