// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.client.component.VersionedKeybindComponent;
import net.labymod.v1_8_9.client.component.VersionedIconComponent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ev.class })
public class MixinChatComponentProcessor
{
    @Inject(method = { "processComponent" }, at = { @At("HEAD") }, cancellable = true)
    private static void labyMod$processIconComponent(final m sender, final eu component, final pk entity, final CallbackInfoReturnable<eu> cir) {
        if (component instanceof VersionedIconComponent || component instanceof VersionedKeybindComponent) {
            cir.setReturnValue((Object)component);
        }
    }
}
