// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fu.class })
public abstract class MixinLanguageMap
{
    @Inject(method = { "tryTranslateKey" }, at = { @At("HEAD") }, cancellable = true)
    public void injectLabyTranslations(final String translationKey, final CallbackInfoReturnable<String> cir) {
        if (Laby.labyAPI() == null || Laby.labyAPI().internationalization() == null) {
            return;
        }
        final String translation = Laby.labyAPI().internationalization().getRawTranslation(translationKey);
        if (!translation.equals(translationKey)) {
            cir.setReturnValue((Object)translation);
        }
    }
    
    @Inject(method = { "isKeyTranslated" }, at = { @At("HEAD") }, cancellable = true)
    public void has(final String key, final CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI() == null || Laby.labyAPI().internationalization() == null) {
            return;
        }
        if (Laby.labyAPI().internationalization().has(key)) {
            cir.setReturnValue((Object)true);
        }
    }
}
