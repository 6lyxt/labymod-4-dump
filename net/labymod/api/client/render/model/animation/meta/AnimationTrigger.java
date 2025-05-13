// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation.meta;

import java.util.Objects;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Map;

public class AnimationTrigger
{
    private static final Map<String, AnimationTrigger> TRIGGERS;
    public static final AnimationTrigger NONE;
    public static final AnimationTrigger MOVING;
    public static final AnimationTrigger IDLE;
    public static final AnimationTrigger START_MOVING;
    public static final AnimationTrigger STOP_MOVING;
    public static final AnimationTrigger START_SNEAKING;
    public static final AnimationTrigger STOP_SNEAKING;
    public static final AnimationTrigger SNEAK_MOVING;
    public static final AnimationTrigger SNEAK_IDLE;
    public static final AnimationTrigger START_FIRST;
    public static final AnimationTrigger STOP_FIRST;
    public static final AnimationTrigger FIRST_MOVING;
    public static final AnimationTrigger FIRST_IDLE;
    public static final AnimationTrigger FIRST_START_MOVING;
    public static final AnimationTrigger FIRST_STOP_MOVING;
    public static final AnimationTrigger FIRST_START_SNEAKING;
    public static final AnimationTrigger FIRST_STOP_SNEAKING;
    public static final AnimationTrigger FIRST_SNEAK_MOVING;
    public static final AnimationTrigger FIRST_SNEAK_IDLE;
    public static final AnimationTrigger BLOCKING;
    public static final AnimationTrigger BLOCK_ATTACK;
    private final String name;
    private final int mask;
    
    private AnimationTrigger(final String name, final int mask) {
        this.name = name;
        this.mask = mask;
    }
    
    private static AnimationTrigger create(final String name) {
        return create(name, Builder::build);
    }
    
    private static AnimationTrigger create(final String name, final Consumer<Builder> builderConsumer) {
        return AnimationTrigger.TRIGGERS.computeIfAbsent(name, triggerName -> {
            final Builder builder = new Builder(triggerName);
            builderConsumer.accept(builder);
            return builder.build();
        });
    }
    
    @Nullable
    public static AnimationTrigger getByName(final String name) {
        for (final AnimationTrigger trigger : AnimationTrigger.TRIGGERS.values()) {
            if (trigger.getName().equalsIgnoreCase(name)) {
                return trigger;
            }
        }
        return null;
    }
    
    @Contract(pure = true)
    @NotNull
    public static Collection<AnimationTrigger> values() {
        return AnimationTrigger.TRIGGERS.values();
    }
    
    public static AnimationTrigger getMovingOrIdle(final boolean moving, final boolean crouching) {
        return getMovingOrIdle(moving, crouching, false);
    }
    
    public static AnimationTrigger getMovingOrIdle(final boolean moving, final boolean crouching, final boolean firstPerson) {
        return moving ? getMoving(firstPerson, crouching) : getIdle(firstPerson, crouching);
    }
    
    public static AnimationTrigger getMoving(final boolean firstPerson, final boolean crouching) {
        if (firstPerson) {
            return crouching ? AnimationTrigger.FIRST_SNEAK_MOVING : AnimationTrigger.FIRST_MOVING;
        }
        return crouching ? AnimationTrigger.SNEAK_MOVING : AnimationTrigger.MOVING;
    }
    
    public static AnimationTrigger getIdle(final boolean firstPerson, final boolean crouching) {
        if (firstPerson) {
            return crouching ? AnimationTrigger.FIRST_SNEAK_IDLE : AnimationTrigger.FIRST_IDLE;
        }
        return crouching ? AnimationTrigger.SNEAK_IDLE : AnimationTrigger.IDLE;
    }
    
    public static AnimationTrigger getSneakingToggle(final boolean firstPerson, final boolean start) {
        if (firstPerson) {
            return start ? AnimationTrigger.FIRST_START_SNEAKING : AnimationTrigger.FIRST_STOP_SNEAKING;
        }
        return start ? AnimationTrigger.START_SNEAKING : AnimationTrigger.STOP_SNEAKING;
    }
    
    public static AnimationTrigger getMovingToggle(final boolean firstPerson, final boolean start) {
        if (firstPerson) {
            return start ? AnimationTrigger.FIRST_START_MOVING : AnimationTrigger.FIRST_STOP_MOVING;
        }
        return start ? AnimationTrigger.START_MOVING : AnimationTrigger.STOP_MOVING;
    }
    
