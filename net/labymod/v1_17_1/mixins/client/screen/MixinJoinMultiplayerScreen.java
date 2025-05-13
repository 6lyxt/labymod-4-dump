// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.screen;

import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ edc.class })
public class MixinJoinMultiplayerScreen
{
    @Redirect(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    public void clearTitle(final dql param0, final dwl param1, final os param2, final int param3, final int param4, final int param5) {
    }
    
    @Insert(method = { "join(Lnet/minecraft/client/multiplayer/ServerData;)V" }, at = @At("TAIL"))
    private void getServerData(final ejn serverData, final InsertInfo ci) {
        Laby.labyAPI().serverController().loginOrServerSwitch(this.labyMod$getServerController().createServerData(serverData));
    }
    
    private ServerController labyMod$getServerController() {
        return Laby.labyAPI().serverController();
    }
}
