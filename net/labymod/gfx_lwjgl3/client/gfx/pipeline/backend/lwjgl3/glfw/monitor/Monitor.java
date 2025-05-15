// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class Monitor
{
    private final long monitorHandle;
    private final List<VideoMode> videoModes;
    private VideoMode currentMode;
    private int x;
    private int y;
    
    public Monitor(final long monitorHandle) {
        this.monitorHandle = monitorHandle;
        this.videoModes = new ArrayList<VideoMode>();
        this.refresh();
    }
    
    public void refresh() {
        this.videoModes.clear();
        final GLFWVidMode.Buffer videoModesBuffer = GLFW.glfwGetVideoModes(this.monitorHandle);
        for (int index = videoModesBuffer.limit() - 1; index >= 0; --index) {
            videoModesBuffer.position(index);
            final VideoMode videoMode = VideoMode.of(videoModesBuffer);
            if (videoMode.redBits() >= 8 && videoMode.greenBits() >= 8 && videoMode.blueBits() >= 8) {
                this.videoModes.add(videoMode);
            }
        }
        final int[] x = { 0 };
        final int[] y = { 0 };
        GLFW.glfwGetMonitorPos(this.monitorHandle, x, y);
        this.x = x[0];
        this.y = y[0];
        final GLFWVidMode currentVidMode = GLFW.glfwGetVideoMode(this.monitorHandle);
        this.currentMode = VideoMode.of(currentVidMode);
    }
    
    public VideoMode currentMode() {
        return this.currentMode;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public VideoMode getMode(final int index) {
        return this.videoModes.get(index);
    }
    
    public int getModeCount() {
        return this.videoModes.size();
    }
    
    public long getMonitor() {
        return this.monitorHandle;
    }
}
