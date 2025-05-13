// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = { "net.optifine.gui.TooltipManager" })
public class MixinOptiFineTooltipManager
{
    @Inject(method = { "drawTooltips" }, at = { @At("HEAD") }, remap = false)
    @Dynamic
    private void labyMod$preTranslateTooltip(final eed matrixStackIn, final int x, final int y, final List<ekw> buttonList, final CallbackInfo ci) {
        matrixStackIn.a();
        matrixStackIn.a(0.0f, 0.0f, 100.0f);
    }
    
    @Inject(method = { "drawTooltips" }, at = { @At("TAIL") }, remap = false)
    @Dynamic
    private void labyMod$postTranslateTooltip(final eed matrixStackIn, final int x, final int y, final List<ekw> buttonList, final CallbackInfo ci) {
        matrixStackIn.b();
    }
}
