// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.time;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface NanosecondsTimeSource extends TimeSource
{
    default long get(final TimeUnit unit) {
        return unit.convert(this.getAsLong(), TimeUnit.NANOSECONDS);
    }
}
