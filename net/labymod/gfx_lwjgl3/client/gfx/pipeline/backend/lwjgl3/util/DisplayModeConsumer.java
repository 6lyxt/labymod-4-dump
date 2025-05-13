// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import org.lwjgl.opengl.DisplayMode;
import java.util.function.Consumer;

public final class DisplayModeConsumer
{
    private static Consumer<DisplayMode> displayModeConsumer;
    
    public static void setDisplayModeConsumer(final Consumer<DisplayMode> displayModeConsumer) {
        DisplayModeConsumer.displayModeConsumer = displayModeConsumer;
    }
    
    public static void consume(final DisplayMode displayMode) {
        DisplayModeConsumer.displayModeConsumer.accept(displayMode);
    }
    
    static {
        DisplayModeConsumer.displayModeConsumer = (displayMode -> {});
    }
}
