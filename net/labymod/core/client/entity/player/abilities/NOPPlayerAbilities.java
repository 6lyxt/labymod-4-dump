// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.abilities;

import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

public final class NOPPlayerAbilities implements PlayerAbilities
{
    public static final PlayerAbilities INSTANCE;
    private static final BooleanAbilityOption NOP_BOOLEAN_ABILITY_OPTION;
    private static final FloatAbilityOption NOP_FLOAT_ABILITY_OPTION;
    
    private NOPPlayerAbilities() {
    }
    
    @Override
    public BooleanAbilityOption invulnerable() {
        return NOPPlayerAbilities.NOP_BOOLEAN_ABILITY_OPTION;
    }
    
    @Override
    public BooleanAbilityOption flying() {
        return NOPPlayerAbilities.NOP_BOOLEAN_ABILITY_OPTION;
    }
    
    @Override
    public BooleanAbilityOption ableToFly() {
        return NOPPlayerAbilities.NOP_BOOLEAN_ABILITY_OPTION;
    }
    
    @Override
    public BooleanAbilityOption instaBuild() {
        return NOPPlayerAbilities.NOP_BOOLEAN_ABILITY_OPTION;
    }
    
    @Override
    public BooleanAbilityOption canBuild() {
        return NOPPlayerAbilities.NOP_BOOLEAN_ABILITY_OPTION;
    }
    
    @Override
    public FloatAbilityOption flyingSpeed() {
        return NOPPlayerAbilities.NOP_FLOAT_ABILITY_OPTION;
    }
    
    @Override
    public FloatAbilityOption walkingSpeed() {
        return NOPPlayerAbilities.NOP_FLOAT_ABILITY_OPTION;
    }
    
    static {
        INSTANCE = new NOPPlayerAbilities();
        NOP_BOOLEAN_ABILITY_OPTION = new BooleanAbilityOption(() -> false, value -> {});
        NOP_FLOAT_ABILITY_OPTION = new FloatAbilityOption(() -> 0.0f, value -> {});
    }
}
