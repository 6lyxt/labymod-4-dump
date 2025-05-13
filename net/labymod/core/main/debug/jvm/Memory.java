// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.jvm;

import java.util.Locale;

public final class Memory
{
    public String getMemory() {
        final Runtime runtime = Runtime.getRuntime();
        final long maxMemory = runtime.maxMemory();
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long diff = totalMemory - freeMemory;
        return String.format(Locale.ROOT, "% 2d%% %03d/%03dMB", diff * 100L / maxMemory, this.bytesToMegabytes(diff), this.bytesToMegabytes(maxMemory));
    }
    
    public String getAllocated() {
        final Runtime runtime = Runtime.getRuntime();
        final long maxMemory = runtime.maxMemory();
        final long totalMemory = runtime.totalMemory();
        final long percentage = totalMemory * 100L / maxMemory;
        return String.format(Locale.ROOT, "% 2d%% %03dMB", percentage, this.bytesToMegabytes(totalMemory));
    }
    
    private long bytesToMegabytes(final long value) {
        return value / 1024L / 1024L;
    }
}
