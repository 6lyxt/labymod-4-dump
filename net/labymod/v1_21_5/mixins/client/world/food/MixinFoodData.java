// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.world.food;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.food.FoodData;

@Mixin({ cvx.class })
public class MixinFoodData implements FoodData
{
    @Shadow
    private float d;
    @Shadow
    private int c;
    
    @Override
    public float getSaturationLevel() {
        return this.d;
    }
    
    @Override
    public int getFoodLevel() {
        return this.c;
    }
}
