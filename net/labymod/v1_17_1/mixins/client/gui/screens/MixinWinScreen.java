// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.gui.screens;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eax.class })
public class MixinWinScreen
{
    private dql labyMod$poseStack;
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At("HEAD"))
    private void labyMod$getStack(final dql stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }
    
    @Redirect(method = { "render" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dqp labyMod$addStack(final dqg instance, final double x, final double y, final double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float)x, (float)y, (float)z);
    }
    
    @Redirect(method = { "renderBg" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dqp labyMod$addBackgroundStack(final dqg instance, final double x, final double y, final double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float)x, (float)y, (float)z);
    }
}
