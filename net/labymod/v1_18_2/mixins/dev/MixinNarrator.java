// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.dev;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.text2speech.NarratorDummy;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.text2speech.Narrator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Narrator.class })
public interface MixinNarrator
{
    @Inject(method = { "getNarrator()Lcom/mojang/text2speech/Narrator;" }, at = { @At("HEAD") }, remap = false, cancellable = true)
    default void getNarrator(final CallbackInfoReturnable<Narrator> cir) {
        if (Laby.labyAPI().labyModLoader().isDevelopmentEnvironment() && OperatingSystem.getPlatform() == OperatingSystem.LINUX) {
            cir.setReturnValue((Object)new NarratorDummy());
        }
    }
}
