// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.options.MinecraftInputMapping;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ayi.b.class })
public class MixinGuiKeybindingListKeyEntry
{
    @Redirect(method = { "drawEntry" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;getKeyCode()I"))
    private int labyMod$removeRedColor(final avb instance) {
        return MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping)instance) ? Integer.MIN_VALUE : instance.i();
    }
}
