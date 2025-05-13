// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_5.client.gui.IntRangeBaseSliderableValueSet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin("optifine")
@Mixin({ fqt.class })
public class MixinOptiFineOptionInstance<T>
{
    @Shadow
    @Final
    private fqt.n<T> f;
    
    @Inject(method = { "getIntRangeBase()Lnet/minecraft/client/OptionInstance$IntRangeBase;" }, at = { @At("HEAD") }, cancellable = true)
    @Dynamic
    private void labyMod$fixOptiFine(final CallbackInfoReturnable<fqt.g> cir) {
        final fqt.n<T> f = this.f;
        if (f instanceof IntRangeBaseSliderableValueSet) {
            final IntRangeBaseSliderableValueSet<?> set = (IntRangeBaseSliderableValueSet<?>)f;
            cir.setReturnValue((Object)set.base());
        }
    }
}
