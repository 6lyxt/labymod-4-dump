// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import net.labymod.core.main.LabyMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dkv.class })
public class MixinOldHeartAnimation
{
    @WrapOperation(method = { "renderPlayerHealth" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V") })
    private void labyMod$applyOldHeartAnimation(final dkv instance, final dfm poseStack, final int i0, final int i1, int i2, final int i3, final int i4, final int i5, final Operation<Void> original) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HeartOldAnimation animation = oldAnimationRegistry.get("heart");
        if (animation != null) {
            i2 = animation.getU(i2);
        }
        original.call(new Object[] { instance, poseStack, i0, i1, i2, i3, i4, i5 });
    }
}
