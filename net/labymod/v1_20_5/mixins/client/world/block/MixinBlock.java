// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ dfa.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract dfa q();
    
    @Shadow
    public abstract dsd o();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)lp.e.b((Object)this.q());
    }
    
    @Override
    public boolean isAir() {
        final dfa block = this.q();
        return block == dfc.a || block == dfc.nc || block == dfc.nb;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.o();
    }
}
