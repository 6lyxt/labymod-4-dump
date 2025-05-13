// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import net.labymod.core.main.LabyMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eow.class })
public class MixinOldHeartAnimation
{
    @WrapOperation(method = { "renderHeart" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V") })
    private void labyMod$applyOldHeartAnimation(final eox graphics, final acq location, final int i0, final int i1, int i2, final int i3, final int i4, final int i5, final Operation<Void> original) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HeartOldAnimation animation = oldAnimationRegistry.get("heart");
        if (animation != null) {
            i2 = animation.getU(i2);
        }
        original.call(new Object[] { graphics, location, i0, i1, i2, i3, i4, i5 });
    }
}
