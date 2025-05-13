// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug.jvm;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Locale;
import net.labymod.api.util.time.TimeUtil;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public final class AllocationRate
{
    private static final int DEFAULT_UPDATE_INTERVAL = 500;
    private static final List<GarbageCollectorMXBean> GC_BEANS;
    private static final long ONE_SECOND = 1000L;
    private final long updateInterval;
    private long lastTime;
    private long lastHeapUsage;
    private long lastGarbageCollectorCounts;
    private long lastRate;
    
    public AllocationRate() {
        this(500L);
    }
    
    public AllocationRate(final long updateInterval) {
        this.lastHeapUsage = -1L;
        this.lastGarbageCollectorCounts = -1L;
        this.updateInterval = updateInterval;
    }
    
    public long getBytesAllocatedPerSecond(final long value) {
        final long currentTime = TimeUtil.getCurrentTimeMillis();
        if (currentTime - this.lastTime < this.updateInterval) {
            return this.lastRate;
        }
        final long counts = this.getGarbageCollectorCounts();
        if (this.lastTime != 0L && counts == this.lastGarbageCollectorCounts) {
            final double diffTime = 1000.0 / (currentTime - this.lastTime);
            final long diff = value - this.lastHeapUsage;
            this.lastRate = Math.round(diff * diffTime);
        }
        this.lastTime = currentTime;
        this.lastHeapUsage = value;
        this.lastGarbageCollectorCounts = counts;
        return this.lastRate;
    }
    
    public long getMegaBytesAllocatedPerSecond(final long value) {
        return this.getBytesAllocatedPerSecond(value) / 1024L / 1024L;
    }
    
    public String getAllocationRate() {
        final Runtime runtime = Runtime.getRuntime();
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long allocationRate = totalMemory - freeMemory;
        return String.format(Locale.ROOT, "%03dMB /s", this.getMegaBytesAllocatedPerSecond(allocationRate));
    }
    
    private long getGarbageCollectorCounts() {
        long count = 0L;
        for (final GarbageCollectorMXBean bean : AllocationRate.GC_BEANS) {
            count += bean.getCollectionCount();
        }
        return count;
    }
    
    static {
        GC_BEANS = ManagementFactory.getGarbageCollectorMXBeans();
    }
}
