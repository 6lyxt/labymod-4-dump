// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.debug;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ejs.class })
public class MixinDynamicTextureNullCheck
{
    @Inject(method = { "<init>(Lcom/mojang/blaze3d/platform/NativeImage;)V" }, at = { @At("TAIL") })
    private void labyMod$nullCheck(final det image, final CallbackInfo ci) {
        if (image == null) {
            throw new NullPointerException("pixels is null");
        }
    }
}
