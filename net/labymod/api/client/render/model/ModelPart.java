// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.shader.uniform.UniformBone;
import net.labymod.api.client.gfx.pipeline.material.MutableMaterial;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.Transformation;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.material.Material;
import net.labymod.api.client.gfx.pipeline.material.MaterialColor;
import java.util.Iterator;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.List;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.debug.DebugNameable;

public interface ModelPart extends ModelPartHolder, DebugNameable
{
    int getId();
    
    void setId(final int p0);
    
    void addBox(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final boolean p7);
    
    default ModelBox createAndAddBox(final float x, final float y, final float z, final float width, final float height, final float depth, final float delta, final boolean mirror) {
        throw new UnsupportedOperationException("Boxes not supported");
    }
    
    void addBox(final ModelBox p0);
    
    List<ModelBox> getBoxes();
    
    default float getMinX() {
        float minX = Float.MAX_VALUE;
        for (final ModelBox box : this.getBoxes()) {
            minX = Math.min(minX, box.getMinX());
        }
        for (final ModelPart child : this.getChildren().values()) {
            minX = Math.min(minX, child.getMinX());
        }
        return (minX + this.getModelPartTransform().getTranslation().getX()) * ModelUtil.getScale(this, FloatVector3::getX);
    }
    
    default float getMaxX() {
        float maxX = -3.4028235E38f;
        for (final ModelBox box : this.getBoxes()) {
            maxX = Math.max(maxX, box.getMaxX());
        }
        for (final ModelPart child : this.getChildren().values()) {
            maxX = Math.max(maxX, child.getMaxX());
        }
        return (maxX + this.getModelPartTransform().getTranslation().getX()) * ModelUtil.getScale(this, FloatVector3::getX);
    }
    
    default float getMinY() {
        float minY = Float.MAX_VALUE;
        for (final ModelBox box : this.getBoxes()) {
            minY = Math.min(minY, box.getMinY());
        }
        for (final ModelPart child : this.getChildren().values()) {
            minY = Math.min(minY, child.getMinY());
        }
        return (minY + this.getModelPartTransform().getTranslation().getY()) * ModelUtil.getScale(this, FloatVector3::getY);
    }
    
    default float getMaxY() {
        float maxY = -3.4028235E38f;
        for (final ModelBox box : this.getBoxes()) {
            maxY = Math.max(maxY, box.getMaxY());
        }
        for (final ModelPart child : this.getChildren().values()) {
            maxY = Math.max(maxY, child.getMaxY());
        }
        return (maxY + this.getModelPartTransform().getTranslation().getY()) * ModelUtil.getScale(this, FloatVector3::getY);
    }
    
    default float getMinZ() {
        float minZ = Float.MAX_VALUE;
        for (final ModelBox box : this.getBoxes()) {
            minZ = Math.min(minZ, box.getMinZ());
        }
        for (final ModelPart child : this.getChildren().values()) {
            minZ = Math.min(minZ, child.getMinZ());
        }
        return (minZ + this.getModelPartTransform().getTranslation().getZ()) * ModelUtil.getScale(this, FloatVector3::getZ);
    }
    
    default float getMaxZ() {
        float maxZ = Float.MIN_VALUE;
        for (final ModelBox box : this.getBoxes()) {
            maxZ = Math.max(maxZ, box.getMaxZ());
        }
        for (final ModelPart child : this.getChildren().values()) {
            maxZ = Math.max(maxZ, child.getMaxZ());
        }
        return (maxZ + this.getModelPartTransform().getTranslation().getZ()) * ModelUtil.getScale(this, FloatVector3::getZ);
    }
    
    default float getWidth() {
        return Math.abs(this.getMaxX() - this.getMinX());
    }
    
    default float getHeight() {
        return Math.abs(this.getMaxY() - this.getMinY());
    }
    
    default float getDepth() {
        return Math.abs(this.getMaxZ() - this.getMinZ());
    }
    
    default boolean isInvisible() {
        return !this.isVisible();
    }
    
    boolean isVisible();
    
    void setVisible(final boolean p0);
    
    default MaterialColor getColor() {
        final Material material = this.getMaterial();
        if (material == null) {
            return (this.getParent() == null) ? null : this.getParent().getColor();
        }
        return material.getColor();
    }
    
    @Nullable
    Material getMaterial();
    
    void setMaterial(@Nullable final Material p0);
    
    Transformation getModelPartTransform();
    
    Transformation getAnimationTransformation();
    
    void copyFrom(final ModelPart p0);
    
    int getTextureWidth();
    
    int getTextureHeight();
    
    void setTextureSize(final int p0, final int p1);
    
    int getTextureOffsetX();
    
    int getTextureOffsetY();
    
    void setTextureOffset(final int p0, final int p1);
    
    @Nullable
    ModelPart getParent();
    
    void setParent(@Nullable final ModelPart p0);
    
    default boolean isParentGlowing() {
        ModelPart parent = this.getParent();
        boolean glowing = false;
        while (parent != null) {
            glowing = parent.isGlowing();
            if (glowing) {
                break;
            }
            parent = parent.getParent();
        }
        return glowing;
    }
    
    default long getParentCycleDuration() {
        ModelPart parent = this.getParent();
        long duration = 0L;
        while (parent != null) {
            final Material material = parent.getMaterial();
            if (material != null) {
                duration = material.getColor().getCycle();
            }
            if (duration > 0L) {
                break;
            }
            parent = parent.getParent();
        }
        return duration;
    }
    
    default boolean isGlowing() {
        final Material material = this.getMaterial();
        return material != null && material.isGlowing();
    }
    
    default ResourceLocation getTextureLocation() {
        final Material material = this.getMaterial();
        return (material == null) ? null : material.getTextureLocation();
    }
    
    default void setTextureLocation(final ResourceLocation textureLocation) {
        final Material material = this.getMaterial();
        if (!(material instanceof MutableMaterial)) {
            return;
        }
        ((MutableMaterial)material).setTextureLocation(textureLocation);
    }
    
    default void transformMatrix(final UniformBone uniform, final Stack transformMatrix) {
    }
    
    default void reset() {
        final Transformation animationTransformation = this.getAnimationTransformation();
        animationTransformation.reset();
        this.forChildren(ModelPart::reset);
    }
    
    default void forChildren(final Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (final ModelPart child : this.getChildren().values()) {
            consumer.accept(child);
        }
    }
    
    default boolean isInvisibleOrEmpty() {
        return this.isInvisible() || (this.getBoxes().isEmpty() && this.getChildren().isEmpty());
    }
    
    default ModelPart copy() {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
