// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ buo.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract buo p();
    
    @Shadow
    public abstract ceh n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)gm.Q.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final buo block = this.p();
        return block == bup.a || block == bup.lb || block == bup.la;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
