// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.world.chunk;

import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.Event;

public class ChunkEvent implements Event
{
    private final Chunk chunk;
    private final Type type;
    
    private ChunkEvent(final Chunk chunk, final Type type) {
        this.chunk = chunk;
        this.type = type;
    }
    
    public static ChunkEvent load(final Chunk chunk) {
        return new ChunkEvent(chunk, Type.LOAD);
    }
    
    public static ChunkEvent unload(final Chunk chunk) {
        return new ChunkEvent(chunk, Type.UNLOAD);
    }
    
    public Chunk getChunk() {
        return this.chunk;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public enum Type
    {
        LOAD, 
        UNLOAD;
    }
}
