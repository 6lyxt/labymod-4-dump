// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_4.client.util.BlockOutlineUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glv.class })
public class MixinLevelRenderer_CustomBlockOutline
{
    @Shadow
    private gga p;
    @Shadow
    @Final
    private gmh k;
    
    @WrapOperation(method = { "renderBlockOutline" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V") })
    @Dynamic
    private void labyMod$renderCustomBlockOutline(final glv instance, final ffv poseStack, final ffz lineConsumer, final bum entity, final double x, final double y, final double z, final ji blockPos, final dwy blockState, final int color, final Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline((dgj)this.p, (glz)this.k.c(), lineConsumer, poseStack, entity, x, y, z, blockPos, blockState, color);
    }
}
