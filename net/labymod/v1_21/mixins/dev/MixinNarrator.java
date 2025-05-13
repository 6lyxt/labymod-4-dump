// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.dev;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.text2speech.Narrator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Narrator.class })
public interface MixinNarrator
{
    @Shadow
    @Final
    public static final Narrator EMPTY = null;
    
    @Inject(method = { "getNarrator()Lcom/mojang/text2speech/Narrator;" }, at = { @At("HEAD") }, remap = false, cancellable = true)
    default void getNarrator(final CallbackInfoReturnable<Narrator> cir) {
        if (Laby.labyAPI().labyModLoader().isDevelopmentEnvironment() && OperatingSystem.getPlatform() == OperatingSystem.LINUX) {
            cir.setReturnValue((Object)MixinNarrator.EMPTY);
        }
    }
}
