// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.localization.InternationalizationReloader;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eac.class })
public class MixinLanguageSelectScreen
{
    @Shadow
    @Final
    eyj o;
    
    @WrapWithCondition(method = { "lambda$init$0" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;") })
    private boolean labyMod$fasterLanguageReload(final dvp instance) {
        final boolean fastLanguageReload = Laby.labyAPI().config().ingame().fastLanguageReload().get();
        if (fastLanguageReload) {
            this.o.a(instance.N());
            InternationalizationReloader.reload();
        }
        return !fastLanguageReload;
    }
}
