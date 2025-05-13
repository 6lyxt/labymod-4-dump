// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { gdn.class }, priority = 900)
public class MixinLevelRendererLevelRenderContext
{
    @Redirect(method = { "renderLevel" }, at = @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;"))
    private ezz labyMod$storePoseStack() {
        final ezz poseStack = new ezz();
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
