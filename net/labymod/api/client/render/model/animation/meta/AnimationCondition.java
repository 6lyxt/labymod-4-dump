// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation.meta;

import net.labymod.api.util.function.Functional;
import java.util.HashMap;
import net.labymod.api.client.entity.Entity;
import java.util.function.Function;
import java.util.Map;

public enum AnimationCondition
{
    NO_MOTION(entity -> entity.getForwardMotion() >= 0.0f), 
    MOTION_FORWARD(entity -> entity.getForwardMotion() != 0.0f), 
    MOTION_BACKWARDS(entity -> entity.getForwardMotion() <= 0.0f), 
    SNEAKING(entity -> !entity.isCrouching()), 
    NOT_SNEAKING(Entity::isCrouching), 
    IN_WATER(entity -> !entity.isInWater()), 
    IN_NOT_WATER(Entity::isInWater), 
    ON_WATER(entity -> !entity.isOnGround()), 
    IN_AIR(Entity::isOnGround);
    
    private static final Map<String, AnimationCondition> VALUES;
    private final Function<Entity, Boolean> condition;
    
    private AnimationCondition(final Function<Entity, Boolean> condition) {
        this.condition = condition;
    }
    
    public boolean apply(final Entity entity) {
        return this.condition.apply(entity);
    }
    
    public static AnimationCondition findCondition(final String name) {
        final AnimationCondition animationCondition = AnimationCondition.VALUES.get(name);
        if (animationCondition == null) {
            throw new IllegalArgumentException("No enum constant " + AnimationCondition.class.getCanonicalName() + "." + name);
        }
        return animationCondition;
    }
    
    static {
        VALUES = Functional.of((HashMap)new HashMap(), map -> {
            values();
            final AnimationCondition[] array;
            int i = 0;
            for (int length = array.length; i < length; ++i) {
                final AnimationCondition value = array[i];
                map.put(value.name(), value);
            }
        });
    }
}
