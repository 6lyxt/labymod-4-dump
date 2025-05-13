// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows;

import net.labymod.core.client.os.windows.credentials.WindowsCredentialsAccessor;
import net.labymod.api.models.OperatingSystem;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.core.client.os.windows.clipboard.WinGDI32;
import net.labymod.core.client.os.windows.clipboard.WinUser;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;

public class WindowsAccessor extends AbstractOperatingSystemAccessor
{
    @Override
    public void copyToClipboard(final GameImage image) {
        final WinUser USER_32 = WinUser.INSTANCE;
        USER_32.OpenClipboard(0);
        USER_32.EmptyClipboard();
        USER_32.SetClipboardData(2, WinGDI32.toBitMap(image));
        USER_32.CloseClipboard();
    }
    
    @Override
    public GameImage getImageInClipboard() {
        GameImage image = null;
        final WinUser USER_32 = WinUser.INSTANCE;
        USER_32.OpenClipboard(0);
        if (USER_32.IsClipboardFormatAvailable(2)) {
            try {
                image = WinGDI32.fromBitMap(USER_32.GetClipboardData(2));
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        USER_32.CloseClipboard();
        return image;
    }
    
    @Override
    public CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException {
        if (OperatingSystem.is64Bit()) {
            return (CredentialsAccessor)new WindowsCredentialsAccessor();
        }
        throw new UnsupportedOperationException("Windows 32-bit is not supported");
    }
}
