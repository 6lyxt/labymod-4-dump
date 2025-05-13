// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public final class DebugStatistics
{
    private static final Map<UUID, Runnable> BUFFER_BUILDERS;
    
    public static void registerBufferBuilder(final UUID uuid, final Runnable renderTask) {
        DebugStatistics.BUFFER_BUILDERS.put(uuid, renderTask);
    }
    
    public static void unregisterBufferBuilder(final UUID uuid) {
        DebugStatistics.BUFFER_BUILDERS.remove(uuid);
    }
    
    public static Map<UUID, Runnable> getBufferBuilders() {
        return DebugStatistics.BUFFER_BUILDERS;
    }
    
    static {
        BUFFER_BUILDERS = new HashMap<UUID, Runnable>();
    }
}
