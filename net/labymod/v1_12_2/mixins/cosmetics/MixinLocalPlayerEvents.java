// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.cosmetics;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.entity.player.DamageBlockedEventCaller;
import net.labymod.api.client.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bud.class })
public class MixinLocalPlayerEvents extends bua
{
    public MixinLocalPlayerEvents(final amu lvt_1_1_, final GameProfile lvt_2_1_) {
        super(lvt_1_1_, lvt_2_1_);
    }
    
    @Inject(method = { "attackEntityFrom" }, at = { @At("HEAD") })
    private void labyMod$blockAttack(final ur source, final float damageValue, final CallbackInfoReturnable<Boolean> cir) {
        if (!this.l.G && damageValue > 0.0f && this.f(source)) {
            DamageBlockedEventCaller.fireDamageBlocked((Player)this, damageValue);
        }
    }
}
