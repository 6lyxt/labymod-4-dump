// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin("optifine")
@Mixin({ avh.class })
public class MixinOptiFineGameSettings
{
    @Redirect(method = { "loadOptions" }, at = @At(value = "FIELD", opcode = 180, ordinal = 0, target = "Lnet/minecraft/client/settings/GameSettings;enableVsync:Z"))
    @Dynamic
    public boolean labyMod$fixFpsLimit(final avh settings) {
        return false;
    }
}
