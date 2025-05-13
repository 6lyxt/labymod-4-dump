// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ aow.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    public abstract awt t();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)aow.h.b((Object)this);
    }
    
    @Override
    public boolean isAir() {
        return this == aox.a;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.t();
    }
}
