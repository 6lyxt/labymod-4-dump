// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bnf.class })
public class MixinGuiMultiplayer
{
    @Shadow
    private bnj h;
    
    @Inject(method = { "initGui()V" }, at = { @At("RETURN") })
    protected void init(final CallbackInfo ci) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.h.a(window.getScaledWidth(), window.getScaledHeight(), 32, window.getScaledHeight() - 64);
    }
    
    @Redirect(method = { "drawScreen(IIF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"))
    public void clearTitle(final bnf guiMultiplayer, final bip p_drawCenteredString_1_, final String p_drawCenteredString_2_, final int p_drawCenteredString_3_, final int p_drawCenteredString_4_, final int p_drawCenteredString_5_) {
    }
}
