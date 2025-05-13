// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.model.animation;

import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import java.util.Iterator;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.GameTickProvider;
import java.util.Objects;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.util.function.FloatSupplier;
import java.util.function.Function;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.List;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.function.TriFunction;
import net.labymod.api.client.render.model.animation.AnimationController;

public class DefaultAnimationController implements AnimationController
{
    public static final TriFunction<Model, String, ModelPartAnimation, ModelPart> SIMPLE_ANIMATION;
    public static final TriFunction<Model, String, ModelPartAnimation, ModelPart> SHARED_MODEL_ANIMATION;
    private final TriFunction<Model, String, ModelPartAnimation, ModelPart> modelFunction;
    private final List<ModelAnimation> animationQueue;
    private Consumer<ModelAnimation> onStop;
    private Function<ModelPart, Boolean> hiddenPart;
    private ModelAnimation playing;
    private float speed;
    private float speedChangedProgress;
    private int maxQueueSize;
    private boolean animateStrict;
    private float startTick;
    private float lengthTicks;
    private FloatSupplier tickProvider;
    private AnimationApplier animationApplier;
    private PartTransformer partTransformer;
    private boolean positionEnabled;
    private boolean rotationEnabled;
    private boolean scaleEnabled;
    
    public DefaultAnimationController() {
        this(DefaultAnimationController.SIMPLE_ANIMATION);
    }
    
    public DefaultAnimationController(final TriFunction<Model, String, ModelPartAnimation, ModelPart> modelFunction) {
        this.hiddenPart = ModelPart::isInvisible;
        this.modelFunction = modelFunction;
        this.animationQueue = new ArrayList<ModelAnimation>();
        this.speed = 1.0f;
        this.maxQueueSize = 10;
        this.animateStrict = true;
        final GameTickProvider gameTickProvider = Laby.labyAPI().renderPipeline().gameTickProvider();
        Objects.requireNonNull(gameTickProvider);
        this.tickProvider = gameTickProvider::get;
        this.animationApplier = new DefaultAnimationApplier();
        this.partTransformer = new PartTransformer(this) {};
        this.positionEnabled = true;
        this.rotationEnabled = true;
        this.scaleEnabled = true;
    }
    
    @Override
    public AnimationController playNext(@NotNull final ModelAnimation defaultAnimation) {
        this.startTick = this.tickProvider.get();
        this.swap(this.animationQueue.isEmpty() ? defaultAnimation : this.animationQueue.remove(0));
        this.speedChangedProgress = 0.0f;
        float speed = 1.0f;
        final boolean hasToReachNextInQueue = !this.animationQueue.isEmpty();
        if (hasToReachNextInQueue) {
            speed = this.playing.getMetaDefault(AnimationMeta.SPEED, speed);
        }
        return this.speed(speed);
    }
    
    @Override
    public AnimationController swap(@Nullable final ModelAnimation animation) {
        this.playing = animation;
        this.lengthTicks = ((animation == null) ? 0.0f : TimeUtil.convertMillisecondsToTicks(animation.getLength()));
        return this;
    }
    
    @Override
    public AnimationController stop() {
        this.startTick = 0.0f;
        return this.swap(null);
    }
    
    @Override
    public boolean isPlaying() {
        if (this.playing == null) {
            return false;
        }
        final float currentTick = this.tickProvider.get();
        if (this.startTick > currentTick) {
            return false;
        }
        if (this.getProgressTicks() > this.lengthTicks) {
            if (this.playing != null && this.onStop != null) {
                this.onStop.accept(this.playing);
            }
            this.stop();
            return false;
        }
        return true;
    }
    
    @Override
    public AnimationController queue(@NotNull final ModelAnimation animation) {
        if (this.maxQueueSize != -1 && this.animationQueue.size() >= this.maxQueueSize) {
            return this;
        }
        this.animationQueue.add(animation);
        return this;
    }
    
    @Override
    public boolean isQueued(@NotNull final ModelAnimation animation) {
        return this.animationQueue.contains(animation);
    }
    
    @Nullable
    @Override
    public ModelAnimation getPlaying() {
        return this.isPlaying() ? this.playing : null;
    }
    
    @Override
    public float getSpeed() {
        return this.speed;
    }
    
    @Override
    public AnimationController speed(final float speed) {
        this.speedChangedProgress = this.getProgressTicks();
        this.speed = speed;
        return this;
    }
    
