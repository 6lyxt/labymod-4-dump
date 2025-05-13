// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ClientBrandRetriever.class })
public class MixinClientBrandRetriever
{
    @Inject(method = { "getClientModName" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$setLabyModBrand(final CallbackInfoReturnable<String> cir) {
        cir.setReturnValue((Object)LabyMod.getClientBrand());
    }
}
