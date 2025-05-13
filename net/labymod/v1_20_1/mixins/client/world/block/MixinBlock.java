// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ cpn.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract cpn p();
    
    @Shadow
    public abstract dcb n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)jb.f.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final cpn block = this.p();
        return block == cpo.a || block == cpo.nc || block == cpo.nb;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
