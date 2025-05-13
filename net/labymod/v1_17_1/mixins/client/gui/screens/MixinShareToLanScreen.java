// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_17_1.client.gui.screen.CompletableShareToLanScreen;

@Mixin({ ear.class })
public class MixinShareToLanScreen implements CompletableShareToLanScreen
{
    private Consumer<Boolean> labyMod$finishHandler;
    
    @Insert(method = { "lambda$init$2(Lnet/minecraft/client/gui/components/Button;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/TranslatableComponent;<init>(Ljava/lang/String;[Ljava/lang/Object;)V"))
    private void labyMod$handleLanWorldOpenSuccess(final dxa button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(true);
        }
    }
    
    @Insert(method = { "lambda$init$2(Lnet/minecraft/client/gui/components/Button;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/TranslatableComponent;<init>(Ljava/lang/String;)V"))
    private void labyMod$handleLanWorldOpenFail(final dxa button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(false);
        }
    }
    
    @Override
    public void setFinishHandler(final Consumer<Boolean> finishHandler) {
        this.labyMod$finishHandler = finishHandler;
    }
}
