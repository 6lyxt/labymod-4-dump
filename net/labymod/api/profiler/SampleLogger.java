// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.profiler;

import java.util.Objects;

public class SampleLogger
{
    private static final int DEFAULT_CAPACITY = 240;
    private final int capacity;
    private final long[] samples;
    private int start;
    private int size;
    
    public SampleLogger() {
        this(240);
    }
    
    public SampleLogger(final int capacity) {
        this.capacity = capacity;
        this.samples = new long[capacity];
    }
    
    public void log(final long sample) {
        final int wrappedIndex = this.wrapIndex(this.start + this.size);
        this.samples[wrappedIndex] = sample;
        if (this.size < this.capacity) {
            ++this.size;
        }
        else {
            this.start = this.wrapIndex(this.start + 1);
        }
    }
    
    public long get(final int index) {
        Objects.checkIndex(index, this.size);
        final int wrappedIndex = this.wrapIndex(this.start + index);
        return this.samples[wrappedIndex];
    }
    
    public int capacity() {
        return this.capacity;
    }
    
    public int size() {
        return this.size;
    }
    
    public void reset() {
        this.start = 0;
        this.size = 0;
    }
    
    private int wrapIndex(final int index) {
        return index % this.capacity;
    }
}