    @Override
    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }
    
    @Override
    public AnimationController maxQueueSize(final int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        return this;
    }
    
    @Override
    public boolean isAnimateStrict() {
        return this.animateStrict;
    }
    
    @Override
    public AnimationController animateStrict(final boolean animateStrict) {
        this.animateStrict = animateStrict;
        return this;
    }
    
    @Override
    public AnimationController tickProvider(@NotNull final FloatSupplier tickProvider) {
        this.tickProvider = tickProvider;
        return this;
    }
    
    @NotNull
    @Override
    public AnimationApplier animationApplier() {
        return this.animationApplier;
    }
    
    @Override
    public AnimationController animationApplier(@NotNull final AnimationApplier animationApplier) {
        this.animationApplier = animationApplier;
        return this;
    }
    
    @NotNull
    @Override
    public PartTransformer partTransformer() {
        return this.partTransformer;
    }
    
    @Override
    public AnimationController partTransformer(@NotNull final PartTransformer partTransformer) {
        this.partTransformer = partTransformer;
        return this;
    }
    
    @Override
    public AnimationController enableTransformation(final TransformationType... types) {
        for (final TransformationType type : types) {
            switch (type) {
                case POSITION: {
                    this.positionEnabled = true;
                    break;
                }
                case ROTATION: {
                    this.rotationEnabled = true;
                    break;
                }
                case SCALE: {
                    this.scaleEnabled = true;
                    break;
                }
            }
        }
        return this;
    }
    
    @Override
    public AnimationController disableTransformation(final TransformationType... types) {
        for (final TransformationType type : types) {
            switch (type) {
                case POSITION: {
                    this.positionEnabled = false;
                    break;
                }
                case ROTATION: {
                    this.rotationEnabled = false;
                    break;
                }
                case SCALE: {
                    this.scaleEnabled = false;
                    break;
                }
            }
        }
        return this;
    }
    
    @Override
    public AnimationController applyAnimation(final Model model, final String... excludedParts) {
        if (!this.isPlaying()) {
            return this;
        }
        for (final ModelAnimation.NamedModelPartAnimation animation : this.playing.getPartAnimations()) {
            final String partName = this.partTransformer.remapName(model, animation.getName());
            if (model.isInvalidPart(partName)) {
                continue;
            }
            final ModelPartAnimation partAnimation = animation.getAnimation();
            if (CollectionHelper.contains(excludedParts, animation.getName())) {
                continue;
            }
            final ModelPart modelPart = this.modelFunction.apply(model, partName, partAnimation);
            if (modelPart == null) {
                continue;
            }
            if (this.hiddenPart.apply(modelPart)) {
                continue;
            }
            this.partTransformer.preAnimationApply(model, partName, modelPart, partAnimation);
            final ModelPartTransformation position = partAnimation.position();
            final ModelPartTransformation rotation = partAnimation.rotation();
            final ModelPartTransformation scale = partAnimation.scale();
            final long progress = this.getProgress();
            if (this.positionEnabled && (this.animateStrict || position.hasKeyframe(progress))) {
                this.animationApplier.applyPosition(model, modelPart, position.get(progress), partAnimation);
            }
            if (this.rotationEnabled && (this.animateStrict || rotation.hasKeyframe(progress))) {
                this.animationApplier.applyRotation(model, modelPart, rotation.get(progress), partAnimation);
            }
            if (this.scaleEnabled && (this.animateStrict || scale.hasKeyframe(progress))) {
                this.animationApplier.applyScale(model, modelPart, scale.get(progress), partAnimation);
            }
            this.partTransformer.postAnimationApply(model, partName, modelPart, partAnimation);
        }
        return this;
    }
    
    @Override
    public AnimationController transform(final Stack stack, final String partName) {
        final FloatVector3 translation = this.getCurrentPosition(partName);
        if (this.positionEnabled && translation != null) {
            stack.translate(translation.getX() * 0.0625f, translation.getY() * 0.0625f, translation.getZ() * 0.0625f);
        }
        final FloatVector3 rotation = this.getCurrentRotation(partName);
        if (this.rotationEnabled && rotation != null) {
            stack.rotateRadians(rotation.getZ(), 0.0f, 0.0f, 1.0f);
            stack.rotateRadians(rotation.getY(), 0.0f, 1.0f, 0.0f);
            stack.rotateRadians(rotation.getX(), 1.0f, 0.0f, 0.0f);
        }
        final FloatVector3 scale = this.getCurrentScale(partName);
        if (this.scaleEnabled && scale != null) {
            stack.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        return this;
    }
    
    @Override
    public AnimationController hiddenPart(@NotNull final Function<ModelPart, Boolean> hiddenPart) {
        this.hiddenPart = hiddenPart;
        return this;
    }
    
    @Nullable
    @Override
    public FloatVector3 getCurrentPosition(@NotNull final String partName) {
        if (!this.isPlaying()) {
            return null;
        }
        if (!this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).position().get(this.getProgress());
    }
    
    @Nullable
    @Override
    public FloatVector3 getCurrentRotation(@NotNull final String partName) {
        if (!this.isPlaying()) {
            return null;
        }
        if (!this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).rotation().get(this.getProgress());
    }
    
    @Nullable
    @Override
    public FloatVector3 getCurrentScale(@NotNull final String partName) {
        if (!this.isPlaying()) {
            return null;
        }
        if (!this.playing.hasPartAnimation(partName)) {
            return null;
        }
        return this.playing.getPartAnimation(partName).scale().get(this.getProgress());
    }
    
    @Override
    public float getProgressTicks() {
        final float progress = this.tickProvider.get() - this.startTick;
        if (this.speed == 1.0f) {
            return progress;
        }
        final float speedProgress = progress - this.speedChangedProgress;
        return this.speedChangedProgress + speedProgress * this.speed;
    }
    
    @Override
    public long getProgress() {
        return (long)TimeUtil.convertTicksToMilliseconds(this.getProgressTicks());
    }
    
    @Override
    public AnimationController onStop(final Consumer<ModelAnimation> onStop) {
        this.onStop = onStop;
        return this;
    }
    
    static {
        SIMPLE_ANIMATION = ((model, name, animation) -> model.getPart(name));
        SHARED_MODEL_ANIMATION = ((model, name, animation) -> {
            ModelPart modelPart = animation.getModelPart();
            if (modelPart == null) {
                modelPart = model.getPart(name);
                animation.setModelPart(modelPart);
            }
            return modelPart;
        });
    }
    
    public static class DefaultAnimationApplier implements AnimationApplier
    {
        @Override
        public void applyPosition(final Model model, final ModelPart modelPart, final FloatVector3 position, final ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setTranslation(position);
        }
        
        @Override
        public void applyRotation(final Model model, final ModelPart modelPart, final FloatVector3 rotation, final ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setRotation(rotation);
        }
        
        @Override
        public void applyScale(final Model model, final ModelPart modelPart, final FloatVector3 scale, final ModelPartAnimation animation) {
            modelPart.getAnimationTransformation().setScale(scale);
        }
    }
}
