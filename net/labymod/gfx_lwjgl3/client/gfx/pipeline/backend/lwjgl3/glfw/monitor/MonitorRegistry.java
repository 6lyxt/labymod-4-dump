// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.system.Callback;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.CallbackUtil;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.Disposable;

public final class MonitorRegistry implements Disposable
{
    private static final Logging LOGGER;
    private final Long2ObjectMap<Monitor> monitors;
    
    public MonitorRegistry() {
        this.monitors = (Long2ObjectMap<Monitor>)new Long2ObjectOpenHashMap();
        GLFW.glfwSetMonitorCallback(this::onMonitorChange);
        this.collectMonitors();
    }
    
    @Nullable
    public Monitor findBestMonitor(final long windowHandle) {
        return this.findBestMonitor(windowHandle, (minX, minY, maxX, maxY) -> true);
    }
    
    @Nullable
    public Monitor findBestMonitor(final long windowHandle, final MonitorPredicate filter) {
        final long monitorHandle = GLFW.glfwGetWindowMonitor(windowHandle);
        if (monitorHandle != 0L) {
            return this.getMonitor(monitorHandle);
        }
        for (final Monitor monitor : this.monitors.values()) {
            final VideoMode videoMode = monitor.currentMode();
            final int monitorX = monitor.getX();
            final int monitorY = monitor.getY();
            final int maxMonitorX = monitorX + videoMode.width();
            final int maxMonitorY = monitorY + videoMode.height();
            if (filter.test(monitorX, monitorY, maxMonitorX, maxMonitorY)) {
                continue;
            }
            return monitor;
        }
        final long primaryMonitorHandle = GLFW.glfwGetPrimaryMonitor();
        MonitorRegistry.LOGGER.info("Selecting primary monitor: {}", primaryMonitorHandle);
        return new Monitor(primaryMonitorHandle);
    }
    
    @Nullable
    public Monitor getMonitor(final long handle) {
        return (Monitor)this.monitors.get(handle);
    }
    
    @Override
    public void dispose() {
        final GLFWMonitorCallback callback = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI)null);
        CallbackUtil.free((Callback)callback);
    }
    
    private void collectMonitors() {
        final PointerBuffer monitorsBuffer = GLFW.glfwGetMonitors();
        if (monitorsBuffer == null) {
            return;
        }
        for (int index = 0; index < monitorsBuffer.limit(); ++index) {
            final long monitorHandle = monitorsBuffer.get(index);
            this.addMonitor(monitorHandle);
        }
    }
    
    private void onMonitorChange(final long handle, final int event) {
        switch (event) {
            case 262145: {
                this.addMonitor(handle);
                MonitorRegistry.LOGGER.info("Monitor {} connected.", handle);
                break;
            }
            case 262146: {
                this.monitors.remove(handle);
                MonitorRegistry.LOGGER.info("Monitor {} disconnected.", new Object[0]);
                break;
            }
        }
    }
    
    private void addMonitor(final long handle) {
        this.monitors.put(handle, (Object)new Monitor(handle));
    }
    
    static {
        LOGGER = Logging.create(MonitorRegistry.class);
    }
}
