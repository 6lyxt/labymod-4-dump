// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme.renderer.background;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;

public interface BackgroundRenderer
{
    boolean renderBackground(final Stack p0, final Object p1);
    
    @Nullable
    default ResourceLocation getBackgroundLocation() {
        return null;
    }
}
