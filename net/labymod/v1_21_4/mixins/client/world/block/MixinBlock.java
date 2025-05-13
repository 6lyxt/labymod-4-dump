// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ djn.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract djn o();
    
    @Shadow
    public abstract dwy m();
    
    @Override
    public ResourceLocation id() {
        final akv key = mb.e.b((Object)this.o());
        return (ResourceLocation)key;
    }
    
    @Override
    public boolean isAir() {
        final djn block = this.o();
        return block == djp.a || block == djp.nE || block == djp.nD;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.m();
    }
}
