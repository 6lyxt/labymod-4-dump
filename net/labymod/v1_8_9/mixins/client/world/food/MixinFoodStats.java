// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.world.food;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.food.FoodData;

@Mixin({ xg.class })
public class MixinFoodStats implements FoodData
{
    @Shadow
    private float b;
    @Shadow
    private int a;
    
    @Override
    public float getSaturationLevel() {
        return this.b;
    }
    
    @Override
    public int getFoodLevel() {
        return this.a;
    }
}
