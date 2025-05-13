// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ dfy.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract dfy q();
    
    @Shadow
    public abstract dtc o();
    
    @Override
    public ResourceLocation id() {
        final akr key = lt.e.b((Object)this.q());
        return (ResourceLocation)key;
    }
    
    @Override
    public boolean isAir() {
        final dfy block = this.q();
        return block == dga.a || block == dga.nc || block == dga.nb;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.o();
    }
}
