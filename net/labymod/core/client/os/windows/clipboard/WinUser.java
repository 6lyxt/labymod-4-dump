// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.clipboard;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;

public interface WinUser extends StdCallLibrary
{
    public static final int CF_BITMAP = 2;
    public static final int CF_DIB = 8;
    public static final WinUser INSTANCE = (WinUser)Native.load("user32", (Class)WinUser.class);
    
    boolean OpenClipboard(final int p0);
    
    boolean EmptyClipboard();
    
    boolean CloseClipboard();
    
    boolean SetClipboardData(final int p0, final WinNT.HANDLE p1);
    
    WinDef.HDC GetDC(final WinDef.HWND p0);
    
    boolean IsClipboardFormatAvailable(final int p0);
    
    WinNT.HANDLE GetClipboardData(final int p0);
}
