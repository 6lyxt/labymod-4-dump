// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_3.client.util.BlockOutlineUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Final;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ glh.class })
public class MixinOptiFineLevelRenderer_CustomBlockOutline
{
    @Shadow
    @Nullable
    private gfk p;
    @Shadow
    @Final
    private glt k;
    
    @WrapOperation(method = { "renderBlockOutline(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;ZF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V") })
    @Dynamic
    private void labyMod$renderCustomBlockOutline(final glh instance, final fgs poseStack, final fgw lineConsumer, final bvk entity, final double x, final double y, final double z, final jh blockPos, final dxv blockState, final int color, final Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline((dhi)this.p, (gll)this.k.c(), lineConsumer, poseStack, entity, x, y, z, blockPos, blockState, color);
    }
}
