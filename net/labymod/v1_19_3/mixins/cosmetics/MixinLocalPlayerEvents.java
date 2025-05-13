// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.cosmetics;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.entity.player.DamageBlockedEventCaller;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fcz.class })
public class MixinLocalPlayerEvents extends fcw
{
    public MixinLocalPlayerEvents(final eyz $$0, final GameProfile $$1) {
        super($$0, $$1);
    }
    
    @Inject(method = { "hurt" }, at = { @At("HEAD") })
    private void labyMod$blockAttack(final bcz source, final float damageValue, final CallbackInfoReturnable<Boolean> cir) {
        if (this.s.y && damageValue > 0.0f && this.e(source)) {
            DamageBlockedEventCaller.fireDamageBlocked((Player)this, damageValue);
        }
    }
}
