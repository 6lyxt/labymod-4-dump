// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.world.inventory;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_4.client.world.inventory.SlotAccessor;

@Mixin({ cjw.class })
public class MixinSlot implements SlotAccessor
{
    @Mutable
    @Shadow
    @Final
    public int f;
    @Mutable
    @Shadow
    @Final
    public int g;
    
    @Override
    public cjw setPosition(final int x, final int y) {
        this.f = x;
        this.g = y;
        return (cjw)this;
    }
}
