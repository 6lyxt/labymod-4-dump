// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os;

import net.labymod.core.client.os.linux.LinuxAccessor;
import net.labymod.core.client.os.unix.MacOSAccessor;
import net.labymod.core.client.os.windows.WindowsAccessor;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.client.os.OperatingSystemAccessorFactory;

public class DefaultOperatingSystemAccessorFactory implements OperatingSystemAccessorFactory
{
    @Override
    public OperatingSystemAccessor createAccessor() {
        return switch (OperatingSystem.getPlatform()) {
            case WINDOWS -> new WindowsAccessor();
            case MACOS -> new MacOSAccessor();
            case LINUX -> new LinuxAccessor();
            default -> null;
        };
    }
}
