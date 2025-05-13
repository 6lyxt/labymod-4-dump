// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.linux.clipboard;

import net.labymod.api.Laby;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.resources.texture.GameImage;
import com.sun.jna.Pointer;
import java.util.concurrent.ExecutorService;
import java.util.Objects;
import java.util.concurrent.Executors;
import com.sun.jna.Native;
import com.sun.jna.Library;

public interface GTKLib extends Library
{
    public static final GTKLib INSTANCE = load();
    public static final int GDK_COLORSPACE_RGB = 0;
    
    default GTKLib load() {
        final GTKLib lib = (GTKLib)Native.load("libgtk-3", (Class)GTKLib.class);
        lib.gtk_init(0, null);
        final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        final GTKLib obj = lib;
        Objects.requireNonNull(obj);
        singleThreadExecutor.execute(obj::gtk_main);
        return lib;
    }
    
    void gtk_init(final int p0, final String[] p1);
    
    void gtk_main();
    
    void gtk_main_quit();
    
    Pointer gdk_display_get_default();
    
    Pointer gtk_clipboard_get_default(final Pointer p0);
    
    void gtk_clipboard_set_text(final Pointer p0, final String p1, final int p2);
    
    String gtk_clipboard_wait_for_text(final Pointer p0);
    
    Pointer g_bytes_new(final byte[] p0, final int p1);
    
    int g_bytes_get_size(final Pointer p0);
    
    Pointer g_bytes_get_data(final Pointer p0, final Pointer p1);
    
    void g_bytes_unref(final Pointer p0);
    
    Pointer gdk_pixbuf_new_from_bytes(final Pointer p0, final int p1, final boolean p2, final int p3, final int p4, final int p5, final int p6);
    
    boolean gdk_pixbuf_get_has_alpha(final Pointer p0);
    
    int gdk_pixbuf_get_width(final Pointer p0);
    
    int gdk_pixbuf_get_height(final Pointer p0);
    
    Pointer gdk_pixbuf_read_pixel_bytes(final Pointer p0);
    
    void gtk_clipboard_set_image(final Pointer p0, final Pointer p1);
    
    Pointer gtk_clipboard_wait_for_image(final Pointer p0);
    
    boolean gdk_display_supports_clipboard_persistence(final Pointer p0);
    
    void gtk_clipboard_store(final Pointer p0);
    
    void gdk_pixbuf_unref(final Pointer p0);
    
    default Pointer toPixelBuf(final GameImage image) {
        final GTKLib lib = GTKLib.INSTANCE;
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlpha = image.getImage().getColorModel().hasAlpha();
        final byte[] data = new byte[width * height * (hasAlpha ? 4 : 3)];
        int i = 0;
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = image.getARGB(x, y);
                final byte red = (byte)colorFormat.red(argb);
                final byte green = (byte)colorFormat.green(argb);
                final byte blue = (byte)colorFormat.blue(argb);
                final byte alpha = (byte)colorFormat.alpha(argb);
                data[i++] = red;
                data[i++] = green;
                data[i++] = blue;
                if (hasAlpha) {
                    data[i++] = alpha;
                }
            }
        }
        final Pointer gBytes = lib.g_bytes_new(data, data.length);
        final Pointer pixelBuf = lib.gdk_pixbuf_new_from_bytes(gBytes, 0, hasAlpha, 8, width, height, width * (hasAlpha ? 4 : 3));
        lib.g_bytes_unref(gBytes);
        return pixelBuf;
    }
    
    default GameImage fromPixelBuf(final Pointer pixelBuf) {
        final GTKLib lib = GTKLib.INSTANCE;
        final int width = lib.gdk_pixbuf_get_width(pixelBuf);
        final int height = lib.gdk_pixbuf_get_height(pixelBuf);
        final boolean hasAlpha = lib.gdk_pixbuf_get_has_alpha(pixelBuf);
        final Pointer gBytes = lib.gdk_pixbuf_read_pixel_bytes(pixelBuf);
        final int size = lib.g_bytes_get_size(gBytes);
        final Pointer memory = lib.g_bytes_get_data(gBytes, null);
        final byte[] data = memory.getByteArray(0L, size);
        final GameImage image = Laby.references().gameImageProvider().createImage(width, height);
        int i = 0;
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int r = data[i++] & 0xFF;
                final int g = data[i++] & 0xFF;
                final int b = data[i++] & 0xFF;
                final int a = hasAlpha ? (data[i++] & 0xFF) : 255;
                image.setARGB(x, y, colorFormat.pack(r, g, b, a));
            }
        }
        lib.gdk_pixbuf_unref(pixelBuf);
        return image;
    }
}
