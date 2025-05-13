// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.cosmetics;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gqm.class })
public class MixinLocalPlayerEvents extends gqj
{
    public MixinLocalPlayerEvents(final glo $$0, final GameProfile $$1) {
        super($$0, $$1);
    }
    
    protected void c(final asb level, final bvt damageSource, final float damageValue) {
        super.c(level, damageSource, damageValue);
    }
}
