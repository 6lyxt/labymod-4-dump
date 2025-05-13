// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.canvas.CanvasFactory;

@Singleton
@Implements(CanvasFactory.class)
public class DefaultCanvasFactory implements CanvasFactory
{
    @Nullable
    @Override
    public Canvas createCanvas(@NotNull final CanvasMeta meta, @NotNull final SignObjectPosition position) {
        return new DefaultCanvas(meta, position);
    }
}
