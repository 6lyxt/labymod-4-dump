// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.window;

import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface DwmApi extends StdCallLibrary
{
    public static final DwmApi INSTANCE = (DwmApi)Native.load("dwmapi", (Class)DwmApi.class, W32APIOptions.DEFAULT_OPTIONS);
    public static final WinDef.DWORD DWMWA_USE_IMMERSIVE_DARK_MODE = new WinDef.DWORD(20L);
    public static final WinDef.DWORD DWMWA_WINDOW_CORNER_PREFERENCE = new WinDef.DWORD(33L);
    public static final WinDef.DWORD DWMWA_BORDER_COLOR = new WinDef.DWORD(34L);
    public static final WinDef.DWORD DWMWA_CAPTION_COLOR = new WinDef.DWORD(35L);
    public static final WinDef.DWORD DWMWA_TEXT_COLOR = new WinDef.DWORD(36L);
    public static final WinDef.DWORD DWMWA_SYSTEM_BACKDROP_TYPE = new WinDef.DWORD(38L);
    public static final int DWMWA_COLOR_NONE = -2;
    public static final int DWMWA_COLOR_DEFAULT = -1;
    
    int DwmSetWindowAttribute(final WinDef.HWND p0, final WinDef.DWORD p1, final WinDef.LPVOID p2, final WinDef.DWORD p3);
}
