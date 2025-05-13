// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.cosmetics;

import net.labymod.core.event.client.entity.player.DamageBlockedEventCaller;
import net.labymod.api.client.entity.player.Player;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gkh.class })
public class MixinLocalPlayerEvents extends gke
{
    public MixinLocalPlayerEvents(final gfk $$0, final GameProfile $$1) {
        super($$0, $$1);
    }
    
    protected void b(final ash level, final bua damageSource, final float damageValue) {
        super.b(level, damageSource, damageValue);
        if (damageValue > 0.0f && this.g(damageSource)) {
            DamageBlockedEventCaller.fireDamageBlocked((Player)this, damageValue);
        }
    }
}
