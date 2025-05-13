// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.util;

import java.util.List;
import oshi.hardware.PowerSource;
import javax.inject.Inject;
import oshi.hardware.CentralProcessor;
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
        final CentralProcessor centralProcessor = this.systemInfo.getHardware().getProcessor();
        this.processor = new Processor((centralProcessor == null) ? "None" : centralProcessor.getProcessorIdentifier().getName());
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
        final List<PowerSource> powerSources = this.systemInfo.getHardware().getPowerSources();
        return powerSources.isEmpty() ? -1.0 : (powerSources.get(0).getRemainingCapacityPercent() * 100.0);
    }
    
    @Override
    public double getCPUTemperature() {
        return this.systemInfo.getHardware().getSensors().getCpuTemperature();
    }
}
