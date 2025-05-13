// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.linux;

import net.labymod.core.client.os.linux.credentials.LinuxCredentialsAccessor;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import com.sun.jna.Pointer;
import net.labymod.core.client.os.linux.clipboard.GTKLib;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;

public class LinuxAccessor extends AbstractOperatingSystemAccessor
{
    @Override
    public void copyToClipboard(final GameImage image) {
        final GTKLib lib = GTKLib.INSTANCE;
        lib.gtk_init(0, null);
        final Pointer display = lib.gdk_display_get_default();
        final Pointer clipboard = lib.gtk_clipboard_get_default(display);
        final Pointer pixelBuf = GTKLib.toPixelBuf(image);
        lib.gtk_clipboard_set_image(clipboard, pixelBuf);
        lib.gdk_pixbuf_unref(pixelBuf);
    }
    
    @Override
    public GameImage getImageInClipboard() {
        final GTKLib lib = GTKLib.INSTANCE;
        lib.gtk_init(0, null);
        final Pointer display = lib.gdk_display_get_default();
        final Pointer clipboard = lib.gtk_clipboard_get_default(display);
        final Pointer pixelBuf = lib.gtk_clipboard_wait_for_image(clipboard);
        if (pixelBuf == null) {
            return null;
        }
        return GTKLib.fromPixelBuf(pixelBuf);
    }
    
    @Override
    public CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException {
        return (CredentialsAccessor)new LinuxCredentialsAccessor();
    }
}
