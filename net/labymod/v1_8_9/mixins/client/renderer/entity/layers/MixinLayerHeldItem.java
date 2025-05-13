// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity.layers;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bky.class })
public class MixinLayerHeldItem
{
    @Redirect(method = { "doRenderLayer" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void labyMod$callItemInHandLayerEvent(final bfn renderer, final pr livingEntity, final zx itemStack, final bgr.b type) {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final ItemStack labyItemStack = MinecraftUtil.fromMinecraft(itemStack);
        final ModelTransformType labyTransformType = MinecraftUtil.fromMinecraft(type);
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        gfx.storeBlaze3DStates();
        final ItemInHandLayerRenderEvent event = Laby.fireEvent(new ItemInHandLayerRenderEvent(stack, Phase.PRE, (LivingEntity)livingEntity, labyItemStack, labyTransformType, LivingEntity.HandSide.RIGHT, -1));
        gfx.restoreBlaze3DStates();
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type);
        gfx.storeBlaze3DStates();
        Laby.fireEvent(new ItemInHandLayerRenderEvent(stack, Phase.POST, (LivingEntity)livingEntity, labyItemStack, labyTransformType, LivingEntity.HandSide.RIGHT, -1));
        gfx.restoreBlaze3DStates();
    }
}
