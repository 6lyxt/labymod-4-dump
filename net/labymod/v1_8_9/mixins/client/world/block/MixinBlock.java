// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ afh.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    public abstract alz Q();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)afh.c.c((Object)this);
    }
    
    @Override
    public boolean isAir() {
        return this == afi.a;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.Q();
    }
}
