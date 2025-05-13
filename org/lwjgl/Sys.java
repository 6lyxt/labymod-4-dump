// 
// Decompiled by Procyon v0.6.0
// 

package org.lwjgl;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.models.OperatingSystem;

public class Sys
{
    private static final long TIMER_RESOLUTION = 1000000000L;
    private static final long TIMER_OFFSET;
    private static final String VERSION;
    
    private Sys() {
    }
    
    public static String getVersion() {
        return Sys.VERSION;
    }
    
    public static long getTimerResolution() {
        return 1000000000L;
    }
    
    public static long getTime() {
        return System.nanoTime() - Sys.TIMER_OFFSET & Long.MAX_VALUE;
    }
    
    public static void initialize() {
    }
    
    public static boolean openURL(final String url) {
        final OperatingSystem platform = OperatingSystem.getPlatform();
        platform.openUrl(url);
        return true;
    }
    
    static {
        TIMER_OFFSET = TimeUtil.getNanoTime();
        VERSION = Version.getVersion();
    }
}
