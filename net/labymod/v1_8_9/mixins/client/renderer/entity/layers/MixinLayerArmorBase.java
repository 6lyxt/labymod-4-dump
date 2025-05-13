// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bkn.class })
public class MixinLayerArmorBase
{
    @Insert(method = { "shouldCombineTextures()Z" }, at = @At("HEAD"))
    private void labyMod$oldDamageAnimation(final InsertInfoReturnable<Boolean> returnable) {
        final DamageOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("damage");
        returnable.setReturnValue(animation != null && animation.shouldCombineTextures());
    }
}
