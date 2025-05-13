// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dzk.class })
public class MixinInput
{
    @Shadow
    public float b;
    
    @Insert(method = { "hasForwardImpulse()Z" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$oldSlowdown(final InsertInfoReturnable<Boolean> returnable) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final SlowdownOldAnimation animation = oldAnimationRegistry.get("slowdown");
        if (animation == null) {
            return;
        }
        if (animation.isEnabled()) {
            returnable.setReturnValue(animation.isEnabled(this.b));
        }
    }
}
