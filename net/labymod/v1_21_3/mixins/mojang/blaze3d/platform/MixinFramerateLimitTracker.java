// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.mojang.blaze3d.platform;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ffj.class })
public class MixinFramerateLimitTracker
{
    @Insert(method = { "getFramerateLimit" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$getFramerateLimit(final InsertInfoReturnable<Integer> iir) {
        GameFpsLimitEventCaller.callEvent(iir);
    }
}
