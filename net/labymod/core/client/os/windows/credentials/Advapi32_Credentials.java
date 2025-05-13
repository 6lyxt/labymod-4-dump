// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.credentials;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Advapi32_Credentials extends StdCallLibrary
{
    public static final Advapi32_Credentials INSTANCE = (Advapi32_Credentials)Native.load("advapi32", (Class)Advapi32_Credentials.class);
    
    boolean CredEnumerateW(final String p0, final int p1, final IntByReference p2, final PointerByReference p3);
    
    boolean CredWriteW(final Credential p0, final int p1);
}
