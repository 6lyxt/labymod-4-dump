// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.resources.VersionedI18n;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bnq.class })
public class MixinI18n
{
    @Inject(method = { "setLocale" }, at = { @At("TAIL") })
    private static void setLocale(final bnt locale, final CallbackInfo callback) {
        VersionedI18n.setLocale(locale);
    }
}
