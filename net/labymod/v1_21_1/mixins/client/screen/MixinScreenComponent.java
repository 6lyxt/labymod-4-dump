// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.slf4j.Logger;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fod.class })
public class MixinScreenComponent
{
    @Shadow
    @Nullable
    protected fgo l;
    @Shadow
    @Final
    private static Logger a;
    
    @Inject(method = { "handleComponentClicked" }, cancellable = true, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/StringUtil;filterText(Ljava/lang/String;)Ljava/lang/String;", ordinal = 1, shift = At.Shift.BEFORE) })
    public void labyMod$handleRunCommand(final xw style, final CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((Object)true);
        String message = style.h().b();
        final ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(message, false));
        if (event.isCancelled()) {
            return;
        }
        message = event.getMessage();
        if (message == null || message.isEmpty()) {
            return;
        }
        message = azl.g(message);
        if (message.startsWith("/")) {
            if (!this.l.s.h.d(message.substring(1))) {
                MixinScreenComponent.a.error("Not allowed to run command with signed argument from click event: '{}'", (Object)message);
            }
        }
        else {
            MixinScreenComponent.a.error("Failed to run command without '/' prefix from click event: '{}'", (Object)message);
        }
    }
}
