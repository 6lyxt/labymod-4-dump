// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.input;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import org.lwjgl.input.Keyboard;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public class MixinModernKeybinding
{
    @Shadow
    public avh t;
    
    @Inject(method = { "setIngameFocus" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/MouseHelper;grabMouseCursor()V", shift = At.Shift.BEFORE) })
    private void labyMod$restoreKeybindingStates(final CallbackInfo ci) {
        final LabyConfig config = Laby.labyAPI().config();
        if (config == null) {
            return;
        }
        if (!config.hotkeys().modernKeybinding().get()) {
            return;
        }
        final avb[] ax;
        final avb[] keyBindings = ax = this.t.ax;
        for (final avb keybinding : ax) {
            try {
                final int keyCode = keybinding.i();
                if (keyCode >= 0) {
                    if (keyCode > 0 && keyCode < 256) {
                        final boolean keyDown = Keyboard.isKeyDown(keyCode);
                        avb.a(keyCode, keyDown);
                    }
                }
            }
            catch (final IndexOutOfBoundsException ex) {}
        }
    }
}
