// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import net.labymod.api.util.version.VersionMultiRange;
import java.util.function.BooleanSupplier;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.models.version.VersionCompatibility;

public interface MouseUser
{
    public static final VersionCompatibility FILE_DROP_COMPATIBILITY = new VersionMultiRange("1.17<*");
    
    boolean mouseReleased(final MutableMouse p0, final MouseButton p1);
    
    boolean mouseClicked(final MutableMouse p0, final MouseButton p1);
    
    boolean mouseScrolled(final MutableMouse p0, final double p1);
    
    boolean mouseDragged(final MutableMouse p0, final MouseButton p1, final double p2, final double p3);
    
    boolean fileDropped(final MutableMouse p0, final List<Path> p1);
    
    default boolean transformMouse(final MutableMouse mouse, final BooleanSupplier handler) {
        return handler.getAsBoolean();
    }
}
