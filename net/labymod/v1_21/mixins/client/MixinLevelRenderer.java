// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import net.labymod.api.util.Color;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_21.client.renderer.OverlayRenderType;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.WorldRenderer;

@Mixin({ gex.class })
public class MixinLevelRenderer implements WorldRenderer
{
    @Shadow
    private fzf u;
    @Shadow
    private gff t;
    @Shadow
    private int ag;
    
    @Redirect(method = { "renderLevel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public void labyMod$renderCustomOutline(final gex instance, final fbi poseStack, final fbm vertexConsumer, final bsr entity, final double x, final double y, final double z, final jd blockPos, final dtc blockState) {
        final RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.u(), blockPos.v(), blockPos.w());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        final double blockX = blockPos.u() - x;
        final double blockY = blockPos.v() - y;
        final double blockZ = blockPos.w() - z;
        final exv voxelShape = blockState.a((dcc)this.u, blockPos, exh.a(entity));
        if (event.getLineColor() != null) {
            voxelShape.a((minX, minY, minZ, maxX, maxY, maxZ) -> this.drawLines(vertexConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ));
            poseStack.a();
        }
        if (event.getOverlayColor() != null) {
            final fbm boxVertex = this.t.c().getBuffer((gfh)OverlayRenderType.INSTANCE);
            voxelShape.b((minX, minY, minZ, maxX, maxY, maxZ) -> {
                this.drawBoxes(boxVertex, poseStack.c(), blockX, blockY, blockZ, event.getOverlayColor()).consume(minX, minY, minZ, maxX, maxY, maxZ);
                poseStack.a();
            });
        }
    }
    
    private exs.a drawLines(final fbm vertexConsumer, final fbi.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = ayo.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
        };
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.AFTER) })
    private void labyMod$beforeEntityRenderPass(final fgf $$0, final boolean $$1, final ffy $$2, final ges $$3, final gey $$4, final Matrix4f $$5, final Matrix4f $$6, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V", shift = At.Shift.AFTER) })
    private void labyMod$afterEntityRenderPass(final fgf $$0, final boolean $$1, final ffy $$2, final ges $$3, final gey $$4, final Matrix4f $$5, final Matrix4f $$6, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
    
    private exs.a drawBoxes(final fbm vertexConsumer, final fbi.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = ayo.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            final float depth = 0.001f;
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
        };
    }
    
    private fbm vertex(final fbm vertexConsumer, final Matrix4f matrix, final double x, final double y, final double z) {
        return vertexConsumer.a(matrix, (float)x, (float)y, (float)z);
    }
    
    @Override
    public int getRenderedEntities() {
        return this.ag;
    }
}
