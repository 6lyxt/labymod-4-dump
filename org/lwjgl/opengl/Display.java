// 
// Decompiled by Procyon v0.6.0
// 

package org.lwjgl.opengl;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import net.labymod.api.property.Property;
import java.util.function.Consumer;
import org.lwjgl.glfw.GLFWImage;
import net.labymod.api.util.Buffers;
import org.lwjgl.BufferUtils;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryStack;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import org.lwjgl.system.MemoryUtil;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.Laby;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import org.lwjgl.glfw.GLFWDropCallback;
import java.nio.file.Path;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.VideoMode;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.DisplayModeConsumer;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.Monitor;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.MonitorPredicate;
import org.lwjgl.system.Callback;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.CallbackUtil;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.glfw.GLFWVidMode;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.monitor.MonitorRegistry;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.jetbrains.annotations.Nullable;
import java.nio.ByteBuffer;
import java.util.List;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;
import net.labymod.api.util.logging.Logging;

public final class Display
{
    private static final Logging LOGGER;
    private static final FullscreenWindowController FULLSCREEN_WINDOW_CONTROLLER;
    private static long windowHandle;
    private static String title;
    private static boolean resizeable;
    private static int x;
    private static int y;
    private static int width;
    private static int height;
    private static int framebufferWidth;
    private static int framebufferHeight;
    private static boolean fullscreen;
    private static boolean focused;
    private static DisplayMode displayMode;
    private static DisplayMode initialMode;
    private static boolean windowResized;
    private static List<String> initErrors;
    @Nullable
    private static ByteBuffer[] cachedIcons;
    private static GLFWErrorCallback errorCallback;
    private static MonitorRegistry monitorRegistry;
    private static MutableRectangle previousWindowedRectangle;
    private static MutableRectangle windowedRectangle;
    private static BorderlessWindow borderlessWindow;
    private static boolean actuallyFullscreen;
    
    private Display() {
    }
    
    public static long getWindowHandle() {
        return Display.windowHandle;
    }
    
