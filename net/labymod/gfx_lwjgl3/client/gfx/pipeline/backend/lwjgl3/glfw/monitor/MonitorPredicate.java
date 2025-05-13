// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import org.lwjgl.opengl.Display;

public interface MonitorPredicate
{
    public static final MonitorPredicate DISPLAY_MONITOR_FILTER = (minX, minY, maxX, maxY) -> {
        final int windowMinX = Display.getX();
        final int windowMinY = Display.getY();
        final int windowMaxX = windowMinX + Display.getWidth();
        final int windowMaxY = windowMinY + Display.getHeight();
        return minX >= windowMaxX || minY >= windowMaxY || maxX <= windowMinX || maxY <= windowMinY;
    };
    
    boolean test(final int p0, final int p1, final int p2, final int p3);
}
