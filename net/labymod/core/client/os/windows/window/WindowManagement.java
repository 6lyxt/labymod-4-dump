// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.window;

import net.labymod.api.util.Lazy;
import net.labymod.core.client.os.windows.util.NtDll;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.Pointer;
import net.labymod.api.Laby;
import net.labymod.core.client.os.windows.util.WindowsVersion;
import net.labymod.api.models.OperatingSystem;

public final class WindowManagement
{
    private static final long NULL = 0L;
    private static final int MINIMUM_BUILD_NUMBER = 22000;
    private static final int MINIMUM_WINDOW_MATERIAL_BUILD_NUMBER = 22621;
    
    public static boolean isCompatible() {
        if (OperatingSystem.getPlatform() != OperatingSystem.WINDOWS) {
            return false;
        }
        final WindowsVersion version = windowsVersion();
        return version.majorVersion() >= 10 && version.buildNumber() >= 22000;
    }
    
    public static boolean isWindowMaterialSupported() {
        if (OperatingSystem.getPlatform() != OperatingSystem.WINDOWS) {
            return false;
        }
        final WindowsVersion version = windowsVersion();
        return version.majorVersion() >= 10 && version.buildNumber() >= 22621;
    }
    
    public static void update(final long windowPointer) {
        if (windowPointer == 0L) {
            return;
        }
        if (!isCompatible()) {
            return;
        }
        final long win32WindowHandle = Laby.gfx().backend().glfwNatives().getWin32Window(windowPointer);
        final WinDef.HWND hwnd = new WinDef.HWND(Pointer.createConstant(win32WindowHandle));
        final MicrosoftWindowConfig config = Laby.labyAPI().config().other().window().microsoftWindow();
        setWindowAttribute(hwnd, DwmApi.DWMWA_USE_IMMERSIVE_DARK_MODE, config.immersiveDarkMode());
        if (isWindowMaterialSupported()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_SYSTEM_BACKDROP_TYPE, config.windowMaterial().get());
        }
        setWindowAttribute(hwnd, DwmApi.DWMWA_WINDOW_CORNER_PREFERENCE, config.cornerCurvatures().get());
        if (config.defaultWindowBorderColor().get()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, -1);
        }
        else if (config.hideWindowBorder().get()) {
            setWindowAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, -2);
        }
        else {
            setWindowColorAttribute(hwnd, DwmApi.DWMWA_BORDER_COLOR, config.windowBorderColor());
        }
        setWindowAttribute(hwnd, DwmApi.DWMWA_CAPTION_COLOR, config.defaultWindowTitleBarColor(), config.windowTitleBarColor());
        setWindowAttribute(hwnd, DwmApi.DWMWA_TEXT_COLOR, config.defaultTitleTextColor(), config.titleTextColor());
    }
    
    private static void setWindowAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final ConfigProperty<Boolean> state, final ConfigProperty<Color> color) {
        if (state.get()) {
            setWindowAttribute(hwnd, attribute, -1);
        }
        else {
            setWindowColorAttribute(hwnd, attribute, color);
        }
    }
    
    private static void setWindowColorAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final ConfigProperty<Color> value) {
        int colorValue = value.get().get();
        colorValue = ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, colorValue);
        setWindowAttribute(hwnd, attribute, colorValue & 0xFFFFFF);
    }
    
    private static void setWindowAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final MicrosoftWindowConfig.IdProvider provider) {
        setWindowAttribute(hwnd, attribute, provider.getId());
    }
    
    private static void setWindowAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final ConfigProperty<Boolean> value) {
        setWindowAttribute(hwnd, attribute, value.get());
    }
    
    private static void setWindowAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final boolean value) {
        setWindowAttribute(hwnd, attribute, value ? 1 : 0);
    }
    
    private static void setWindowAttribute(final WinDef.HWND hwnd, final WinDef.DWORD attribute, final int value) {
        try (final Memory memory = new Memory((long)Native.POINTER_SIZE)) {
            memory.setInt(0L, value);
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, attribute, new WinDef.LPVOID((Pointer)memory), new WinDef.DWORD(4L));
        }
    }
    
    private static WindowsVersion windowsVersion() {
        final Lazy<WindowsVersion> version = NtDll.WINDOWS_VERSION;
        version.reset();
        return version.get();
    }
}
