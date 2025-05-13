// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.cosmetics;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.entity.player.DamageBlockedEventCaller;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ epw.class })
public class MixinLocalPlayerEvents extends ept
{
    public MixinLocalPlayerEvents(final ems $$0, final GameProfile $$1) {
        super($$0, $$1);
    }
    
    @Inject(method = { "hurt" }, at = { @At("HEAD") })
    private void labyMod$blockAttack(final awu source, final float damageValue, final CallbackInfoReturnable<Boolean> cir) {
        if (this.s.x && damageValue > 0.0f && this.e(source)) {
            DamageBlockedEventCaller.fireDamageBlocked((Player)this, damageValue);
        }
    }
}
