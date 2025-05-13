// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ aye.class })
public class MixinGuiAchievements extends axu
{
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/achievement/GuiAchievements;drawDefaultBackground()V", shift = At.Shift.AFTER) })
    private void labyMod$drawScreen(final int lvt_1_1_, final int lvt_2_1_, final float lvt_3_1_, final CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            bfl.f();
            bfl.n();
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            avp.a(0, 0, this.l, this.m, ColorFormat.ARGB32.pack(0.0f, 0.0f, 0.0f, 0.105f));
        }
    }
}
