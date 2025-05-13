// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin(targets = { "net.optifine.gui.SlotGui" }, remap = false)
public class MixinOptiFineSlotGui
{
    @Inject(method = { "renderList" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableTexture()V", shift = At.Shift.BEFORE) })
    private void labyMod$fixShaderLighting(final eaq poseStack, final int x, final int y, final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
