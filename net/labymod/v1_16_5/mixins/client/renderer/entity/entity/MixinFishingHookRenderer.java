// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.entity.entity;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.FishingRodOldAnimation;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ efa.class })
public abstract class MixinFishingHookRenderer
{
    @Redirect(method = { "render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = @At(value = "NEW", target = "net/minecraft/world/phys/Vec3"))
    private dcn labyMod$renderFishingHook(double x, double y, final double z) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().oldFishingRod().get()) {
            final FishingRodOldAnimation animation = LabyMod.getInstance().getOldAnimationRegistry().get("fishing_rod");
            if (animation != null) {
                final FloatVector3 stringVector = animation.getStringVector();
                x += stringVector.getX();
                y += stringVector.getY();
            }
        }
        return new dcn(x, y, z);
    }
}
