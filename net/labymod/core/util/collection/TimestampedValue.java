// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.collection;

import net.labymod.api.util.time.TimeUtil;

public class TimestampedValue<T>
{
    private final T value;
    private long timestamp;
    
    public TimestampedValue(final T value) {
        this(TimeUtil.getCurrentTimeMillis(), value);
    }
    
    public TimestampedValue(final long timestamp, final T value) {
        this.timestamp = timestamp;
        this.value = value;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public T getValue() {
        this.timestamp = TimeUtil.getCurrentTimeMillis();
        return this.value;
    }
}
