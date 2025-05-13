// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.animation;

import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.model.animation.TransformationType;
import java.util.Collections;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import java.util.HashMap;
import java.util.HashSet;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.main.user.shop.emote.abort.AbortAction;
import java.util.Map;
import java.util.Collection;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.util.function.FloatSupplier;

public class EmoteAnimationStorage
{
    private static final FloatSupplier TICK_PROVIDER;
    private static final AnimationController.AnimationApplier ANIMATION_APPLIER;
    private static final AnimationController.PartTransformer PART_TRANSFORMER;
    private static final long SUSPEND_TIMEOUT = 500L;
    private final AnimationController animationController;
    private final Collection<String> suspendedParts;
    private final Map<AbortAction, Long> lastActionMetTimes;
    private EmoteItem emote;
    private Model propsModel;
    private ModelBuffer propsModelBuffer;
    private Model steveModel;
    private Model alexModel;
    private boolean abortRequested;
    private boolean lastMoving;
    private boolean lastSneaking;
    private Perspective lastPerspective;
    
    public EmoteAnimationStorage() {
        this.animationController = new DefaultAnimationController().tickProvider(EmoteAnimationStorage.TICK_PROVIDER).animationApplier(EmoteAnimationStorage.ANIMATION_APPLIER).partTransformer(EmoteAnimationStorage.PART_TRANSFORMER);
        this.suspendedParts = new HashSet<String>();
        this.lastActionMetTimes = new HashMap<AbortAction, Long>();
    }
    
    public void playEmote(final EmoteItem emote) {
        this.deactivate();
        this.suspendedParts.clear();
        this.lastActionMetTimes.clear();
        this.emote = emote;
        final Model propsModel = emote.getPropsModel();
        if (propsModel != null) {
            this.propsModel = propsModel.copy();
            if (this.propsModel != null) {
                this.propsModel.setTextureLocation(emote.getPropsTextureLocation());
                this.propsModelBuffer = new DefaultModelBuffer(this.propsModel);
            }
        }
        final Model steveModel = emote.getSteveModel();
        if (steveModel != null) {
            this.steveModel = steveModel.copy();
        }
        final Model alexModel = emote.getAlexModel();
        if (alexModel != null) {
            this.alexModel = alexModel.copy();
        }
        this.abortRequested = false;
        this.animationController.playNext(emote.getStartAnimation());
    }
    
    public void suspendAllParts() {
        this.updateSuspendedParts(null);
    }
    
    public void updateSuspendedParts(final Collection<String> suspendedParts) {
        if (!this.isActive() || !this.isPlaying() || this.suspendedParts.equals(suspendedParts)) {
            return;
        }
        final long progress = this.animationController.getProgress();
        final long suspendEnd = progress + 500L;
        final Collection<TransformationType> disabledSuspensions = (Collection<TransformationType>)((suspendedParts == null) ? Collections.emptyList() : this.emote.getDisabledSuspensions());
        final String playingName = this.animationController.getPlaying().getName();
        final ModelAnimation modelAnimation = this.emote.animationContainer().getByName(playingName).copy((partName, partAnimation) -> {
            if (progress > partAnimation.getLength()) {
                return null;
            }
            else {
                final boolean alreadySuspended = this.suspendedParts.contains(partName);
                final boolean suspend = suspendedParts == null || suspendedParts.contains(partName);
                if (alreadySuspended && suspend) {
                    return null;
                }
                else if (!alreadySuspended && !suspend) {
                    return partAnimation;
                }
                else {
                    final Collection<TransformationType> partDisabledSuspensions = new HashSet<TransformationType>(disabledSuspensions);
                    TransformationType.values();
                    final TransformationType[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final TransformationType type = array[i];
                        if (progress > partAnimation.transformationByType(type).getLength()) {
                            partDisabledSuspensions.add(type);
                        }
                    }
                    if (suspend) {
                        final FloatVector3 position = partAnimation.position().get(progress);
                        final FloatVector3 rotation = partAnimation.rotation().get(progress);
                        final FloatVector3 scale = partAnimation.scale().get(progress);
                        partAnimation.filterKeyframes((transformation, keyframe) -> partDisabledSuspensions.contains(transformation.type()) || keyframe.getTimestamp() < progress);
                        partAnimation.addKeyframes(progress, true, position, rotation, scale, partDisabledSuspensions).addKeyframes(suspendEnd, false, new FloatVector3(), new FloatVector3(rotation).map(MathHelper::roundRadians), new FloatVector3(1.0f), partDisabledSuspensions);
                    }
                    else {
                        final FloatVector3 position2 = partAnimation.position().get(suspendEnd);
                        final FloatVector3 rotation2 = partAnimation.rotation().get(suspendEnd);
                        final FloatVector3 scale2 = partAnimation.scale().get(suspendEnd);
                        partAnimation.filterKeyframes((transformation, keyframe) -> disabledSuspensions.contains(transformation.type()) || keyframe.getTimestamp() > suspendEnd);
                        partAnimation.addKeyframes(progress, true, new FloatVector3(), new FloatVector3(rotation2).map(MathHelper::roundRadians), new FloatVector3(1.0f), partDisabledSuspensions).addKeyframes(suspendEnd, true, position2, rotation2, scale2, partDisabledSuspensions);
                    }
                    return partAnimation;
                }
            }
        });
        if (suspendedParts == null) {
            modelAnimation.removeMeta(EmoteAnimationMeta.TRIGGER_EMOTE);
        }
        this.animationController.swap(modelAnimation);
        this.suspendedParts.clear();
        if (suspendedParts != null) {
            this.suspendedParts.addAll(suspendedParts);
        }
    }
    
