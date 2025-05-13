// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.input;

import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_8_9.client.input.InputHandler;
import net.labymod.api.volt.callback.JumpStatement;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.v1_8_9.client.VersionedMinecraft;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ axu.class })
public abstract class MixinGuiScreenInput
{
    @Shadow
    protected ave j;
    
    @Insert(method = { "handleInput" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleMouseInput()V", shift = At.Shift.BEFORE), jumpStatement = JumpStatement.BREAK)
    private void labyMod$dispatchMouseInput(final InsertInfo ii) {
        if (((VersionedMinecraft)this.j).dispatchMouseInput()) {
            ii.jump();
        }
    }
    
    @Insert(method = { "handleInput" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleMouseInput()V", shift = At.Shift.AFTER))
    private void labyMod$fireMouseInput(final InsertInfo ii) {
        InputHandler.fireMouseInput(true);
    }
    
    @Insert(method = { "handleInput" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleKeyboardInput()V", shift = At.Shift.BEFORE), jumpStatement = JumpStatement.CONTINUE)
    private void labyMod$dispatchKeyboardInput(final InsertInfo ii) {
        final Object instance = this;
        final LabyScreenRenderer screenRenderer = (instance instanceof LabyScreenRenderer) ? ((LabyScreenRenderer)instance) : null;
        if (((VersionedMinecraft)this.j).dispatchKeyboardInput(screenRenderer)) {
            ii.jump();
        }
    }
}
