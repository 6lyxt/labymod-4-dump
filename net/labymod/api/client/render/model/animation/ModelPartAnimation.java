// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation;

import java.util.function.BiPredicate;
import java.util.Collection;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.HashMap;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.model.ModelPart;
import java.util.Map;

public class ModelPartAnimation
{
    private final ModelAnimation parent;
    private final ModelPartTransformation position;
    private final ModelPartTransformation rotation;
    private final ModelPartTransformation scale;
    private final Map<TransformationType, ModelPartTransformation> transformations;
    @Nullable
    private ModelPart modelPart;
    
    public ModelPartAnimation(final ModelAnimation parent, final ModelPartTransformation position, final ModelPartTransformation rotation, final ModelPartTransformation scale) {
        this.parent = parent;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        (this.transformations = new HashMap<TransformationType, ModelPartTransformation>()).put(TransformationType.POSITION, position);
        this.transformations.put(TransformationType.ROTATION, rotation);
        this.transformations.put(TransformationType.SCALE, scale);
    }
    
    public ModelPartAnimation(final ModelAnimation parent) {
        this(parent, new ModelPartTransformation(TransformationType.POSITION, 0.0f, 0.0f, 0.0f), new ModelPartTransformation(TransformationType.ROTATION, 0.0f, 0.0f, 0.0f), new ModelPartTransformation(TransformationType.SCALE, 1.0f, 1.0f, 1.0f));
    }
    
    public ModelAnimation parent() {
        return this.parent;
    }
    
    public ModelPartTransformation position() {
        return this.position;
    }
    
    public ModelPartTransformation rotation() {
        return this.rotation;
    }
    
    public ModelPartTransformation scale() {
        return this.scale;
    }
    
    public ModelPartAnimation addKeyframes(final long timestamp, final boolean smooth, final FloatVector3 position, final FloatVector3 rotation, final FloatVector3 scale, final Collection<TransformationType> disabledTransformations) {
        if (!disabledTransformations.contains(this.position.type())) {
            this.position.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, position));
        }
        if (!disabledTransformations.contains(this.rotation.type())) {
            this.rotation.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, rotation));
        }
        if (!disabledTransformations.contains(this.scale.type())) {
            this.scale.addKeyframe(new ModelPartTransformation.Keyframe(timestamp, smooth, scale));
        }
        return this;
    }
    
    public void filterKeyframes(final BiPredicate<ModelPartTransformation, ModelPartTransformation.Keyframe> predicate) {
        this.position.filterKeyframes(keyframe -> predicate.test(this.position, keyframe));
        this.rotation.filterKeyframes(keyframe -> predicate.test(this.rotation, keyframe));
        this.scale.filterKeyframes(keyframe -> predicate.test(this.scale, keyframe));
    }
    
    public long getLength() {
        return Math.max(Math.max(this.position.getLength(), this.rotation.getLength()), this.scale.getLength());
    }
    
    public ModelPartTransformation transformationByType(final TransformationType type) {
        return this.transformations.get(type);
    }
    
    @Nullable
    public ModelPart getModelPart() {
        return this.modelPart;
    }
    
    public void setModelPart(@Nullable final ModelPart modelPart) {
        this.modelPart = modelPart;
    }
}
