// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.Iterator;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import java.util.Optional;

public interface Model extends ModelPartHolder
{
    public static final int MAX_BONES = 240;
    
    void addPart(final String p0, final ModelPart p1);
    
    boolean isInvalidPart(final String p0);
    
    ModelPart getPart(final String p0);
    
    default Optional<ModelPart> findPart(final String name) {
        return Optional.ofNullable(this.getPart(name));
    }
    
    Map<String, ModelPart> getParts();
    
    ResourceLocation getTextureLocation();
    
    void setTextureLocation(final ResourceLocation p0);
    
    Metadata metadata();
    
    default void setCompletableTexture(final CompletableResourceLocation texture) {
        texture.addCompletableListener(() -> this.setTextureLocation(texture.getCompleted()));
    }
    
    default void setDrawCommand(final BufferState command) {
    }
    
    @Nullable
    default BufferState getDrawCommand() {
        return null;
    }
    
    @Nullable
    default RenderedBuffer getRenderedBuffer() {
        return null;
    }
    
    default void deleteBuffer() {
    }
    
    default void printModelTree() {
    }
    
    default Model copy() {
        throw new UnsupportedOperationException("Copy is not supported");
    }
    
    default void applyAnimation(final UniformBone boneMatrices) {
        final Stack stack = Stack.getDefaultEmptyStack();
        this.forChildren(child -> {
            if (!(!child.isVisible())) {
                if (child.getId() < 240) {
                    child.transformMatrix(boneMatrices, stack);
                }
            }
        });
    }
    
    default void reset() {
        this.forChildren(ModelPart::reset);
    }
    
    default float getWidth() {
        float minX = Float.MAX_VALUE;
        float maxX = -3.4028235E38f;
        for (final ModelPart modelPart : this.getChildren().values()) {
            minX = Math.min(minX, modelPart.getMinX());
            maxX = Math.max(maxX, modelPart.getMaxX());
        }
        return Math.abs(maxX - minX);
    }
    
    default float getHeight() {
        float minY = Float.MAX_VALUE;
        float maxY = -3.4028235E38f;
        for (final ModelPart modelPart : this.getChildren().values()) {
            minY = Math.min(minY, modelPart.getMinY());
            maxY = Math.max(maxY, modelPart.getMaxY());
        }
        return Math.abs(maxY - minY);
    }
    
    default float getDepth() {
        float minZ = Float.MAX_VALUE;
        float maxZ = -3.4028235E38f;
        for (final ModelPart modelPart : this.getChildren().values()) {
            minZ = Math.min(minZ, modelPart.getMinZ());
            maxZ = Math.max(maxZ, modelPart.getMaxZ());
        }
        return Math.abs(maxZ - minZ);
    }
    
    default void forChildren(final Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (final ModelPart child : this.getChildren().values()) {
            consumer.accept(child);
        }
    }
}
