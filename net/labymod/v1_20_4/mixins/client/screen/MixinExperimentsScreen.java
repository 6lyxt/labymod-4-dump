// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fhu.class })
public class MixinExperimentsScreen
{
    @Redirect(method = { "renderBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void labyMod$disableDirtBackground(final ewu instance, final ahg $$0, final int $$1, final int $$2, final float $$3, final float $$4, final int $$5, final int $$6, final int $$7, final int $$8) {
        final ThemeService themeService = Laby.references().themeService();
        final Theme theme = themeService.currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            instance.a($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
        }
    }
}
