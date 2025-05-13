// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ cdq.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract cdq p();
    
    @Shadow
    public abstract cov n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)hb.U.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final cdq block = this.p();
        return block == cdr.a || block == cdr.lp || block == cdr.lo;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
