// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.accessor;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;

public interface FileDropHandler
{
    boolean handleDroppedFiles(final MutableMouse p0, final List<Path> p1);
}
