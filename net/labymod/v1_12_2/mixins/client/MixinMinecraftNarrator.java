// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bib.class })
public class MixinMinecraftNarrator
{
    @Redirect(method = { "dispatchKeypresses" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"))
    private void labyMod$disableNarrator(final bid settings, final bid.a options, int value) {
        if (Laby.labyAPI().config().hotkeys().disableNarratorHotkey().get()) {
            return;
        }
        settings.a(options, value);
    }
}
