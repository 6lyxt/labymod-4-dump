// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ccc.class })
public abstract class MixinLayerHeldItem
{
    @Redirect(method = { "renderHeldItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"))
    private void labyMod$callItemInHandLayerRenderEvent(final buu renderer, final vp livingEntity, final aip itemStack, final bwc.b type, final boolean leftHand) {
        final ItemInHandLayerRenderEvent event = this.labyMod$fireItemInHandLayerRenderEvent(Phase.PRE, livingEntity, itemStack, type, leftHand, 0);
        if (event.isCancelled()) {
            return;
        }
        renderer.a(livingEntity, itemStack, type, leftHand);
        this.labyMod$fireItemInHandLayerRenderEvent(Phase.POST, livingEntity, itemStack, type, leftHand, 0);
    }
    
    private ItemInHandLayerRenderEvent labyMod$fireItemInHandLayerRenderEvent(final Phase phase, final vp livingEntity, final aip itemStack, final bwc.b type, final boolean leftHand, final int combinedLight) {
        return Laby.fireEvent(new ItemInHandLayerRenderEvent(VersionedStackProvider.DEFAULT_STACK, phase, (LivingEntity)livingEntity, MinecraftUtil.fromMinecraft(itemStack), MinecraftUtil.fromMinecraft(type), leftHand ? LivingEntity.HandSide.LEFT : LivingEntity.HandSide.RIGHT, combinedLight));
    }
}
