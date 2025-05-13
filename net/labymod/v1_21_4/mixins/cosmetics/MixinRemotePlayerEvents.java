// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.cosmetics;

import net.labymod.core.event.client.entity.player.DamageBlockedEventCaller;
import net.labymod.api.client.entity.player.Player;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gky.class })
public class MixinRemotePlayerEvents extends gku
{
    public MixinRemotePlayerEvents(final gga $$0, final GameProfile $$1) {
        super($$0, $$1);
    }
    
    protected void b(final ard level, final btc damageSource, final float damageValue) {
        super.b(level, damageSource, damageValue);
        if (damageValue > 0.0f && this.i(damageSource)) {
            DamageBlockedEventCaller.fireDamageBlocked((Player)this, damageValue);
        }
    }
}
