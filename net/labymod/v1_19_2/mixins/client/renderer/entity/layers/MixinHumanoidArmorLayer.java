// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fii.class })
public class MixinHumanoidArmorLayer
{
    private int labyMod$overlayCoords;
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V" }, at = @At("HEAD"))
    private void labyMod$getLivingEntity(final eaq poseStack, final ezs bufferSource, final int packedLight, final bcc entity, final float param4, final float param5, final float param6, final float param7, final float param8, final float param9, final InsertInfo info) {
        this.labyMod$overlayCoords = ffm.c(entity, 0.0f);
    }
    
    @Redirect(method = { "renderModel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"))
    private void labyMod$enableOverlayTexture(final erw model, final eaq stack, final eau consumer, final int packedLight, final int overlayTexture, final float red, final float green, final float blue, final float alpha) {
        model.a(stack, consumer, packedLight, this.labyMod$overlayCoords, red, green, blue, alpha);
    }
}
