// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop;

import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.entity.Entity;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.List;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import java.util.Map;
import java.util.Random;

public class AnimationContainer
{
    private static final Random RANDOM;
    private final Map<AnimationTrigger, List<ModelAnimation>> animationsByTrigger;
    private Collection<ModelAnimation> animations;
    private AnimationTrigger trigger;
    
    public AnimationContainer() {
        this(null);
    }
    
    public AnimationContainer(final Collection<ModelAnimation> animations) {
        this.trigger = AnimationTrigger.NONE;
        this.animationsByTrigger = new HashMap<AnimationTrigger, List<ModelAnimation>>();
        this.updateAnimations(animations);
    }
    
    public void updateAnimations(final Collection<ModelAnimation> animations) {
        this.animations = animations;
        this.animationsByTrigger.clear();
        if (animations == null) {
            return;
        }
        for (final ModelAnimation animation : animations) {
            final List<AnimationTrigger> triggers = animation.getTriggers();
            if (triggers.isEmpty()) {
                this.animationsByTrigger.computeIfAbsent(AnimationTrigger.NONE, key -> new ArrayList()).add(animation);
            }
            else {
                for (final AnimationTrigger trigger : triggers) {
                    this.animationsByTrigger.computeIfAbsent(trigger, key -> new ArrayList()).add(animation);
                }
            }
        }
    }
    
    public boolean hasTriggerAnimations() {
        final List<ModelAnimation> noTriggerAnimations = this.animationsByTrigger.get(AnimationTrigger.NONE);
        return ((noTriggerAnimations == null) ? 0 : noTriggerAnimations.size()) != ((this.animations == null) ? 0 : this.animations.size());
    }
    
    public List<ModelAnimation> getTriggerAnimations(final AnimationTrigger animationTrigger) {
        return this.animationsByTrigger.getOrDefault(animationTrigger, Collections.emptyList());
    }
    
    public boolean isTriggerPresent(final AnimationTrigger trigger) {
        return !this.getTriggerAnimations(trigger).isEmpty();
    }
    
    public ModelAnimation getByTrigger(final AnimationTrigger animationTrigger) {
        return this.getByTrigger(animationTrigger, null);
    }
    
    public ModelAnimation getByTrigger(final AnimationTrigger animationTrigger, final Entity entity) {
        final List<ModelAnimation> triggerAnimations = this.animationsByTrigger.get(animationTrigger);
        if (triggerAnimations == null || triggerAnimations.isEmpty()) {
            return null;
        }
        final List<ModelAnimation> matchingAnimations = new ArrayList<ModelAnimation>();
        for (final ModelAnimation animation : triggerAnimations) {
            if (!animation.meetsConditions(entity)) {
                continue;
            }
            final Integer probability = animation.getMeta(AnimationMeta.PROBABILITY);
            if (probability == null) {
                return animation;
            }
            for (int i = 0; i < probability; ++i) {
                matchingAnimations.add(animation);
            }
        }
        return matchingAnimations.isEmpty() ? null : matchingAnimations.get(AnimationContainer.RANDOM.nextInt(matchingAnimations.size()));
    }
    
    public ModelAnimation handleAnimationTrigger(AnimationTrigger trigger, final AnimationController animationController, final Entity entity) {
        if (trigger.isFirstPerson() && !this.isTriggerPresent(trigger)) {
            trigger = AnimationTrigger.getAlternateTrigger(trigger);
        }
        final ModelAnimation animation = this.getByTrigger(trigger, entity);
        this.trigger = trigger;
        if (animation == null) {
            return null;
        }
        final boolean force = animation.getMetaDefault(AnimationMeta.FORCE, false);
        final boolean queue = animation.getMetaDefault(AnimationMeta.QUEUE, false);
        final ModelAnimation currentAnimation = animationController.getPlaying();
        final boolean sameAnimation = currentAnimation == animation || animationController.isQueued(animation) || (currentAnimation != null && currentAnimation.hasTrigger(trigger));
        if (force || !animationController.isPlaying() || (!sameAnimation && !queue)) {
            animationController.playNext(animation);
            return animation;
        }
        if (sameAnimation) {
            return animation;
        }
        animationController.queue(animation);
        if (currentAnimation != null) {
            final Float currentSpeed = currentAnimation.getMeta(AnimationMeta.SPEED);
            if (currentSpeed != null && animationController.getSpeed() == 1.0f) {
                animationController.speed(currentSpeed);
            }
        }
        return animation;
    }
    
    public ModelAnimation getByName(final String name) {
        for (final ModelAnimation animation : this.animations) {
            if (animation.getName().equalsIgnoreCase(name)) {
                return animation;
            }
        }
        return null;
    }
    
    public Collection<ModelAnimation> getAnimations() {
        return this.animations;
    }
    
    public AnimationTrigger getTrigger() {
        return this.trigger;
    }
    
    static {
        RANDOM = new Random();
    }
}
