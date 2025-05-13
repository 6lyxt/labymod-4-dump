// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fcy.class })
public class MixinCreateWorldScreen extends eyk
{
    @Shadow
    private eus F;
    
    protected MixinCreateWorldScreen(final tl $$0) {
        super($$0);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER) }, cancellable = true)
    public void labyMod$cancelFooterRender(final esf $$0, final int $$1, final int $$2, final float $$3, final CallbackInfo ci) {
        super.a($$0, $$1, $$2, $$3);
        ci.cancel();
    }
}
