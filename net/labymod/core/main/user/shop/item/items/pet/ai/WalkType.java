// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.pet.ai;

import java.util.HashMap;
import java.util.Collections;
import java.util.Map;

public final class WalkType
{
    private static final Map<String, WalkType> WALK_TYPES;
    public static final WalkType CROUCHING;
    public static final WalkType WALK;
    public static final WalkType SPRINT;
    public static final WalkType FLYING;
    public static final WalkType RUN;
    private final String name;
    private final float multiplier;
    
    private WalkType(final String name, final float multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }
    
    public static WalkType register(final String name, final float multiplier) {
        return WalkType.WALK_TYPES.computeIfAbsent(name, n -> new WalkType(n, multiplier));
    }
    
    public static Map<String, WalkType> getWalkTypes() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends WalkType>)WalkType.WALK_TYPES);
    }
    
    public String getName() {
        return this.name;
    }
    
    public float getMultiplier() {
        return this.multiplier;
    }
    
    static {
        WALK_TYPES = new HashMap<String, WalkType>();
        CROUCHING = register("crouch", 0.25f);
        WALK = register("walk", 1.0f);
        SPRINT = register("sprint", 1.2f);
        FLYING = register("flying", 1.55f);
        RUN = register("run", 1.7f);
    }
}
