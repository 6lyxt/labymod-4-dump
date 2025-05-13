// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.util;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import net.labymod.api.util.Lazy;
import com.sun.jna.win32.StdCallLibrary;

public interface NtDll extends StdCallLibrary
{
    public static final int MASK = -268435456;
    public static final NtDll INSTANCE = (NtDll)Native.load("ntdll", (Class)NtDll.class);
    public static final Lazy<WindowsVersion> WINDOWS_VERSION = Lazy.of(() -> {
        final IntByReference majorVersion = new IntByReference();
        final IntByReference minorVersion = new IntByReference();
        final IntByReference buildNumber = new IntByReference();
        NtDll.INSTANCE.RtlGetNtVersionNumbers(majorVersion, minorVersion, buildNumber);
        final int cleanBuildNumber = buildNumber.getValue() & 0xFFFFFFF;
        final boolean isDebuggingBuild = (buildNumber.getValue() & 0xF0000000) == 0xC0000000;
        return new WindowsVersion(majorVersion.getValue(), minorVersion.getValue(), cleanBuildNumber, isDebuggingBuild);
    });
    
    void RtlGetNtVersionNumbers(final IntByReference p0, final IntByReference p1, final IntByReference p2);
}
