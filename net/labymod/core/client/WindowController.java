// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client;

import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.core.configuration.ConfigurationEventListenerRegistry;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.util.Pair;
import java.util.function.Supplier;

public class WindowController
{
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    private static boolean maximize;
    
    private WindowController() {
    }
    
    public static Pair<Integer, Integer> calculateNewScreenSize(final int initialWidth, final int initialHeight, final Supplier<Pair<Integer, Integer>> monitorSizeSupplier) {
        WindowController.maximize = false;
        if (initialWidth != 854 || initialHeight != 480) {
            return null;
        }
        int width;
        int height;
        try {
            final LabyConfigProvider labyConfigProvider = LabyConfigProvider.INSTANCE;
            LabyConfig labyConfig = labyConfigProvider.get();
            if (labyConfig == null) {
                ConfigurationEventListenerRegistry.register();
                labyConfig = labyConfigProvider.loadJson();
            }
            final OtherConfig otherConfig = labyConfig.other();
            final boolean restoreResolution = otherConfig.window().restoreWindowResolution().get();
            if (!restoreResolution) {
                return null;
            }
            width = otherConfig.lastWidth().get();
            height = otherConfig.lastHeight().get();
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
        final Pair<Integer, Integer> monitorSizePair = monitorSizeSupplier.get();
        if (monitorSizePair != null && (width >= monitorSizePair.getFirst() || height >= monitorSizePair.getSecond())) {
            width = 1280;
            height = 720;
            WindowController.maximize = true;
        }
        return Pair.of(width, height);
    }
    
    public static void maximize(final Runnable maximizeRunnable) {
        if (WindowController.maximize) {
            maximizeRunnable.run();
        }
    }
}
