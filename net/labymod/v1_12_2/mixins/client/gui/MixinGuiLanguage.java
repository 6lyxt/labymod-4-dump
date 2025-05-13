// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.localization.InternationalizationReloader;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.client.gui.GuiLanguage$List" })
public class MixinGuiLanguage
{
    @WrapWithCondition(method = { "elementClicked" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;refreshResources()V") })
    private boolean labyMod$fasterLanguageReload(final bib minecraft) {
        final boolean fastLanguageReload = Laby.labyAPI().config().ingame().fastLanguageReload().get();
        if (fastLanguageReload) {
            minecraft.Q().a(minecraft.O());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
