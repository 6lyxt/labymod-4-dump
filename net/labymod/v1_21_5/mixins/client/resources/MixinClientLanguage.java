// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ hlw.class })
public class MixinClientLanguage
{
    @Inject(method = { "getOrDefault" }, at = { @At("HEAD") }, cancellable = true)
    public void getOrDefault(final String key, final String fallback, final CallbackInfoReturnable<String> cir) {
        final String translation = Laby.labyAPI().internationalization().getRawTranslation(key);
        if (!translation.equals(key)) {
            cir.setReturnValue((Object)translation);
        }
    }
    
    @Inject(method = { "has" }, at = { @At("HEAD") }, cancellable = true)
    public void has(final String key, final CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().internationalization().has(key)) {
            cir.setReturnValue((Object)true);
        }
    }
}
