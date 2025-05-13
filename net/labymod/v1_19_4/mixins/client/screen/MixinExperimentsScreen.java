// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ exq.class })
public class MixinExperimentsScreen
{
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/ExperimentsScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIFFIIII)V"))
    private void labyMod$disableDirtBackground(final ehe poseStack, final int i0, final int i1, final float v2, final float v3, final int i4, final int i5, final int i6, final int i7) {
        final ThemeService themeService = Laby.references().themeService();
        final Theme theme = themeService.currentTheme();
        if (!theme.metadata().getBoolean("hide_default_background")) {
            enq.a(poseStack, i0, i1, v2, v3, i4, i5, i6, i7);
        }
    }
}
