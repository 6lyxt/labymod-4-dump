// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Deprecated(forRemoval = true, since = "4.2.13")
@Referenceable
public interface CanvasRegistry
{
    void registerRenderer(@NotNull final ResourceLocation p0, @NotNull final CanvasRendererFactory p1);
    
    void registerActivity(@NotNull final ResourceLocation p0, @NotNull final Supplier<Activity> p1);
}
