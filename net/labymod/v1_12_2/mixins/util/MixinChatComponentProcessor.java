// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.component.VersionedKeybindComponent;
import net.labymod.v1_12_2.client.component.VersionedIconComponent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ hi.class })
public class MixinChatComponentProcessor
{
    @Inject(method = { "processComponent" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$processIconComponent(final bn sender, final hh component, final vg entity, final CallbackInfoReturnable<hh> cir) {
        if (component instanceof VersionedIconComponent || component instanceof VersionedKeybindComponent) {
            cir.setReturnValue((Object)component);
        }
    }
}
