// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.abilities;

import net.labymod.api.util.function.FloatSetter;
import net.labymod.api.util.function.FloatGetter;

public class FloatAbilityOption
{
    private final FloatGetter getter;
    private final FloatSetter setter;
    
    public FloatAbilityOption(final FloatGetter getter, final FloatSetter setter) {
        this.getter = getter;
        this.setter = setter;
    }
    
    public float get() {
        return this.getter.get();
    }
    
    public void set(final float value) {
        this.setter.set(value);
    }
}
