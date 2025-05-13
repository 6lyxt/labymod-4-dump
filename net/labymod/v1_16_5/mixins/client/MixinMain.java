// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.models.OperatingSystem;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Main.class })
public class MixinMain
{
    @Redirect(method = { "<clinit>" }, at = @At(value = "INVOKE", target = "Ljava/lang/System;setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String labyMod$test(final String key, String value) {
        if (OperatingSystem.isOSX()) {
            return System.setProperty(key, "false");
        }
        return System.setProperty(key, value);
    }
}
