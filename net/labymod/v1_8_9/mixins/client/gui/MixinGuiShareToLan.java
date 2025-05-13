// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.gui.CompletableGuiShareToLan;

@Mixin({ axw.class })
public class MixinGuiShareToLan implements CompletableGuiShareToLan
{
    private Consumer<Boolean> labyMod$finishHandler;
    
    @Insert(method = { "actionPerformed" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatComponentTranslation;<init>(Ljava/lang/String;[Ljava/lang/Object;)V"))
    private void labyMod$handleLanWorldOpenSuccess(final avs button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(true);
        }
    }
    
    @Insert(method = { "actionPerformed" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatComponentText;<init>(Ljava/lang/String;)V"))
    private void labyMod$handleLanWorldOpenFail(final avs button, final InsertInfo ci) {
        if (this.labyMod$finishHandler != null) {
            this.labyMod$finishHandler.accept(false);
        }
    }
    
    @Override
    public void setFinishHandler(final Consumer<Boolean> finishHandler) {
        this.labyMod$finishHandler = finishHandler;
    }
}
