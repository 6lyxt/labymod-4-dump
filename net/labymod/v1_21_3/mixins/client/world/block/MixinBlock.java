// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ dkm.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract dkm o();
    
    @Shadow
    public abstract dxv m();
    
    @Override
    public ResourceLocation id() {
        final alz key = ma.e.b((Object)this.o());
        return (ResourceLocation)key;
    }
    
    @Override
    public boolean isAir() {
        final dkm block = this.o();
        return block == dko.a || block == dko.nx || block == dko.nw;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.m();
    }
}
