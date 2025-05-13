// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.font.text.AdvancedFontRenderer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MixinMinecraftAdvancedFontRenderer
{
    @Shadow
    public bip k;
    @Shadow
    public bid t;
    @Shadow
    private cdr Q;
    
    @Inject(method = { "init" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;standardGalacticFontRenderer:Lnet/minecraft/client/gui/FontRenderer;", ordinal = 0, shift = At.Shift.BEFORE) })
    private void labyMod$overwriteFontRenderer(final CallbackInfo ci) {
        this.k = new AdvancedFontRenderer(this.t, new nf("textures/font/ascii.png"), this.Q, false);
    }
}
