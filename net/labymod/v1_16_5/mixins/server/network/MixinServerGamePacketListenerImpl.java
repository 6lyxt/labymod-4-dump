// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.server.network;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aay.class })
public class MixinServerGamePacketListenerImpl
{
    @Shadow
    public aah b;
    
    @Insert(method = { "onDisconnect" }, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(final nr reason, final InsertInfo ci) {
        final GameProfile profile = (GameProfile)this.b.eA();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }
    
    @Insert(method = { "onDisconnect" }, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(final nr reason, final InsertInfo ci) {
        final GameProfile profile = (GameProfile)this.b.eA();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
