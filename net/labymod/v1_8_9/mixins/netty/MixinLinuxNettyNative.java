// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.netty;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "io.netty.channel.epoll.Native" })
public class MixinLinuxNettyNative
{
    @Inject(method = { "<clinit>" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$disableNettyTransportNativeEpoll(final CallbackInfo ci) {
        ci.cancel();
    }
}
