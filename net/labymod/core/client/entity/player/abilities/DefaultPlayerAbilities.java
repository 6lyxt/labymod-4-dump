// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.abilities;

import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

public class DefaultPlayerAbilities implements PlayerAbilities
{
    private final BooleanAbilityOption invulnerable;
    private final BooleanAbilityOption flying;
    private final BooleanAbilityOption ableToFly;
    private final BooleanAbilityOption instaBuild;
    private final BooleanAbilityOption canBuild;
    private final FloatAbilityOption flyingSpeed;
    private final FloatAbilityOption walkingSpeed;
    
    public DefaultPlayerAbilities(final BooleanAbilityOption invulnerable, final BooleanAbilityOption flying, final BooleanAbilityOption ableToFly, final BooleanAbilityOption instaBuild, final BooleanAbilityOption canBuild, final FloatAbilityOption flyingSpeed, final FloatAbilityOption walkingSpeed) {
        this.invulnerable = invulnerable;
        this.flying = flying;
        this.ableToFly = ableToFly;
        this.instaBuild = instaBuild;
        this.canBuild = canBuild;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }
    
    @Override
    public BooleanAbilityOption invulnerable() {
        return this.invulnerable;
    }
    
    @Override
    public BooleanAbilityOption flying() {
        return this.flying;
    }
    
    @Override
    public BooleanAbilityOption ableToFly() {
        return this.ableToFly;
    }
    
    @Override
    public BooleanAbilityOption instaBuild() {
        return this.instaBuild;
    }
    
    @Override
    public BooleanAbilityOption canBuild() {
        return this.canBuild;
    }
    
    @Override
    public FloatAbilityOption flyingSpeed() {
        return this.flyingSpeed;
    }
    
    @Override
    public FloatAbilityOption walkingSpeed() {
        return this.walkingSpeed;
    }
}
