// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import net.labymod.api.util.Color;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_20_2.client.renderer.OverlayRenderType;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.WorldRenderer;

@Mixin({ foc.class })
public class MixinLevelRenderer implements WorldRenderer
{
    @Shadow
    private fix u;
    @Shadow
    private fok t;
    @Shadow
    private int ag;
    
    @Redirect(method = { "renderLevel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    public void labyMod$renderCustomOutline(final foc instance, final elp poseStack, final elt vertexConsumer, final biq entity, final double x, final double y, final double z, final gw blockPos, final dfj blockState) {
        final RenderBlockSelectionBoxEvent event = new RenderBlockSelectionBoxEvent(blockPos.u(), blockPos.v(), blockPos.w());
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        final double blockX = blockPos.u() - x;
        final double blockY = blockPos.v() - y;
        final double blockZ = blockPos.w() - z;
        final eig voxelShape = blockState.a((cpb)this.u, blockPos, ehs.a(entity));
        if (event.getLineColor() != null) {
            voxelShape.a((minX, minY, minZ, maxX, maxY, maxZ) -> this.drawLines(vertexConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ));
            poseStack.a();
        }
        if (event.getOverlayColor() != null) {
            final elt boxVertex = this.t.b().getBuffer((fom)OverlayRenderType.INSTANCE);
            voxelShape.b((minX, minY, minZ, maxX, maxY, maxZ) -> {
                this.drawBoxes(boxVertex, poseStack.c(), blockX, blockY, blockZ, event.getOverlayColor()).consume(minX, minY, minZ, maxX, maxY, maxZ);
                poseStack.a();
            });
        }
    }
    
    private eid.a drawLines(final elt vertexConsumer, final elp.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = arw.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
        };
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", shift = At.Shift.AFTER) })
    private void labyMod$beforeEntityRenderPass(final elp param0, final float param1, final long param2, final boolean param3, final eqg param4, final fnx param5, final fod param6, final Matrix4f param7, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V", shift = At.Shift.AFTER) })
    private void labyMod$afterEntityRenderPass(final elp param0, final float param1, final long param2, final boolean param3, final eqg param4, final fnx param5, final fod param6, final Matrix4f param7, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }
    
    private eid.a drawBoxes(final elt vertexConsumer, final elp.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = arw.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            final float depth = 0.001f;
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
            this.vertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).a(pose.b(), blockX, blockY, blockZ).e();
        };
    }
    
    private elt vertex(final elt vertexConsumer, final Matrix4f matrix, final double x, final double y, final double z) {
        return vertexConsumer.a(matrix, (float)x, (float)y, (float)z);
    }
    
    @Override
    public int getRenderedEntities() {
        return this.ag;
    }
}
