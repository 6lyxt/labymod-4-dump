// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

public interface LabyScreenRendererAccessor extends FileDropHandler
{
    boolean wrappedMouseClicked(final MutableMouse p0, final int p1);
    
    boolean wrappedMouseReleased(final MutableMouse p0, final int p1);
    
    boolean wrappedKeyPressed(final Key p0, final InputType p1);
    
    boolean wrappedKeyReleased(final Key p0, final InputType p1);
    
    boolean wrappedCharTyped(final Key p0, final char p1);
    
    boolean mouseScrolled(final MutableMouse p0, final double p1);
    
    boolean wrappedMouseClickMove(final MutableMouse p0, final int p1, final double p2, final double p3);
    
    default boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        return false;
    }
}
