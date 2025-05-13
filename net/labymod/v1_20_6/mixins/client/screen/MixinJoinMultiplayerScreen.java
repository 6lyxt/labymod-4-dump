// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.screen;

import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqd.class })
public class MixinJoinMultiplayerScreen
{
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    public void clearTitle(final fgt param0, final fgr param1, final xp param2, final int param3, final int param4, final int param5) {
    }
    
    @Insert(method = { "join(Lnet/minecraft/client/multiplayer/ServerData;)V" }, at = @At("TAIL"))
    private void getServerData(final fyl serverData, final InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(this.labyMod$getServerController().createServerData(serverData));
    }
    
    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
