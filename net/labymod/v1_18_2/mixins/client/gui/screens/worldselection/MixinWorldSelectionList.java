// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.screens.worldselection;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ehv.class })
public class MixinWorldSelectionList
{
    @Redirect(method = { "refreshList" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1))
    public void labyMod$openCreateWorldScreenNextTick(final dyr minecraft, final edw screen) {
        minecraft.i(() -> minecraft.a(screen));
    }
}
