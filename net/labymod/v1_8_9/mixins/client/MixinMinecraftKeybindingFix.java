// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public class MixinMinecraftKeybindingFix
{
    @Inject(method = { "displayGuiScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setIngameNotInFocus()V", shift = At.Shift.AFTER) })
    private void labyMod$unpressKeybinding(final axu screen, final CallbackInfo ci) {
        avb.a();
        while (Mouse.next()) {}
        while (Keyboard.next()) {}
    }
}
