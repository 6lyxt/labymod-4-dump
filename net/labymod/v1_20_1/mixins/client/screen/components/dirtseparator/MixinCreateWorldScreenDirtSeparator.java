// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.screen.components.dirtseparator;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eza.class })
public class MixinCreateWorldScreenDirtSeparator extends euq
{
    protected MixinCreateWorldScreenDirtSeparator(final sw $$0) {
        super($$0);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$skipDirtSeparator(final eox $$0, final int $$1, final int $$2, final float $$3, final CallbackInfo ci) {
        final Theme currentTheme = Laby.references().themeService().currentTheme();
        if (!currentTheme.metadata().get("show_dirt_separator", true)) {
            ci.cancel();
            super.a($$0, $$1, $$2, $$3);
        }
    }
}
