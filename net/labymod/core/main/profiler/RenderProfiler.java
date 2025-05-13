// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.profiler;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Iterator;
import net.labymod.core.main.profiler.memory.BufferMemory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public final class RenderProfiler
{
    private static final Int2ObjectMap<BufferMemory> BUFFER_MEMORY;
    
    public static void increaseRenderCall() {
        Counters.RENDER_CALL.update();
    }
    
    public static void setBufferMemory(final int bufferId, final int type, final long size) {
        final Int2ObjectMap<BufferMemory> buffers = RenderProfiler.BUFFER_MEMORY;
        final BufferMemory bufferMemory = (BufferMemory)buffers.get(bufferId);
        if (bufferMemory != null) {
            bufferMemory.setType(type);
            bufferMemory.setSize(size);
            return;
        }
        final BufferMemory memory = new BufferMemory(bufferId);
        memory.setType(type);
        memory.setSize(size);
        buffers.put(bufferId, (Object)memory);
    }
    
    public static void deleteBuffer(final int bufferId) {
        RenderProfiler.BUFFER_MEMORY.remove(bufferId);
    }
    
    public static Int2ObjectMap<BufferMemory> getBufferMemory() {
        return RenderProfiler.BUFFER_MEMORY;
    }
    
    public static int getRenderCalls() {
        return Counters.RENDER_CALL.getCount();
    }
    
    public static void increaseRenderedCosmeticCount() {
        Counters.RENDERED_COSMETICS.update();
    }
    
    public static void increaseCosmeticTextureBindingCalls() {
        Counters.COSMETIC_TEXTURE_BINDINGS.update();
    }
    
    public static void resetRenderCalls() {
        for (final Counter counter : Counters.getCounters()) {
            counter.reset();
        }
    }
    
    static {
        BUFFER_MEMORY = (Int2ObjectMap)new Int2ObjectOpenHashMap();
    }
}
