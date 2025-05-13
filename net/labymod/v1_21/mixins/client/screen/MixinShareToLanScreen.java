// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21.client.gui.screen.CompletableShareToLanScreen;

@Mixin({ foe.class })
public class MixinShareToLanScreen implements CompletableShareToLanScreen
{
    private Consumer<Boolean> labyMod$finishHandler;
    
    @Insert(method = { "lambda$init$2(Lnet/minecraft/client/server/IntegratedServer;Lnet/minecraft/client/gui/components/Button;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/PublishCommand;getSuccessMessage(I)Lnet/minecraft/network/chat/MutableComponent;"))
    private void labyMod$handleLanWorldOpenSuccess(final guo server, final fim button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(true);
        }
    }
    
    @Insert(method = { "lambda$init$2(Lnet/minecraft/client/server/IntegratedServer;Lnet/minecraft/client/gui/components/Button;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
    private void labyMod$handleLanWorldOpenFail(final guo server, final fim button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(false);
        }
    }
    
    @Override
    public void setFinishHandler(final Consumer<Boolean> finishHandler) {
        this.labyMod$finishHandler = finishHandler;
    }
}
