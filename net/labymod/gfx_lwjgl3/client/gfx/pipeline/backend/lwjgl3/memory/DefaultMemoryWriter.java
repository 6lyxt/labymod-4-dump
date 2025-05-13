// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory;

import org.lwjgl.system.MemoryUtil;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryWriter;

public class DefaultMemoryWriter implements MemoryWriter
{
    private final long address;
    private long current;
    
    public DefaultMemoryWriter(final long address) {
        this.address = address;
    }
    
    @Override
    public MemoryWriter put(final byte value) {
        MemoryUtil.memPutByte(this.getCurrentPosition(), value);
        ++this.current;
        return this;
    }
    
    @Override
    public MemoryWriter putShort(final short value) {
        MemoryUtil.memPutShort(this.getCurrentPosition(), value);
        this.current += 2L;
        return this;
    }
    
    @Override
    public MemoryWriter putInt(final int value) {
        MemoryUtil.memPutInt(this.getCurrentPosition(), value);
        this.current += 4L;
        return this;
    }
    
    @Override
    public MemoryWriter putLong(final long value) {
        MemoryUtil.memPutLong(this.getCurrentPosition(), value);
        this.current += 8L;
        return this;
    }
    
    @Override
    public MemoryWriter putFloat(final float value) {
        MemoryUtil.memPutFloat(this.getCurrentPosition(), value);
        this.current += 4L;
        return this;
    }
    
    @Override
    public MemoryWriter set(final int value, final int bytes) {
        MemoryOperations.set(this.address, value, bytes);
        return this;
    }
    
    @Override
    public void start() {
        this.current = 0L;
    }
    
    @Override
    public void finish() {
    }
    
    private long getCurrentPosition() {
        return this.address + this.current;
    }
}
