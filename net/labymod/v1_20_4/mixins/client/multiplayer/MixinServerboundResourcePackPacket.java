// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.multiplayer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ xv.class })
public class MixinServerboundResourcePackPacket
{
    @Inject(method = { "<init>(Ljava/util/UUID;Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket$Action;)V" }, at = { @At("TAIL") })
    private void labyMod$sendServerResourcePackResponse(final UUID id, final xv.a action, final CallbackInfo ci) {
        if (action == xv.a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
