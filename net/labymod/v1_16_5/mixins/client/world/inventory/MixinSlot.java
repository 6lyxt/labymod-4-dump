// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.world.inventory;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.world.inventory.SlotAccessor;

@Mixin({ bjr.class })
public class MixinSlot implements SlotAccessor
{
    @Mutable
    @Shadow
    @Final
    public int e;
    @Mutable
    @Shadow
    @Final
    public int f;
    
    @Override
    public bjr setPosition(final int x, final int y) {
        this.e = x;
        this.f = y;
        return (bjr)this;
    }
}
