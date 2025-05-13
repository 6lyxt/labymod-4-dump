// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.key;

import net.labymod.api.models.OperatingSystem;

public final class MacOSMouse
{
    public static double fixMouseScroll(final double value, final long windowHandle, final double horizontal, final double vertical) {
        if (!OperatingSystem.isOSX()) {
            return value;
        }
        return (value == 0.0) ? horizontal : value;
    }
}