    public static AnimationTrigger getAlternateTrigger(final AnimationTrigger trigger) {
        String triggerName = trigger.getName();
        if (trigger.isFirstPerson()) {
            triggerName = triggerName.substring(6);
        }
        final AnimationTrigger alternateTrigger = getByName(triggerName);
        return (alternateTrigger == null) ? trigger : alternateTrigger;
    }
    
    public boolean isIdle() {
        return this.hasFlag(1);
    }
    
    public boolean isSneaking() {
        return this.hasFlag(2);
    }
    
    public boolean isMoving() {
        return this.hasFlag(4);
    }
    
    public boolean isFirstPerson() {
        return this.hasFlag(8);
    }
    
    public String getName() {
        return this.name;
    }
    
    private boolean hasFlag(final int flag) {
        return Mask.hasFlag(this.mask, flag);
    }
    
    @Override
    public String toString() {
        final StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("AnimationTrigger[");
        bobTheBuilder.append("name=").append(this.getName());
        if (!this.hasFlag(0)) {
            bobTheBuilder.append(", types=");
            if (this.isIdle()) {
                bobTheBuilder.append("idle ");
            }
            if (this.isSneaking()) {
                bobTheBuilder.append("sneaking ");
            }
            if (this.isMoving()) {
                bobTheBuilder.append("moving ");
            }
            if (this.isFirstPerson()) {
                bobTheBuilder.append("firstPerson ");
            }
        }
        bobTheBuilder.append("]");
        return bobTheBuilder.toString();
    }
    
    static {
        TRIGGERS = new HashMap<String, AnimationTrigger>();
        NONE = create("none");
        MOVING = create("moving", Builder::moving);
        IDLE = create("idle", Builder::idle);
        START_MOVING = create("start_moving", Builder::moving);
        STOP_MOVING = create("stop_moving", Builder::moving);
        START_SNEAKING = create("start_sneaking", Builder::sneaking);
        STOP_SNEAKING = create("stop_sneaking", Builder::sneaking);
        SNEAK_MOVING = create("sneak_moving", builder -> builder.sneaking().moving());
        SNEAK_IDLE = create("sneak_idle", builder -> builder.sneaking().idle());
        START_FIRST = create("start_first", Builder::firstPerson);
        STOP_FIRST = create("stop_first", Builder::firstPerson);
        FIRST_MOVING = create("first_moving", builder -> builder.firstPerson().moving());
        FIRST_IDLE = create("first_idle", builder -> builder.firstPerson().idle());
        FIRST_START_MOVING = create("first_start_moving", builder -> builder.firstPerson().moving());
        FIRST_STOP_MOVING = create("first_stop_moving", builder -> builder.firstPerson().moving());
        FIRST_START_SNEAKING = create("first_start_sneaking", builder -> builder.firstPerson().sneaking());
        FIRST_STOP_SNEAKING = create("first_stop_sneaking", builder -> builder.firstPerson().sneaking());
        FIRST_SNEAK_MOVING = create("first_sneak_moving", builder -> builder.firstPerson().sneaking().moving());
        FIRST_SNEAK_IDLE = create("first_sneak_idle", builder -> builder.firstPerson().sneaking().idle());
        BLOCKING = create("blocking");
        BLOCK_ATTACK = create("block_attack");
    }
    
    public static class Mask
    {
        public static final int NONE = 0;
        public static final int IDLE = 1;
        public static final int SNEAKING = 2;
        public static final int MOVING = 4;
        public static final int FIRST_PERSON = 8;
        
        public static boolean hasFlag(final int mask, final int flag) {
            return (mask & flag) != 0x0;
        }
    }
    
    private static class Builder
    {
        private final String name;
        private int mask;
        
        public Builder(final String name) {
            this.mask = 0;
            this.name = name;
        }
        
        public Builder idle() {
            this.mask |= 0x1;
            return this;
        }
        
        public Builder sneaking() {
            this.mask |= 0x2;
            return this;
        }
        
        public Builder moving() {
            this.mask |= 0x4;
            return this;
        }
        
        public Builder firstPerson() {
            this.mask |= 0x8;
            return this;
        }
        
        public AnimationTrigger build() {
            Objects.requireNonNull(this.name, "name must not be null");
            return new AnimationTrigger(this.name, this.mask);
        }
    }
}
