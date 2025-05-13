// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.time;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public interface TimeSource extends LongSupplier
{
    long get(final TimeUnit p0);
}
