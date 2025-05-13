// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_21.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { gex.class }, priority = 900)
public class MixinLevelRendererLevelRenderContext
{
    @Redirect(method = { "renderLevel" }, at = @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;"))
    private fbi labyMod$storePoseStack() {
        final fbi poseStack = new fbi();
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
