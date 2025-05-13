// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.linux.credentials;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.Library;

public interface GKLib extends Library
{
    public static final GKLib INSTANCE = (GKLib)Native.loadLibrary("gnome-keyring", (Class)GKLib.class);
    
    int gnome_keyring_item_get_info_full_sync(final String p0, final int p1, final int p2, final PointerByReference p3);
    
    void gnome_keyring_item_info_free(final Pointer p0);
    
    String gnome_keyring_item_info_get_display_name(final Pointer p0);
    
    String gnome_keyring_item_info_get_secret(final Pointer p0);
    
    String gnome_keyring_result_to_message(final int p0);
    
    int gnome_keyring_get_default_keyring_sync(final PointerByReference p0);
    
    int gnome_keyring_list_item_ids_sync(final String p0, final PointerByReference p1);
}
