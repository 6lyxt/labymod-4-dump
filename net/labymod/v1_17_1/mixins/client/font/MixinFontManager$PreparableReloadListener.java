// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.font;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.client.gui.font.FontManager$1" })
public class MixinFontManager$PreparableReloadListener
{
    @Inject(method = { "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V" }, at = { @At("TAIL") })
    private void labyMod$onFontApply(final Map<ww, List<doq>> param0, final adt param1, final apv param2, final CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
