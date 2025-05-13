// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.world.inventory;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.world.inventory.SlotAccessor;

@Mixin({ cax.class })
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
    public cax setPosition(final int x, final int y) {
        this.f = x;
        this.g = y;
        return (cax)this;
    }
}
