// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.font.text.AdvancedFontRenderer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public class MixinMinecraftAdvancedFontRenderer
{
    @Shadow
    public avn k;
    @Shadow
    public avh t;
    @Shadow
    private bmj R;
    
    @Inject(method = { "startGame" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;standardGalacticFontRenderer:Lnet/minecraft/client/gui/FontRenderer;", ordinal = 0, shift = At.Shift.BEFORE) })
    private void labyMod$overwriteFontRenderer(final CallbackInfo ci) {
        this.k = new AdvancedFontRenderer(this.t, new jy("textures/font/ascii.png"), this.R, false);
    }
}
