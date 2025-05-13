// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.abilities;

public interface PlayerAbilities
{
    BooleanAbilityOption invulnerable();
    
    BooleanAbilityOption flying();
    
    BooleanAbilityOption ableToFly();
    
    BooleanAbilityOption instaBuild();
    
    BooleanAbilityOption canBuild();
    
    FloatAbilityOption flyingSpeed();
    
    FloatAbilityOption walkingSpeed();
}