    public static DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        final long primaryMonitorPtr = GLFW.glfwGetPrimaryMonitor();
        if (primaryMonitorPtr == 0L) {
            return new DisplayMode[0];
        }
        final GLFWVidMode.Buffer buffer = GLFW.glfwGetVideoModes(primaryMonitorPtr);
        if (buffer == null) {
            throw new IllegalStateException("No video mode was found");
        }
        final List<DisplayMode> displayModes = new ArrayList<DisplayMode>();
        for (int index = buffer.limit() - 1; index >= 0; --index) {
            buffer.position(index);
            displayModes.add(new DisplayMode(buffer.width(), buffer.height(), buffer.redBits() + buffer.blueBits() + buffer.greenBits(), buffer.refreshRate()));
        }
        return displayModes.toArray(new DisplayMode[0]);
    }
    
    public static DisplayMode getDisplayMode() {
        return Display.displayMode;
    }
    
    public static void setDisplayMode(final DisplayMode displayMode) {
        Display.displayMode = displayMode;
    }
    
    public static void sync(final int fps) {
        Sync.sync(fps);
    }
    
    public static void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        if (Display.monitorRegistry != null) {
            Display.monitorRegistry.dispose();
        }
        Callbacks.glfwFreeCallbacks(Display.windowHandle);
        CallbackUtil.free((Callback)Display.errorCallback);
        GLFW.glfwDestroyWindow(Display.windowHandle);
        GLFW.glfwTerminate();
    }
    
    public static String getTitle() {
        return Display.title;
    }
    
    public static DisplayMode getDesktopDisplayMode() {
        final long monitor = findBestMonitor();
        if (monitor == 0L) {
            return Display.initialMode;
        }
        return getDisplayMode(monitor);
    }
    
    private static DisplayMode getDisplayMode(final long monitor) {
        final GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
        if (vidMode == null) {
            return Display.initialMode;
        }
        return new DisplayMode(vidMode.width(), vidMode.height(), vidMode.redBits() + vidMode.greenBits() + vidMode.blueBits(), vidMode.refreshRate());
    }
    
    private static long findBestMonitor() {
        if (Display.windowHandle == 0L) {
            return 0L;
        }
        final Monitor monitor = Display.monitorRegistry.findBestMonitor(Display.windowHandle, MonitorPredicate.DISPLAY_MONITOR_FILTER);
        return (monitor == null) ? GLFW.glfwGetPrimaryMonitor() : monitor.getMonitor();
    }
    
    public static boolean isFullscreen() {
        return Display.fullscreen;
    }
    
    public static void setFullscreen(final boolean fullscreen) throws LWJGLException {
        Display.fullscreen = fullscreen;
        if (GLFWUtil.isBorderlessWindowed()) {
            Display.fullscreen = false;
            Display.borderlessWindow.toggleFullscreen();
        }
    }
    
    public static void setTitle(String newTitle) {
        if (newTitle == null) {
            newTitle = "";
        }
        Display.title = newTitle;
        if (isCreated()) {
            GLFW.glfwSetWindowTitle(Display.windowHandle, (CharSequence)Display.title);
        }
    }
    
    public static boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(Display.windowHandle);
    }
    
    public static boolean isActive() {
        return Display.focused;
    }
    
    public static void update() {
        ImGuiPipeline.getInstance().renderFrame();
        GLFW.glfwPollEvents();
        if (Mouse.isCreated()) {
            Mouse.poll();
        }
        if (Keyboard.isCreated()) {
            Keyboard.poll();
        }
        GLFW.glfwSwapBuffers(Display.windowHandle);
        if (Display.fullscreen != Display.actuallyFullscreen) {
            Display.actuallyFullscreen = Display.fullscreen;
            updateFullscreen();
        }
        if (Display.borderlessWindow != null) {
            Display.borderlessWindow.poll();
        }
    }
    
    public static DisplayMode getPrimaryDisplayMode() {
        final long l = GLFW.glfwGetPrimaryMonitor();
        if (l == 0L) {
            return null;
        }
        return getDisplayMode(l);
    }
    
    private static void updateFullscreen() {
        if (!isCreated()) {
            return;
        }
        final long monitor = findBestMonitor();
        if (Display.fullscreen) {
            if (monitor == 0L) {
                Display.fullscreen = false;
                return;
            }
            Display.windowedRectangle.setPosition((float)getX(), (float)getY());
            GLFW.glfwSetWindowMonitor(Display.windowHandle, monitor, 0, 0, Display.displayMode.getWidth(), Display.displayMode.getHeight(), Display.displayMode.getFrequency());
        }
        else {
            GLFW.glfwSetWindowMonitor(Display.windowHandle, 0L, (int)Display.windowedRectangle.getX(), (int)Display.windowedRectangle.getY(), Display.displayMode.getWidth(), Display.displayMode.getHeight(), -1);
        }
    }
    
    public static void create() throws LWJGLException {
        create(new PixelFormat());
    }
    
    public static void create(final PixelFormat pixel_format) throws LWJGLException {
        Display.errorCallback = GLFW.glfwSetErrorCallback(Display::setError);
        if (!GLFW.glfwInit()) {
            throw createException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(131076, 0);
        GLFW.glfwWindowHint(139266, 2);
        GLFW.glfwWindowHint(139267, 0);
        GLFW.glfwWindowHint(139272, 0);
        setResizable(Display.resizeable);
        final DisplayMode displayMode = getDisplayMode();
        final String title = getTitle();
        Display.windowHandle = GLFW.glfwCreateWindow(displayMode.getWidth(), displayMode.getHeight(), (CharSequence)((title == null) ? "Game" : title), 0L, 0L);
        if (Display.windowHandle == 0L) {
            GLFW.glfwTerminate();
            throw createException("Could not create window!");
        }
        Display.monitorRegistry = new MonitorRegistry();
        Display.borderlessWindow = new BorderlessWindow(Display.windowHandle, () -> Display.windowedRectangle, () -> Display.previousWindowedRectangle, handle -> {
            final Monitor monitor = Display.monitorRegistry.findBestMonitor(handle, MonitorPredicate.DISPLAY_MONITOR_FILTER);
            if (monitor == null) {
                return null;
            }
            else {
                final VideoMode videoMode = monitor.currentMode();
                new(net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow.Monitor.class)();
                monitor.getX();
                monitor.getY();
                BorderlessWindow.VideoMode mode = null;
                if (videoMode == null) {
                    mode = null;
                }
                else {
                    new(net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow.VideoMode.class)();
                    new BorderlessWindow.VideoMode(videoMode.width(), videoMode.height());
                }
                final int x;
                final int y;
                new BorderlessWindow.Monitor(x, y, mode);
                return;
            }
        }, Display.FULLSCREEN_WINDOW_CONTROLLER);
        Display.width = displayMode.getWidth();
        Display.height = displayMode.getHeight();
        onWindowResize(Display.windowHandle, Display.width, Display.height);
        GLFW.glfwMakeContextCurrent(Display.windowHandle);
        GL.createCapabilities();
        GLFW.glfwSetWindowFocusCallback(Display.windowHandle, Display::onWindowFocus);
        GLFW.glfwSetWindowSizeCallback(Display.windowHandle, Display::onWindowResize);
        GLFW.glfwSetFramebufferSizeCallback(Display.windowHandle, Display::onFramebufferResize);
        GLFW.glfwSetWindowPosCallback(Display.windowHandle, Display::onWindowMove);
        GLFW.glfwSetDropCallback(Display.windowHandle, Display::onDrop);
        Mouse.create();
        Keyboard.create();
        centerWindow();
        GLFW.glfwShowWindow(Display.windowHandle);
        ImGuiPipeline.getInstance().initialize(Display.windowHandle);
        update();
        initFramebufferSize();
        if (Display.cachedIcons != null) {
            setIcon(Display.cachedIcons);
        }
        DisplayModeConsumer.consume(getDesktopDisplayMode());
        injectLabyMod();
    }
    
    private static void onDrop(final long window, final int count, final long names) {
        final List<Path> files = new ArrayList<Path>(count);
        for (int index = 0; index < count; ++index) {
            final String droppedFileName = GLFWDropCallback.getName(names, index);
            try {
                files.add(Paths.get(droppedFileName, new String[0]));
            }
            catch (final InvalidPathException exception) {
                Display.LOGGER.error("Failed to parse path: {}", droppedFileName, exception);
            }
        }
        if (files.isEmpty()) {
            return;
        }
        for (final Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            ThreadSafe.executeOnRenderThread(() -> openActivity.fileDropped(Laby.labyAPI().minecraft().mouse(), files));
        }
    }
    
    private static IllegalStateException createException(final String reason) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reason).append("\n\n");
        if (!Display.initErrors.isEmpty()) {
            if (Display.initErrors.size() > 1) {
                builder.append("Reasons:").append("\n");
            }
            else {
                builder.append("Reason:").append("\n");
            }
            for (final String initError : Display.initErrors) {
                builder.append(initError);
            }
            Display.initErrors.clear();
        }
        builder.append("\n");
        throw new IllegalStateException(builder.toString());
    }
    
    private static void setError(final int error, final long description) {
        final String errorMessage = "0x" + Integer.toHexString(error) + ": " + MemoryUtil.memUTF8(description);
        Display.initErrors.add(errorMessage);
        Display.LOGGER.error("GLFW error during init: {}", errorMessage);
    }
    
    private static void onWindowFocus(final long handle, final boolean focused) {
        Display.focused = focused;
    }
    
    private static void injectLabyMod() {
        final LabyConfig config = Laby.labyAPI().config();
        final ConfigProperty<Boolean> rawMouseInput = config.hotkeys().rawMouseInput();
        Mouse.setRawMouseInput(rawMouseInput.get());
        rawMouseInput.addChangeListener((type, oldValue, newValue) -> Mouse.setRawMouseInput(newValue));
    }
    
    private static void initFramebufferSize() {
        final int[] widthBuffer = { 0 };
        final int[] heightBuffer = { 0 };
        final long windowHandle = Display.windowHandle;
        GLFW.glfwGetFramebufferSize(windowHandle, widthBuffer, heightBuffer);
        int width = widthBuffer[0];
        int height = heightBuffer[0];
        width = ((width > 0L) ? width : 1);
        height = ((height > 0L) ? height : 1);
        onFramebufferResize(windowHandle, width, height);
    }
    
    private static void onWindowMove(final long window, final int x, final int y) {
        Display.x = x;
        Display.y = y;
        if (!Display.fullscreen && !Display.borderlessWindow.isFullscreen()) {
            Display.windowedRectangle.setPosition((float)x, (float)y);
        }
    }
    
    private static void onFramebufferResize(final long window, final int width, final int height) {
        Display.windowResized = true;
        Display.framebufferWidth = ((width <= 0) ? 1 : width);
        Display.framebufferHeight = ((height <= 0) ? 1 : height);
    }
    
    private static void centerWindow() {
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer widthPointer = stack.mallocInt(1);
            final IntBuffer heightPointer = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(Display.windowHandle, widthPointer, heightPointer);
            final GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(Display.windowHandle, (vidmode.width() - widthPointer.get(0)) / 2, (vidmode.height() - heightPointer.get(0)) / 2);
        }
    }
    
    private static void onWindowResize(final long window, final int newWidth, final int newHeight) {
        Display.windowResized = true;
        Display.width = newWidth;
        Display.height = newHeight;
        if (!Display.fullscreen && !Display.borderlessWindow.isFullscreen()) {
            Display.windowedRectangle.setSize((float)newWidth, (float)newHeight);
        }
    }
    
    public static boolean isCreated() {
        return Display.windowHandle != 0L;
    }
    
    public static void setVSyncEnabled(final boolean sync) {
        GLFW.glfwSwapInterval((int)(sync ? 1 : 0));
    }
    
    public static void setLocation(final int new_x, final int new_y) {
        Display.x = new_x;
        Display.y = new_y;
        if (isCreated() && !isFullscreen()) {
            reshape();
        }
    }
    
    private static void reshape() {
        final DisplayMode mode = getDisplayMode();
        GLFW.glfwSetWindowPos(Display.windowHandle, Display.x, Display.y);
        GLFW.glfwSetWindowSize(Display.windowHandle, mode.getWidth(), mode.getHeight());
    }
    
    public static int getX() {
        return Display.x;
    }
    
    public static int getY() {
        return Display.y;
    }
    
    public static int getWidth() {
        return Display.width;
    }
    
    public static int getHeight() {
        return Display.height;
    }
    
    public static int getFramebufferWidth() {
        return Display.framebufferWidth;
    }
    
    public static int getFramebufferHeight() {
        return Display.framebufferHeight;
    }
    
    public static int setIcon(final ByteBuffer[] icons) {
        if (Display.cachedIcons == null) {
            Display.cachedIcons = new ByteBuffer[icons.length];
            for (int i = 0; i < icons.length; ++i) {
                Display.cachedIcons[i] = Buffers.cloneBuffer(icons[i], BufferUtils::createByteBuffer);
            }
        }
        if (isCreated()) {
            final int size = Display.cachedIcons.length;
            final List<ByteBuffer> buffers = new ArrayList<ByteBuffer>(size);
            try (final MemoryStack stack = MemoryStack.stackPush()) {
                final GLFWImage.Buffer imageBuffer = GLFWImage.malloc(size, stack);
                for (int index = 0; index < size; ++index) {
                    final ByteBuffer cachedBuffer = Display.cachedIcons[index];
                    if (cachedBuffer != null) {
                        buffers.add(fillGLFWImage(imageBuffer, index, cachedBuffer));
                    }
                }
                GLFW.glfwSetWindowIcon(Display.windowHandle, imageBuffer);
            }
            finally {
                buffers.forEach(MemoryUtil::memFree);
            }
            return 1;
        }
        return 0;
    }
    
    private static ByteBuffer fillGLFWImage(final GLFWImage.Buffer imageBuffer, final int index, final ByteBuffer cachedBuffer) {
        final ByteBuffer buffer = MemoryUtil.memAlloc(cachedBuffer.capacity());
        buffer.put(cachedBuffer);
        buffer.flip();
        int imageDimension = buffer.limit() / 4;
        imageDimension = (int)Math.sqrt(imageDimension);
        imageBuffer.position(index);
        imageBuffer.width(imageDimension);
        imageBuffer.height(imageDimension);
        imageBuffer.pixels(buffer);
        return buffer;
    }
    
    public static void setResizable(final boolean resizable) {
        Display.resizeable = resizable;
        if (isCreated()) {
            GLFW.glfwWindowHint(131075, (int)(resizable ? 1 : 0));
        }
    }
    
    public static boolean wasResized() {
        final boolean _windowResized = Display.windowResized;
        Display.windowResized = false;
        return _windowResized;
    }
    
    public static void maximize() {
        GLFW.glfwWindowHint(131075, 1);
        GLFW.glfwMaximizeWindow(Display.windowHandle);
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("GLFW Window");
        FULLSCREEN_WINDOW_CONTROLLER = new DisplayFullscreenWindowController();
        Display.focused = true;
        Display.displayMode = new DisplayMode(640, 480, 24, 60);
        Display.initialMode = new DisplayMode(Display.displayMode);
        Display.initErrors = new ArrayList<String>();
        Display.previousWindowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
        Display.windowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
