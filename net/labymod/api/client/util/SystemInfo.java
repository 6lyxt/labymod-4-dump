// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.util;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SystemInfo
{
    Processor processor();
    
    long getTotalMemorySize();
    
    long getFreeMemorySize();
    
    double getBatteryLevel();
    
    double getCPUTemperature();
}
