// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.level;

import net.labymod.api.util.math.vector.FloatVector2;
import java.util.ArrayList;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.world.ClientWorld;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gfx.shader.material.SprayShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.Textures;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class SurfaceRenderer
{
    private final LabyAPI labyAPI;
    private final BufferBuilder debugBuffer;
    private float minX;
    private float maxX;
    private float minZ;
    private float maxZ;
    private float radius;
    private float rotation;
    
    public SurfaceRenderer(final LabyAPI labyAPI, final GFXRenderPipeline renderPipeline) {
        this.labyAPI = labyAPI;
        this.debugBuffer = renderPipeline.createBufferBuilder(256);
    }
    
    public void renderSurface(final Stack stack, final BufferConsumer consumer, final double x, final double y, final double z, final Direction direction, final float radius, final float rotation) {
        this.radius = radius;
        this.rotation = rotation;
        final float halfRadius = radius / 2.0f;
        this.minX = -halfRadius;
        this.minZ = -halfRadius;
        this.maxX = halfRadius;
        this.maxZ = halfRadius;
        final int blockX = MathHelper.floor(x);
        final int blockY = MathHelper.floor(y);
        final int blockZ = MathHelper.floor(z);
        final List<FloatVector3> positions = this.calculatePositions(direction, blockX, blockY, blockZ, x, y, z, halfRadius);
        final ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            final RenderProgram sprayProgram = RenderPrograms.getSpray(Textures.WHITE, Textures.WHITE);
            final ShaderMaterial shaderMaterial = sprayProgram.shaderMaterial();
            if (shaderMaterial instanceof final SprayShaderMaterial sprayShaderMaterial) {
                sprayShaderMaterial.getWear().set(1.0f);
            }
            this.debugBuffer.begin(sprayProgram);
        }
        stack.push();
        stack.translate(-0.5f, -0.5f, 0.0f);
        for (int i = positions.size() - 1; i >= 0; --i) {
            final FloatVector3 position = positions.get(i);
            this.renderPosition(stack, consumer, x, y, z, direction, position, blockX, blockY, blockZ, world);
        }
        stack.pop();
        if (DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            final GFXBridge glStateBridge = Laby.gfx();
            glStateBridge.polygonMode(1032, 6913);
            ImmediateRenderer.drawWithProgram(this.debugBuffer.end());
            glStateBridge.polygonMode(1032, 6914);
        }
    }
    
    private void renderPosition(final Stack stack, final BufferConsumer consumer, final double x, final double y, final double z, final Direction direction, final FloatVector3 position, final int blockX, final int blockY, final int blockZ, final ClientWorld world) {
        int blockPosX = MathHelper.floor(position.getX());
        int blockPosY = MathHelper.floor(position.getY());
        int blockPosZ = MathHelper.floor(position.getZ());
        final int posX = blockPosX - blockX;
        final int posY = blockPosY - blockY;
        final int posZ = blockPosZ - blockZ;
        final double offsetX = blockPosX - x;
        final double offsetY = blockPosY - y;
        final double offsetZ = blockPosZ - z;
        if (direction == Direction.EAST) {
            --blockPosX;
        }
        else if (direction == Direction.SOUTH) {
            --blockPosZ;
        }
        else if (direction == Direction.UP) {
            --blockPosY;
        }
        final BlockState blockState = world.getBlockState(blockPosX, blockPosY, blockPosZ);
        if (blockState.renderShape() == RenderShape.INVISIBLE) {
            return;
        }
        if (!MathHelper.isBox(blockState.bounds())) {
            return;
        }
        stack.push();
        if (direction == Direction.EAST || direction == Direction.WEST) {
            this.buildTile(stack, consumer, posZ, posY, offsetZ, offsetY, direction);
        }
        else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            this.buildTile(stack, consumer, posX, posY, offsetX, offsetY, direction);
        }
        else {
            this.buildTile(stack, consumer, posX, posZ, offsetX, offsetZ, direction);
        }
        stack.pop();
    }
    
    private List<FloatVector3> calculatePositions(final Direction direction, final int blockX, final int blockY, final int blockZ, final double x, final double y, final double z, final float halfRadius) {
        final int blockMinX = MathHelper.floor(x + this.minX);
        final int blockMaxX = MathHelper.floor(x + this.maxX);
        final int blockMinY = MathHelper.floor(y + -halfRadius);
        final int blockMaxY = MathHelper.floor(y + halfRadius);
        final int blockMinZ = MathHelper.floor(z + this.minZ);
        final int blockMaxZ = MathHelper.floor(z + this.maxZ);
        final List<FloatVector3> positions = new ArrayList<FloatVector3>();
        if (direction == Direction.EAST || direction == Direction.WEST) {
            for (int yIndex = blockMinY; yIndex <= blockMaxY; ++yIndex) {
                for (int zIndex = blockMinZ; zIndex <= blockMaxZ; ++zIndex) {
                    positions.add(new FloatVector3((float)blockX, (float)yIndex, (float)zIndex));
                }
            }
        }
        else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            for (int yIndex = blockMinY; yIndex <= blockMaxY; ++yIndex) {
                for (int xIndex = blockMinX; xIndex <= blockMaxX; ++xIndex) {
                    positions.add(new FloatVector3((float)xIndex, (float)yIndex, (float)blockZ));
                }
            }
        }
        else {
            for (int zIndex2 = blockMinZ; zIndex2 <= blockMaxZ; ++zIndex2) {
                for (int xIndex = blockMinX; xIndex <= blockMaxX; ++xIndex) {
                    positions.add(new FloatVector3((float)xIndex, (float)blockY, (float)zIndex2));
                }
            }
        }
        return positions;
    }
    
    private void buildTile(final Stack stack, final BufferConsumer consumer, final double x, final double y, double offsetX, double offsetY, final Direction direction) {
        double left = x;
        double top = -y;
        double right = x + 1.0;
        double bottom = -y + 1.0;
        if (direction == Direction.WEST) {
            left = -x;
            right = -x + 1.0;
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            top = y;
            bottom = y + 1.0;
        }
        if (left == this.minX) {
            left += 0.5;
        }
        else if (right == this.maxX + 1.0f) {
            right -= 0.5;
        }
        if (top == this.minZ) {
            top += 0.5;
        }
        else if (bottom == this.maxZ + 1.0f) {
            bottom -= 0.5;
        }
        offsetX = 1.0 - Math.abs(x - offsetX);
        if (left < 0.0) {
            if (direction == Direction.WEST) {
                right -= -0.5 + offsetX;
            }
            else {
                right += -0.5 + offsetX;
            }
        }
        else if (left > 0.0) {
            if (direction == Direction.WEST) {
                left -= -0.5 + offsetX;
            }
            else {
                left += -0.5 + offsetX;
            }
        }
        else if (direction == Direction.WEST) {
            left -= -0.5 + offsetX;
            right -= -0.5 + offsetX;
        }
        else {
            left += -0.5 + offsetX;
            right += -0.5 + offsetX;
        }
        offsetY = 1.0 - Math.abs(y - offsetY);
        if (top < 0.0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                bottom += -0.5 + offsetY;
            }
            else {
                bottom -= -0.5 + offsetY;
            }
        }
        else if (top > 0.0) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                top += -0.5 + offsetY;
            }
            else {
                top -= -0.5 + offsetY;
            }
        }
        else if (direction == Direction.UP || direction == Direction.DOWN) {
            top += -0.5 + offsetY;
            bottom += -0.5 + offsetY;
        }
        else {
            top -= -0.5 + offsetY;
            bottom -= -0.5 + offsetY;
        }
        if (DebugRegistry.SURFACE_WIREFRAME.isEnabled()) {
            this.buildQuad(stack, this.debugBuffer, direction, left, top, right, bottom, this.radius);
        }
        this.buildQuad(stack, consumer, direction, left, top, right, bottom, this.radius);
    }
    
    private void buildQuad(final Stack stack, final BufferConsumer consumer, final Direction direction, final double left, final double top, final double right, final double bottom, final float radius) {
        this.buildQuad(stack, consumer, direction, left, top, right, bottom, radius, -1);
    }
    
    private void buildQuad(final Stack stack, final BufferConsumer consumer, final Direction direction, final double left, final double top, final double right, final double bottom, final float radius, final int color) {
        final float halfRadius = radius / 2.0f;
        final float norm = halfRadius - 0.5f;
        final float scale = radius;
        float minU = (float)((left + norm) / scale);
        final float minV = (float)((top + norm) / scale);
        float maxU = (float)((right + norm) / scale);
        final float maxV = (float)((bottom + norm) / scale);
        if (direction == Direction.WEST || direction == Direction.EAST || direction == Direction.NORTH || direction == Direction.DOWN) {
            minU = 1.0f - minU;
            maxU = 1.0f - maxU;
        }
        this.buildVertex(stack, consumer, left, top, minU, minV, color);
        this.buildVertex(stack, consumer, left, bottom, minU, maxV, color);
        this.buildVertex(stack, consumer, right, bottom, maxU, maxV, color);
        this.buildVertex(stack, consumer, right, top, maxU, minV, color);
    }
    
    private void buildVertex(final Stack stack, final BufferConsumer consumer, final double x, final double y, float u, float v, final int color) {
        final FloatVector2 uv = this.rotateUV(u, v);
        u = uv.getX();
        v = uv.getY();
        consumer.pos(stack, x, y, 0.0).uv(u, v).color(color).endVertex();
    }
    
    private FloatVector2 rotateUV(final float u, final float v) {
        final float center = this.radius / 4.0f;
        final float translatedU = u - center;
        final float translatedV = v - center;
        final float radians = MathHelper.toRadiansFloat(this.rotation);
        final float cosTheta = MathHelper.cos(radians);
        final float sinTheta = MathHelper.sin(radians);
        float rotatedU = translatedU * cosTheta - translatedV * sinTheta;
        float rotatedV = translatedU * sinTheta + translatedV * cosTheta;
        rotatedU += center;
        rotatedV += center;
        return new FloatVector2(rotatedU, rotatedV);
    }
    
    public void close() {
        if (this.debugBuffer != null) {
            this.debugBuffer.close();
        }
    }
}
