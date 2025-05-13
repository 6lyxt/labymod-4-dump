// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ csv.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract csv p();
    
    @Shadow
    public abstract dfj n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)jb.f.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final csv block = this.p();
        return block == csw.a || block == csw.nc || block == csw.nb;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
