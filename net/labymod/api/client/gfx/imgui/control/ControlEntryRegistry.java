// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui.control;

import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import java.util.function.Function;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

@Referenceable
public interface ControlEntryRegistry extends Registry<ControlEntry>
{
    void registerEntry(final boolean p0, final Function<ImGuiBooleanType, ImGuiWindow> p1);
    
    default boolean isVisible(final String id) {
        if (!DebugRegistry.DEBUG_WINDOWS.isEnabled()) {
            return false;
        }
        final ControlEntry controlEntry = this.getById(id);
        return controlEntry == null || controlEntry.getVisible().get();
    }
}
