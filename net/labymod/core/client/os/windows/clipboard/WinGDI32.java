// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.clipboard;

import net.labymod.api.Laby;
import com.sun.jna.Pointer;
import net.labymod.api.util.color.format.ColorFormat;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.Native;
import net.labymod.api.client.resources.texture.GameImage;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface WinGDI32 extends StdCallLibrary
{
    public static final WinGDI32 INSTANCE = (WinGDI32)Native.load("gdi32", (Class)WinGDI32.class);
    
    boolean SetDIBitsToDevice(final WinDef.HDC p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final byte[] p9, final WinGDI.BITMAPINFO p10, final int p11);
    
    int GetBitmapBits(final WinNT.HANDLE p0, final int p1, final byte[] p2);
    
    default WinNT.HANDLE toBitMap(final GameImage image) {
        final WinDef.HDC screen = WinUser.INSTANCE.GetDC(null);
        if (screen == null) {
            throw new RuntimeException("Error GetDC: " + Native.getLastError());
        }
        final GDI32 gdi32 = GDI32.INSTANCE;
        final WinDef.HDC backBuffer = gdi32.CreateCompatibleDC(screen);
        if (backBuffer == null) {
            throw new RuntimeException("Error CreateCompatibleDC: " + Native.getLastError());
        }
        final WinDef.HBITMAP bitMap = gdi32.CreateCompatibleBitmap(screen, image.getWidth(), image.getHeight());
        if (bitMap == null) {
            throw new RuntimeException("Error CreateCompatibleBitmap: " + Native.getLastError());
        }
        if (gdi32.SelectObject(backBuffer, (WinNT.HANDLE)bitMap) == null) {
            throw new RuntimeException("Error SelectObject: " + Native.getLastError());
        }
        final WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
        info.bmiHeader.biWidth = image.getWidth();
        info.bmiHeader.biHeight = -image.getHeight();
        info.bmiHeader.biPlanes = 1;
        info.bmiHeader.biBitCount = 32;
        info.bmiHeader.biCompression = 0;
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int color = image.getARGB(x, y);
                final byte red = (byte)colorFormat.red(color);
                final byte green = (byte)colorFormat.green(color);
                final byte blue = (byte)colorFormat.blue(color);
                final byte alpha = (byte)colorFormat.alpha(color);
                final int index = (y * image.getWidth() + x) * 4;
                buffer[index] = blue;
                buffer[index + 1] = green;
                buffer[index + 2] = red;
                buffer[index + 3] = alpha;
            }
        }
        final boolean result = WinGDI32.INSTANCE.SetDIBitsToDevice(backBuffer, 0, 0, image.getWidth(), image.getHeight(), 0, 0, 0, image.getHeight(), buffer, info, 0);
        if (!result) {
            throw new RuntimeException("Error SetDIBitsToDevice: " + Native.getLastError());
        }
        gdi32.DeleteDC(backBuffer);
        gdi32.DeleteDC(screen);
        return (WinNT.HANDLE)bitMap;
    }
    
    default GameImage fromBitMap(final WinNT.HANDLE data) {
        final WinDef.HDC screen = WinUser.INSTANCE.GetDC(null);
        if (screen == null) {
            throw new RuntimeException("Error GetDC: " + Native.getLastError());
        }
        final GDI32 gdi32 = GDI32.INSTANCE;
        final WinDef.HDC backBuffer = gdi32.CreateCompatibleDC(screen);
        if (backBuffer == null) {
            throw new RuntimeException("Error CreateCompatibleDC: " + Native.getLastError());
        }
        final WinGDI.BITMAPINFO info = new WinGDI.BITMAPINFO();
        final WinDef.HBITMAP hbitmap = new WinDef.HBITMAP(data.getPointer());
        final int status = GDI32.INSTANCE.GetDIBits(backBuffer, hbitmap, 0, 0, (Pointer)null, info, 0);
        if (status == 0) {
            throw new RuntimeException("Error GetDIBits: " + Native.getLastError());
        }
        final GameImage image = Laby.references().gameImageProvider().createImage(info.bmiHeader.biWidth, info.bmiHeader.biHeight);
        final byte[] buffer = new byte[info.bmiHeader.biWidth * info.bmiHeader.biHeight * 4];
        final int length = WinGDI32.INSTANCE.GetBitmapBits((WinNT.HANDLE)hbitmap, buffer.length, buffer);
        if (length <= 0) {
            throw new RuntimeException("Error GetBitmapBits: " + Native.getLastError());
        }
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        for (int y = 0; y < info.bmiHeader.biHeight; ++y) {
            for (int x = 0; x < info.bmiHeader.biWidth; ++x) {
                final int index = (y * info.bmiHeader.biWidth + x) * 4;
                final byte red = buffer[index + 2];
                final byte green = buffer[index + 1];
                final byte blue = buffer[index];
                final byte alpha = buffer[index + 3];
                final int color = colorFormat.pack(red, green, blue, alpha);
                image.setARGB(x, y, color);
            }
        }
        gdi32.DeleteDC(backBuffer);
        gdi32.DeleteDC(screen);
        return image;
    }
}
