// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.server.network;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aji.class })
public class MixinServerGamePacketListenerImpl
{
    @Shadow
    public aiq b;
    
    @Insert(method = { "onDisconnect" }, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(final tj reason, final InsertInfo ci) {
        final GameProfile profile = (GameProfile)this.b.fI();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }
    
    @Insert(method = { "onDisconnect" }, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(final tj reason, final InsertInfo ci) {
        final GameProfile profile = (GameProfile)this.b.fI();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
