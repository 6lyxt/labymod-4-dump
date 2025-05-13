// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher.link;

import org.jetbrains.annotations.Nullable;
import net.labymod.core.main.quicklauncher.link.implementation.UnixLink;
import net.labymod.core.main.quicklauncher.link.implementation.MacOSLink;
import net.labymod.core.main.quicklauncher.link.implementation.WindowsLink;
import net.labymod.api.models.OperatingSystem;

public class LinkFactory
{
    public static boolean isSupported(final OperatingSystem operatingSystem) {
        return create(operatingSystem) != null;
    }
    
    @Nullable
    public static Link create(final OperatingSystem operatingSystem) {
        switch (operatingSystem) {
            case WINDOWS: {
                return new WindowsLink();
            }
            case MACOS: {
                return new MacOSLink();
            }
            case LINUX: {
                return new UnixLink();
            }
            default: {
                return null;
            }
        }
    }
}
