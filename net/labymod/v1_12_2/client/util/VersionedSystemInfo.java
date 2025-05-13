// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.util;

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
        oshi.hardware.Processor[] processors = new oshi.hardware.Processor[0];
        try {
            processors = this.systemInfo.getHardware().getProcessors();
        }
        catch (final Throwable throwable) {
            throwable.printStackTrace();
        }
        this.processor = new Processor((processors.length == 0) ? "None" : processors[0].toString());
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