    public boolean isActive() {
        return this.emote != null && this.emote.animationContainer().getAnimations() != null;
    }
    
    public boolean isPlaying() {
        return this.animationController.isPlaying();
    }
    
    public boolean hasProps() {
        return this.propsModel != null && this.propsModelBuffer != null;
    }
    
    public boolean hasPlayerModel() {
        return this.steveModel != null && this.alexModel != null;
    }
    
    public void deactivate() {
        this.emote = null;
        this.propsModel = null;
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
        }
    }
    
    public void requestAbort() {
        this.abortRequested = true;
    }
    
    public void actionMet(final AbortAction abortAction) {
        this.lastActionMetTimes.put(abortAction, TimeUtil.getMillis());
    }
    
    public boolean isActionMetDelay(final AbortAction abortAction) {
        final long lastActionMetTime = this.lastActionMetTimes.getOrDefault(abortAction, 0L);
        return TimeUtil.getMillis() - lastActionMetTime < 500L;
    }
    
    public AnimationController animationController() {
        return this.animationController;
    }
    
    public Collection<String> getSuspendedParts() {
        return this.suspendedParts;
    }
    
    public EmoteItem getEmote() {
        return this.emote;
    }
    
    public Model getPropsModel() {
        return this.propsModel;
    }
    
    public ModelBuffer getPropsModelBuffer() {
        return this.propsModelBuffer;
    }
    
    public Model getSteveModel() {
        return this.steveModel;
    }
    
    public Model getAlexModel() {
        return this.alexModel;
    }
    
    public boolean isAbortRequested() {
        return this.abortRequested;
    }
    
    public boolean isLastMoving() {
        return this.lastMoving;
    }
    
    public void setLastMoving(final boolean lastMoving) {
        this.lastMoving = lastMoving;
    }
    
    public boolean isLastSneaking() {
        return this.lastSneaking;
    }
    
    public void setLastSneaking(final boolean lastSneaking) {
        this.lastSneaking = lastSneaking;
    }
    
    public Perspective getLastPerspective() {
        return this.lastPerspective;
    }
    
    public void setLastPerspective(final Perspective lastPerspective) {
        this.lastPerspective = lastPerspective;
    }
    
    static {
        TICK_PROVIDER = (() -> {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            final ClientPlayer clientPlayer = minecraft.getClientPlayer();
            if (clientPlayer == null) {
                return Laby.references().gameTickProvider().get();
            }
            else {
                return clientPlayer.getRenderTick(minecraft.getPartialTicks());
            }
        });
        ANIMATION_APPLIER = new EmoteAnimationApplier();
        PART_TRANSFORMER = new EmotePartTransformer();
    }
}
