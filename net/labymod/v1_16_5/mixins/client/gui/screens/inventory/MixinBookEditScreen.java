// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.gui.screens.inventory;

import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dpu.class })
public class MixinBookEditScreen
{
    private dfm labyMod$poseStack;
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V" }, at = @At("HEAD"))
    private void labyMod$getStack(final dfm stack, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }
    
    @Redirect(method = { "renderHighlight" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dfq labyMod$addStack(final dfh instance, final double x, final double y, final double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float)x, (float)y, (float)z);
    }
    
    @Inject(method = { "renderHighlight" }, at = { @At("TAIL") })
    private void labyMod$setWhiteShaderColor(final eal[] $$0, final CallbackInfo ci) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
