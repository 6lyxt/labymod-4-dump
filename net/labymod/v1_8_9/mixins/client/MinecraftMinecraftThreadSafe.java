// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public class MinecraftMinecraftThreadSafe
{
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$setScreenThreadSafe(final axu screen, final CallbackInfo ci) {
        if (!ThreadSafe.isRenderThread()) {
            ThreadSafe.executeOnRenderThread(() -> ave.A().a(screen));
            ci.cancel();
        }
    }
}
