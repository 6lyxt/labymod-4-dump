// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { gdo.class }, priority = 900)
public class MixinLevelRendererLevelRenderContext
{
    @Redirect(method = { "renderLevel" }, at = @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;"))
    private faa labyMod$storePoseStack() {
        final faa poseStack = new faa();
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
