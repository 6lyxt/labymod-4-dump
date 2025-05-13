// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_21_4.client.gui.IntRangeBaseSliderableValueSet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.function.ToIntFunction;
import java.util.function.IntFunction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fln.g.class })
public interface MixinIntRangeBase
{
    @Inject(method = { "xmap" }, at = { @At("HEAD") }, cancellable = true)
    default <R> void labyMod$replaceInnerClass(final IntFunction<? extends R> intFunction, final ToIntFunction<? super R> mapperFunction, final CallbackInfoReturnable<fln.k<R>> cir) {
        cir.setReturnValue((Object)new IntRangeBaseSliderableValueSet((fln.g)this, intFunction, (ToIntFunction<? super Object>)mapperFunction));
    }
}
