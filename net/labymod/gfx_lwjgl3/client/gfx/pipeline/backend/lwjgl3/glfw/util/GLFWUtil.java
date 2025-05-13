// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util;

import net.labymod.api.util.function.Functional;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.labymod.api.property.Property;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;
import net.labymod.api.client.resources.texture.GameImage;
import org.lwjgl.glfw.GLFWImage;
import net.labymod.api.util.color.format.ColorFormat;
import java.io.InputStream;
import net.labymod.api.client.os.OperatingSystemAccessor;
import java.io.IOException;
import net.labymod.core.util.classpath.ClasspathUtil;
import net.labymod.api.Laby;
import net.labymod.api.models.OperatingSystem;
import org.lwjgl.glfw.GLFW;
import net.labymod.api.util.bounds.Rectangle;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntList;

public final class GLFWUtil
{
    private static final String DEBUG_WINDOW_ICON_PATH = "assets/labymod/textures/debug_window_icon.png";
    private static final IntList ACTION_NUMPAD_SCANCODES;
    private static final Int2IntMap MAPPED_ACTION_KEYS;
    
    private GLFWUtil() {
    }
    
    public static void decoratedWindow(final long handle, final boolean decorated, final Rectangle rectangle) {
        decoratedWindow(handle, decorated, (int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
    }
    
    public static void decoratedWindow(final long handle, final boolean decorated, final int x, final int y, final int width, final int height) {
        GLFW.glfwSetWindowAttrib(handle, 131077, (int)(decorated ? 1 : 0));
        GLFW.glfwSetWindowPos(handle, x, y);
        GLFW.glfwSetWindowSize(handle, width, height);
    }
    
    public static void setDebugWindowIcon(final long handle) {
        if (OperatingSystem.isOSX()) {
            return;
        }
        final OperatingSystemAccessor operatingSystemAccessor = Laby.labyAPI().operatingSystemAccessor();
        try (final InputStream stream = ClasspathUtil.getResourceAsInputStream("labymod", "assets/labymod/textures/debug_window_icon.png")) {
            operatingSystemAccessor.setWindowIcon(handle, stream);
        }
        catch (final IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
    
    public static void setIcon(final long handle, final InputStream stream) throws IOException {
        final GameImage image = Laby.references().gameImageProvider().getImage(stream);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final MemoryHandler memoryHandler = Laby.gfx().backend().memoryHandler();
        final ByteBuffer pixelBuffer = memoryHandler.create(width * height * 4);
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = image.getARGB(x, y);
                pixelBuffer.put((byte)colorFormat.red(argb));
                pixelBuffer.put((byte)colorFormat.green(argb));
                pixelBuffer.put((byte)colorFormat.blue(argb));
                pixelBuffer.put((byte)colorFormat.alpha(argb));
            }
        }
        pixelBuffer.flip();
        try (final GLFWImage.Buffer buffer = GLFWImage.malloc(1)) {
            buffer.width(width);
            buffer.height(height);
            buffer.pixels(pixelBuffer);
            GLFW.glfwSetWindowIcon(handle, buffer);
        }
        memoryHandler.free(pixelBuffer);
    }
    
    public static boolean isBorderlessWindowed() {
        return Laby.labyAPI().config().other().window().borderlessWindow().get();
    }
    
    public static void addBorderlessWindowedChangeListener(final BooleanConsumer stateConsumer) {
        Laby.labyAPI().config().other().window().borderlessWindow().addChangeListener((type, oldValue, newValue) -> stateConsumer.accept(newValue));
    }
    
    public static int getNumpadActionKey(final int key, final int scancode, final int mods) {
        final boolean numLockDisabled = (mods & 0xFFFCCFFB) == 0x0;
        if (numLockDisabled && GLFWUtil.ACTION_NUMPAD_SCANCODES.contains(scancode)) {
            return GLFWUtil.MAPPED_ACTION_KEYS.get(scancode);
        }
        return key;
    }
    
    static {
        ACTION_NUMPAD_SCANCODES = (IntList)new IntArrayList(new int[] { 71, 72, 73, 75, 77, 79, 80, 81, 82, 83 });
        MAPPED_ACTION_KEYS = Functional.of((Int2IntMap)new Int2IntArrayMap(), map -> {
            map.put(71, 268);
            map.put(72, 265);
            map.put(73, 266);
            map.put(75, 263);
            map.put(77, 262);
            map.put(79, 269);
            map.put(80, 264);
            map.put(81, 267);
            map.put(82, 260);
            map.put(83, 261);
        });
    }
}
