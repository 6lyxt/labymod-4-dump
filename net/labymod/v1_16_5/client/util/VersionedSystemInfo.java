// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.util;

import javax.inject.Inject;
import net.labymod.api.client.util.Processor;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.util.SystemInfo;

@Singleton
@Implements(SystemInfo.class)
public final class VersionedSystemInfo implements SystemInfo
{
    private final oshi.SystemInfo systemInfo;
    private final Processor processor;
    
    @Inject
    public VersionedSystemInfo() {
        this.systemInfo = new oshi.SystemInfo();
        final oshi.hardware.Processor[] processors = this.systemInfo.getHardware().getProcessors();
        this.processor = new Processor((processors == null) ? "None" : processors[0].getName());
    }
    
    @Override
    public Processor processor() {
        return this.processor;
    }
    
    @Override
    public long getTotalMemorySize() {
        return this.systemInfo.getHardware().getMemory().getTotal();
    }
    
    @Override
    public long getFreeMemorySize() {
        return this.systemInfo.getHardware().getMemory().getAvailable();
    }
    
    @Override
    public double getBatteryLevel() {
        return -1.0;
    }
    
    @Override
    public double getCPUTemperature() {
        return -1.0;
    }
}
