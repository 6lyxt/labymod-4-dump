// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.abilities;

import net.labymod.api.util.function.BooleanSetter;
import net.labymod.api.util.function.BooleanGetter;

public class BooleanAbilityOption
{
    private final BooleanGetter getter;
    private final BooleanSetter setter;
    
    public BooleanAbilityOption(final BooleanGetter getter, final BooleanSetter setter) {
        this.getter = getter;
        this.setter = setter;
    }
    
    public boolean get() {
        return this.getter.get();
    }
    
    public void set(final boolean value) {
        this.setter.set(value);
    }
}
