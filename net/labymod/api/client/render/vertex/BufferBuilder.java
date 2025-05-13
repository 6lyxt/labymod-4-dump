// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public interface BufferBuilder
{
    default BufferBuilder begin(final int mode, final VertexFormatType formatType) {
        return this.begin(mode, formatType.vertexName());
    }
    
    BufferBuilder begin(final int p0, final String p1);
    
    default BufferBuilder begin(final RenderPhase phase) {
        return this.begin(phase, null);
    }
    
    default BufferBuilder begin(final RenderPhase phase, final VertexSorter vertexSorter) {
        return this.begin(phase, vertexSorter, false);
    }
    
    BufferBuilder begin(final RenderPhase p0, final VertexSorter p1, final boolean p2);
    
    BufferBuilder vertex(final float p0, final float p1, final float p2);
    
    default BufferBuilder vertex(@NotNull final Stack stack, final double x, final double y, final double z) {
        return this.vertex(stack, (float)x, (float)y, (float)z);
    }
    
    default BufferBuilder vertex(@NotNull final Stack stack, final float x, final float y, final float z) {
        return this.vertex(stack.getProvider().getPosition(), x, y, z);
    }
    
    default BufferBuilder vertex(final FloatMatrix4 poseMatrix, final float x, final float y, final float z) {
        final FloatVector4 vector = new FloatVector4(x, y, z, 1.0f);
        vector.transform(poseMatrix);
        return this.vertex(vector.getX(), vector.getY(), vector.getZ());
    }
    
    default BufferBuilder texture(final FloatVector2 uv) {
        return this.texture(uv.getX(), uv.getY());
    }
    
    BufferBuilder texture(final float p0, final float p1);
    
    BufferBuilder color(final float p0, final float p1, final float p2, final float p3);
    
    default BufferBuilder color(@NotNull final GraphicsColor color) {
        return this.color(color.red(), color.green(), color.blue(), color.alpha());
    }
    
    default BufferBuilder color(final int color) {
        return this.color(GraphicsColor.DEFAULT_COLOR.update(color));
    }
    
    BufferBuilder overlay(final int p0, final int p1);
    
    BufferBuilder lightMap(final int p0, final int p1);
    
    BufferBuilder normal(final float p0, final float p1, final float p2);
    
    default BufferBuilder normal(final Stack stack, final float x, final float y, final float z) {
        return this.normal(stack.getProvider().getNormal(), x, y, z);
    }
    
    default BufferBuilder normal(final FloatMatrix3 normal, final float x, final float y, final float z) {
        final FloatVector3 vector = new FloatVector3(x, y, z);
        vector.transform(normal);
        return this.normal(vector.getX(), vector.getY(), vector.getZ());
    }
    
    BufferBuilder next();
    
    void end();
    
    void uploadToBuffer();
    
    OldVertexFormat currentFormat();
    
    default void addVertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int overlayUV, final int lightMapUV, final float normalX, final float normalY, final float normalZ) {
        this.vertex(x, y, z);
        this.color(red, green, blue, alpha);
        this.texture(u, v);
        this.overlay(overlayUV);
        this.lightMap(lightMapUV);
        this.normal(normalX, normalY, normalZ);
        this.next();
    }
    
    default BufferBuilder lightMap(final int lightMapUV) {
        return this.lightMap(lightMapUV & 0xFFFF, lightMapUV >> 16 & 0xFFFF);
    }
    
    default BufferBuilder overlay(final int overlayUV) {
        return this.overlay(overlayUV & 0xFFFF, overlayUV >> 16 & 0xFFFF);
    }
    
    default boolean building() {
        return false;
    }
    
    @FunctionalInterface
    public interface VertexSorter
    {
        Object bufferSource();
    }
}
