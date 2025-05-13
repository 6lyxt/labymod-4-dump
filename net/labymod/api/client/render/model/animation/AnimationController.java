// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation;

import java.util.function.Consumer;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.ModelPart;
import java.util.function.Function;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.function.FloatSupplier;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public interface AnimationController
{
    AnimationController playNext(@NotNull final ModelAnimation p0);
    
    AnimationController swap(@Nullable final ModelAnimation p0);
    
    AnimationController stop();
    
    boolean isPlaying();
    
    AnimationController queue(@NotNull final ModelAnimation p0);
    
    boolean isQueued(@NotNull final ModelAnimation p0);
    
    @Nullable
    ModelAnimation getPlaying();
    
    float getSpeed();
    
    AnimationController speed(final float p0);
    
    int getMaxQueueSize();
    
    AnimationController maxQueueSize(final int p0);
    
    boolean isAnimateStrict();
    
    AnimationController animateStrict(final boolean p0);
    
    AnimationController tickProvider(@NotNull final FloatSupplier p0);
    
    @NotNull
    AnimationApplier animationApplier();
    
    AnimationController animationApplier(@NotNull final AnimationApplier p0);
    
    @NotNull
    PartTransformer partTransformer();
    
    AnimationController partTransformer(@NotNull final PartTransformer p0);
    
    AnimationController enableTransformation(final TransformationType... p0);
    
    AnimationController disableTransformation(final TransformationType... p0);
    
    AnimationController applyAnimation(final Model p0, final String... p1);
    
    AnimationController transform(final Stack p0, final String p1);
    
    AnimationController hiddenPart(@NotNull final Function<ModelPart, Boolean> p0);
    
    @Nullable
    FloatVector3 getCurrentPosition(@NotNull final String p0);
    
    @Nullable
    FloatVector3 getCurrentRotation(@NotNull final String p0);
    
    @Nullable
    FloatVector3 getCurrentScale(@NotNull final String p0);
    
    float getProgressTicks();
    
    long getProgress();
    
    AnimationController onStop(final Consumer<ModelAnimation> p0);
    
    public interface PartTransformer
    {
        default String remapName(final Model model, final String partName) {
            return partName;
        }
        
        default void preAnimationApply(final Model model, final String partName, final ModelPart modelPart, final ModelPartAnimation animation) {
        }
        
        default void postAnimationApply(final Model model, final String partName, final ModelPart modelPart, final ModelPartAnimation animation) {
        }
    }
    
    public interface AnimationApplier
    {
        void applyPosition(final Model p0, final ModelPart p1, final FloatVector3 p2, final ModelPartAnimation p3);
        
        void applyRotation(final Model p0, final ModelPart p1, final FloatVector3 p2, final ModelPartAnimation p3);
        
        void applyScale(final Model p0, final ModelPart p1, final FloatVector3 p2, final ModelPartAnimation p3);
    }
}
