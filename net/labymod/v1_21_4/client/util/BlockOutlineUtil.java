// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.util;

import org.joml.Matrix4f;
import net.labymod.v1_21_4.client.renderer.OverlayRenderType;
import net.labymod.api.Laby;
import net.labymod.api.util.Color;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;

public class BlockOutlineUtil
{
    public static void renderBlockOutline(final dgj level, final glz bufferSource, final ffz lineConsumer, final ffv poseStack, final bum entity, final double x, final double y, final double z, final ji blockPos, final dwy blockState, final int color) {
        final RenderBlockSelectionBoxEvent event = Laby.fireEvent(new RenderBlockSelectionBoxEvent(Color.of(color), blockPos.u(), blockPos.v(), blockPos.w()));
        if (event.isCancelled()) {
            return;
        }
        final double blockX = blockPos.u() - x;
        final double blockY = blockPos.v() - y;
        final double blockZ = blockPos.w() - z;
        final fbv shape = blockState.a((dfo)level, blockPos, fbg.a(entity));
        if (event.getLineColor() != null) {
            shape.a((minX, minY, minZ, maxX, maxY, maxZ) -> drawLines(lineConsumer, poseStack.c(), blockX, blockY, blockZ, event.getLineColor()).consume(minX, minY, minZ, maxX, maxY, maxZ));
        }
        final Color overlayColor = event.getOverlayColor();
        if (overlayColor != null) {
            final ffz overlayConsumer = bufferSource.getBuffer((gmj)OverlayRenderType.INSTANCE);
            shape.b((minX, minY, minZ, maxX, maxY, maxZ) -> drawBoxes(overlayConsumer, poseStack.c(), blockX, blockY, blockZ, overlayColor).consume(minX, minY, minZ, maxX, maxY, maxZ));
        }
    }
    
    private static fbs.a drawLines(final ffz vertexConsumer, final ffv.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = ayz.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
        };
    }
    
    private static fbs.a drawBoxes(final ffz vertexConsumer, final ffv.a pose, final double x, final double y, final double z, final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        return (minX, minY, minZ, maxX, maxY, maxZ) -> {
            float blockX = (float)(maxX - minX);
            float blockY = (float)(maxY - minY);
            float blockZ = (float)(maxZ - minZ);
            final float offset = ayz.c(blockX * blockX + blockY * blockY + blockZ * blockZ);
            blockX /= offset;
            blockY /= offset;
            blockZ /= offset;
            final float depth = 0.001f;
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y - depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y, minZ + z - depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, maxY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x, minY + y, maxZ + z + depth).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x - depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), minX + x - depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, minZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x + depth, maxY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
            addVertex(vertexConsumer, pose.a(), maxX + x + depth, minY + y + depth, maxZ + z).a(red, green, blue, alpha).b(pose, blockX, blockY, blockZ);
        };
    }
    
    private static ffz addVertex(final ffz vertexConsumer, final Matrix4f matrix, final double x, final double y, final double z) {
        return vertexConsumer.a(matrix, (float)x, (float)y, (float)z);
    }
}
