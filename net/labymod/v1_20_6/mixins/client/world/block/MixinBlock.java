// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ dfb.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract dfb q();
    
    @Shadow
    public abstract dse o();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)lp.e.b((Object)this.q());
    }
    
    @Override
    public boolean isAir() {
        final dfb block = this.q();
        return block == dfd.a || block == dfd.nc || block == dfd.nb;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.o();
    }
}
