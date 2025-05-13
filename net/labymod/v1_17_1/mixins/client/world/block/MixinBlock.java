// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ bzp.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract bzp p();
    
    @Shadow
    public abstract ckt n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)gw.W.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final bzp block = this.p();
        return block == bzq.a || block == bzq.lp || block == bzq.lo;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
