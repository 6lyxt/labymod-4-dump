// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render.world;

import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;

public final class RenderWorldEventCaller
{
    public static void call(final Stack stack, final float partialTicks) {
        final MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        Laby.fireEvent(new RenderWorldEvent(stack, camera, partialTicks));
        gfx.restoreBlaze3DStates();
    }
}
