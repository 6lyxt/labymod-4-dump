// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.canvas;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface CanvasFactory
{
    @Nullable
    Canvas createCanvas(@NotNull final CanvasMeta p0, @NotNull final SignObjectPosition p1);
}
