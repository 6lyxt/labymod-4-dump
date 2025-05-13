// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true, since = "4.2.13")
public interface CanvasRendererFactory
{
    @Nullable
    CanvasRenderer createRenderer(@NotNull final CanvasMeta p0);
}
