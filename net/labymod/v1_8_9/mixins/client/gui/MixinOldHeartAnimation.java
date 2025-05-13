// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.HeartOldAnimation;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avo.class })
public class MixinOldHeartAnimation
{
    @WrapOperation(method = { "renderPlayerStats" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 3) })
    private void labyMod$oldHeartBackground(final avo instance, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight, final Operation<Void> original) {
        this.labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }
    
    @WrapOperation(method = { "renderPlayerStats" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 4) })
    private void labyMod$oldFullHeartFlash(final avo instance, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight, final Operation<Void> original) {
        this.labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }
    
    @WrapOperation(method = { "renderPlayerStats" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 5) })
    private void labyMod$oldHalfHeartFlash(final avo instance, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight, final Operation<Void> original) {
        this.labyMod$applyOldHeartAnimation(instance, x, y, u, v, uWidth, vHeight, original);
    }
    
    private void labyMod$applyOldHeartAnimation(final avo instance, final int x, final int y, int u, final int v, final int uWidth, final int vHeight, final Operation<Void> original) {
        final OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        final HeartOldAnimation animation = oldAnimationRegistry.get("heart");
        if (animation != null) {
            u = animation.getU(u);
        }
        original.call(new Object[] { instance, x, y, u, v, uWidth, vHeight });
    }
}
