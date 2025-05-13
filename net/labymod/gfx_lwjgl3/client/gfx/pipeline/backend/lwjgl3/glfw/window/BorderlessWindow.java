// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import java.util.ArrayDeque;
import java.util.function.Supplier;
import java.util.Queue;
import net.labymod.api.util.bounds.MutableRectangle;
import java.util.function.LongFunction;
import net.labymod.api.util.logging.Logging;

public final class BorderlessWindow
{
    private static final Logging LOGGER;
    private final long windowHandle;
    private final LongFunction<Monitor> monitorFindFunction;
    private final MutableRectangle previousWindowedRectangle;
    private final MutableRectangle windowedRectangle;
    private boolean fullscreen;
    private final Queue<Runnable> queue;
    
    public BorderlessWindow(final long windowHandle, final Supplier<MutableRectangle> windowedRectangle, final Supplier<MutableRectangle> previousWindowedRectangle, final LongFunction<Monitor> monitorFindFunction, final FullscreenWindowController fullscreenWindowController) {
        this.queue = new ArrayDeque<Runnable>();
        this.windowHandle = windowHandle;
        this.windowedRectangle = windowedRectangle.get();
        this.previousWindowedRectangle = previousWindowedRectangle.get();
        this.monitorFindFunction = monitorFindFunction;
        GLFWUtil.addBorderlessWindowedChangeListener(value -> {
            if (fullscreenWindowController.isWindowFullscreen()) {
                this.previousWindowedRectangle.set(this.windowedRectangle);
                this.queue.add(() -> fullscreenWindowController.setWindowFullscreen(value));
            }
            else if (this.isFullscreen()) {
                this.setFullscreen(false);
                this.queue.add(() -> fullscreenWindowController.setWindowFullscreen(true));
                this.windowedRectangle.set(this.previousWindowedRectangle);
                this.previousWindowedRectangle.setBounds(0.0f, 0.0f, 0.0f, 0.0f);
            }
        });
    }
    
    public boolean isFullscreen() {
        return this.fullscreen;
    }
    
    public void setFullscreen(final boolean fullscreen) {
        if (this.fullscreen == fullscreen) {
            return;
        }
        this.saveFullscreenOption(this.fullscreen = fullscreen);
        if (fullscreen) {
            final Monitor monitor = this.monitorFindFunction.apply(this.windowHandle);
            if (monitor == null) {
                BorderlessWindow.LOGGER.error("Failed to get a monitor.", new Object[0]);
                this.fullscreen = false;
                return;
            }
            final int x = monitor.x();
            final int y = monitor.y();
            final VideoMode videoMode = monitor.mode();
            if (videoMode == null) {
                BorderlessWindow.LOGGER.error("Failed to get a video mode for the current monitor.", new Object[0]);
                this.fullscreen = false;
                return;
            }
            final int width = videoMode.width();
            final int height = videoMode.height();
            this.queue.add(new WindowTask(this.windowHandle, false, x, y, width, height));
        }
        else {
            final Rectangle rectangle = this.previousWindowedRectangle.hasSize() ? this.previousWindowedRectangle : this.windowedRectangle;
            this.queue.add(new WindowTask(this.windowHandle, true, rectangle));
        }
    }
    
    public void toggleFullscreen() {
        this.setFullscreen(!this.fullscreen);
    }
    
    public void poll() {
        if (this.queue.isEmpty()) {
            return;
        }
        final Runnable task = this.queue.poll();
        if (task == null) {
            return;
        }
        task.run();
    }
    
    private void saveFullscreenOption(final boolean fullscreen) {
        try {
            final MinecraftOptions options = Laby.labyAPI().minecraft().options();
            options.setFullscreen(fullscreen);
            options.save();
        }
        catch (final Exception ex) {}
    }
    
    static {
        LOGGER = Logging.create(BorderlessWindow.class);
    }
    
    record Monitor(int x, int y, @Nullable VideoMode mode) {
        @Nullable
        public VideoMode mode() {
            return this.mode;
        }
    }
    
    record VideoMode(int width, int height) {}
    
    record WindowTask(long handle, boolean decorated, int x, int y, int width, int height) implements Runnable {
        public WindowTask(final long handle, final boolean decorated, final Rectangle rect) {
            this(handle, decorated, (int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        }
        
        @Override
        public void run() {
            GLFWUtil.decoratedWindow(this.handle, this.decorated, this.x, this.y, this.width, this.height);
        }
    }
}
