// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MinecraftMinecraftThreadSafe
{
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$setScreenThreadSafe(final blk screen, final CallbackInfo ci) {
        if (!ThreadSafe.isRenderThread()) {
            ThreadSafe.executeOnRenderThread(() -> bib.z().a(screen));
            ci.cancel();
        }
    }
}
