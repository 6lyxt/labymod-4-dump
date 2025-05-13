// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation;

import java.util.function.BiFunction;
import java.util.Collections;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.render.model.animation.meta.AnimationCondition;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import java.util.Iterator;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class ModelAnimation
{
    private final String name;
    private final long length;
    private final List<NamedModelPartAnimation> partAnimations;
    private final List<AnimationMetaContainer> meta;
    
    public ModelAnimation(final String name) {
        this(name, -1L);
    }
    
    public ModelAnimation(final String name, final long length) {
        this.name = name;
        this.length = length;
        this.partAnimations = new ArrayList<NamedModelPartAnimation>();
        this.meta = new ArrayList<AnimationMetaContainer>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addPartAnimation(final String name, final ModelPartAnimation partAnimation) {
        this.partAnimations.add(new NamedModelPartAnimation(name, partAnimation));
    }
    
    public ModelPartAnimation getPartAnimation(final String name) {
        for (final NamedModelPartAnimation animation : this.partAnimations) {
            if (Objects.equals(name, animation.getName())) {
                return animation.getAnimation();
            }
        }
        final ModelPartAnimation animation2 = new ModelPartAnimation(this);
        this.addPartAnimation(name, animation2);
        return animation2;
    }
    
    public boolean hasPartAnimation(final String name) {
        for (final NamedModelPartAnimation animation : this.partAnimations) {
            if (Objects.equals(name, animation.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public List<NamedModelPartAnimation> getPartAnimations() {
        return this.partAnimations;
    }
    
    public <T> void addMeta(final AnimationMeta<T> meta, final T value) {
        this.meta.add(new AnimationMetaContainer(meta, value));
    }
    
    public <T> void removeMeta(final AnimationMeta<T> meta) {
        this.meta.removeIf(container -> Objects.equals(meta, container.getAnimationMeta()));
    }
    
    public <T> void parseAndAddMeta(final AnimationMeta<T> meta, final String value) {
        this.addMeta(meta, meta.getParser().apply(value));
    }
    
    public <T> T getMeta(final AnimationMeta<T> meta) {
        return this.getMetaDefault(meta, (T)null);
    }
    
    public <T> T getMetaDefault(final AnimationMeta<T> meta, final T def) {
        for (final AnimationMetaContainer container : this.meta) {
            if (Objects.equals(meta, container.getAnimationMeta())) {
                return (T)container.getValue();
            }
        }
        return def;
    }
    
    public boolean meetsConditions(final Entity entity) {
        final List<AnimationCondition> conditions = this.getMeta(AnimationMeta.CONDITION);
        if (conditions == null || conditions.isEmpty()) {
            return true;
        }
        if (entity == null) {
            return false;
        }
        for (final AnimationCondition condition : conditions) {
            if (condition.apply(entity)) {
                return false;
            }
        }
        return true;
    }
    
    public List<AnimationTrigger> getTriggers() {
        return this.getMetaDefault(AnimationMeta.TRIGGER, Collections.emptyList());
    }
    
    public boolean hasTrigger(final AnimationTrigger trigger) {
        return this.getTriggers().contains(trigger);
    }
    
    public long getLength() {
        long length = 0L;
        for (final NamedModelPartAnimation animation : this.partAnimations) {
            length = Math.max(length, animation.getAnimation().getLength());
        }
        return Math.max(length, this.length);
    }
    
    public ModelAnimation copy() {
        return this.copy((partName, partAnimation) -> partAnimation);
    }
    
    public ModelAnimation copy(final BiFunction<String, ModelPartAnimation, ModelPartAnimation> transformer) {
        final ModelAnimation copy = new ModelAnimation(this.name);
        for (final NamedModelPartAnimation partAnimation : this.partAnimations) {
            final ModelPartAnimation transformed = transformer.apply(partAnimation.getName(), new ModelPartAnimation(copy, partAnimation.getAnimation().position().copy(), partAnimation.getAnimation().rotation().copy(), partAnimation.getAnimation().scale().copy()));
            if (transformed != null) {
                copy.addPartAnimation(partAnimation.getName(), transformed);
            }
        }
        for (final AnimationMetaContainer meta : this.meta) {
            copy.addMeta(meta.getAnimationMeta(), meta.getValue());
        }
        return copy;
    }
    
    public static class NamedModelPartAnimation
    {
        private final String name;
        private final ModelPartAnimation animation;
        
        public NamedModelPartAnimation(final String name, final ModelPartAnimation animation) {
            this.name = name;
            this.animation = animation;
        }
        
        public String getName() {
            return this.name;
        }
        
        public ModelPartAnimation getAnimation() {
            return this.animation;
        }
    }
    
    public static class AnimationMetaContainer
    {
        private final AnimationMeta<?> animationMeta;
        private final Object value;
        
        public AnimationMetaContainer(final AnimationMeta<?> animationMeta, final Object value) {
            this.animationMeta = animationMeta;
            this.value = value;
        }
        
        public AnimationMeta<?> getAnimationMeta() {
            return this.animationMeta;
        }
        
        public Object getValue() {
            return this.value;
        }
    }
}
