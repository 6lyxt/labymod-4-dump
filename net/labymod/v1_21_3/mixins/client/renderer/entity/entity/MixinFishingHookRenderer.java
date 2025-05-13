// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ grx.class })
public abstract class MixinFishingHookRenderer
{
    @Redirect(method = { "getPlayerHandPos" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera$NearPlane;getPointOnPlane(FF)Lnet/minecraft/world/phys/Vec3;"))
    private fby labyMod$renderFishingHook(final flp.a instance, float x, float y) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get()) {
            final FishingRodOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("fishing_rod");
            if (animation != null) {
                final FloatVector3 stringVector = animation.getStringVector();
                x += stringVector.getX();
                y += stringVector.getY();
            }
        }
        return instance.a(x, y);
    }
}
