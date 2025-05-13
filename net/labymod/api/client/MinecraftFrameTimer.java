// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.profiler.SampleLogger;

public interface MinecraftFrameTimer
{
    @NotNull
    SampleLogger logger();
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default long averageDuration(final int value) {
        return 0L;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default int scaleSampleTo(final long param0, final int param1, final int param2) {
        return 0;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default long scaleAverageDurationTo(final int value, final int anotherValue) {
        return 0L;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default int logStart() {
        return 0;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default int logEnd() {
        return 0;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default int wrapIndex(final int value) {
        return 0;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.10")
    default long[] log() {
        return new long[0];
    }
}
