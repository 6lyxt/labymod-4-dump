// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.localization.InternationalizationReloader;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fxm.class })
public class MixinLanguageSelectScreen
{
    @Shadow
    @Final
    hgd w;
    
    @WrapWithCondition(method = { "onDone" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;") })
    private boolean labyMod$fasterLanguageReload(final flk instance) {
        final boolean fastLanguageReload = Laby.labyAPI().config().ingame().fastLanguageReload().get();
        if (fastLanguageReload) {
            this.w.a(instance.ac());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
