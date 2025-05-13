// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model.animation.meta;

import java.util.Arrays;
import net.labymod.api.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.Collection;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public class AnimationMeta<T>
{
    private static final Logging LOGGING;
    public static AnimationMeta<List<AnimationTrigger>> TRIGGER;
    public static final AnimationMeta<Integer> PROBABILITY;
    public static final AnimationMeta<Boolean> FORCE;
    public static final AnimationMeta<Boolean> QUEUE;
    public static final AnimationMeta<Float> SPEED;
    public static final AnimationMeta<List<AnimationCondition>> CONDITION;
    private static final Collection<AnimationMeta<?>> DEFAULTS;
    private final String key;
    private final String shortcut;
    private final Function<String, T> parser;
    
    public static Collection<AnimationMeta<?>> defaults() {
        return AnimationMeta.DEFAULTS;
    }
    
    public AnimationMeta(final String key, final String shortcut, final Function<String, T> parser) {
        this.key = key;
        this.shortcut = shortcut;
        this.parser = parser;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getShortcut() {
        return this.shortcut;
    }
    
    public Function<String, T> getParser() {
        return this.parser;
    }
    
    static {
        LOGGING = Logging.create("Animation Meta");
        AnimationMeta.TRIGGER = new AnimationMeta<List<AnimationTrigger>>("trigger", "t", value -> {
            if ("*".equals(value)) {
                return new ArrayList(AnimationTrigger.values());
            }
            else {
                final ArrayList<AnimationTrigger> list = new ArrayList<AnimationTrigger>();
                if (value.contains(",")) {
                    value.split(",");
                    final String[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final String triggerName = array[i];
                        final AnimationTrigger trigger = AnimationTrigger.getByName(triggerName);
                        if (trigger != null) {
                            list.add(trigger);
                        }
                    }
                    return list;
                }
                else {
                    final AnimationTrigger trigger2 = AnimationTrigger.getByName(value);
                    if (trigger2 != null) {
                        list.add(trigger2);
                    }
                    return list;
                }
            }
        });
        PROBABILITY = new AnimationMeta<Integer>("probability", "p", Integer::parseInt);
        FORCE = new AnimationMeta<Boolean>("force", "f", Boolean::parseBoolean);
        QUEUE = new AnimationMeta<Boolean>("queue", "q", Boolean::parseBoolean);
        SPEED = new AnimationMeta<Float>("speed", "s", Float::parseFloat);
        CONDITION = new AnimationMeta<List<AnimationCondition>>("condition", "c", value -> {
            if (value == null) {
                return Collections.emptyList();
            }
            else {
                try {
                    final ArrayList<AnimationCondition> conditions = new ArrayList<AnimationCondition>();
                    value.split(",");
                    final String[] array2;
                    int j = 0;
                    for (int length2 = array2.length; j < length2; ++j) {
                        final String condition = array2[j];
                        conditions.add(AnimationCondition.findCondition(StringUtil.toUppercase(condition)));
                    }
                    return conditions;
                }
                catch (final Exception exception) {
                    AnimationMeta.LOGGING.error("Invalid condition: {}", value);
                    return Collections.emptyList();
                }
            }
        });
        DEFAULTS = Arrays.asList(AnimationMeta.TRIGGER, AnimationMeta.PROBABILITY, AnimationMeta.FORCE, AnimationMeta.QUEUE, AnimationMeta.SPEED, AnimationMeta.CONDITION);
    }
}
