// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.resources.VersionedLocale;

@Mixin({ cfb.class })
public abstract class MixinLocale implements VersionedLocale
{
    @Shadow
    Map<String, String> a;
    
    @Shadow
    protected abstract String b(final String p0);
    
    @Inject(method = { "translateKeyPrivate" }, at = { @At("HEAD") }, cancellable = true)
    public void injectLabyTranslations(final String translationKey, final CallbackInfoReturnable<String> cir) {
        final String translation = Laby.labyAPI().internationalization().getRawTranslation(translationKey);
        if (!translation.equals(translationKey)) {
            cir.setReturnValue((Object)translation);
        }
    }
    
    @Override
    public String getTranslation(final String translationKey) {
        return this.b(translationKey);
    }
    
    @Override
    public boolean hasTranslation(final String translationKey) {
        return this.a.containsKey(translationKey) || Laby.labyAPI().internationalization().has(translationKey);
    }
}
