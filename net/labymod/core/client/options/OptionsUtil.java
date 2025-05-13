// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.options;

import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.models.OperatingSystem;

public final class OptionsUtil
{
    private static final int RENDER_DISTANCE_NORMAL = 8;
    private static final int RENDER_DISTANCE_FAR = 12;
    private static final int RENDER_DISTANCE_REALLY_FAR = 16;
    private static final int RENDER_DISTANCE_EXTREME = 32;
    private static final OperatingSystem PLATFORM;
    
    public static int getRenderDistance(final int currentRenderDistance) {
        if (OptiFine.isPresent()) {
            return currentRenderDistance;
        }
        final boolean is64Bit = OptionsUtil.PLATFORM.is64BitSystem();
        final boolean allowExtremeRenderDistance = is64Bit && Runtime.getRuntime().maxMemory() >= 1000000000L;
        final int maxRenderDistance = allowExtremeRenderDistance ? 32 : 16;
        if (currentRenderDistance <= maxRenderDistance) {
            return currentRenderDistance;
        }
        return is64Bit ? 12 : 8;
    }
    
    static {
        PLATFORM = OperatingSystem.getPlatform();
    }
}
