// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import java.util.function.IntSupplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fnt.class })
public class MixinLoadingOverlayDarkBackground
{
    @Shadow
    @Final
    private fgo m;
    @Shadow
    @Final
    private static int e;
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"), require = 0)
    private int labyMod$darkModeSetting(final IntSupplier supplier) {
        return Laby.labyAPI().config().appearance().darkLoadingScreen().get() ? MixinLoadingOverlayDarkBackground.e : supplier.getAsInt();
    }
}
