// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ biz.class })
public abstract class MixinRenderFish
{
    @Redirect(method = { "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V" }, at = @At(value = "NEW", target = "Lnet/minecraft/util/Vec3;<init>"))
    private aui labyMod$renderFishingHook(double x, double y, final double z) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get()) {
            final FishingRodOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("fishing_rod");
            if (animation != null) {
                final FloatVector3 stringVector = animation.getStringVector();
                x += stringVector.getX();
                y += stringVector.getY();
            }
        }
        return new aui(x, y, z);
    }
}
