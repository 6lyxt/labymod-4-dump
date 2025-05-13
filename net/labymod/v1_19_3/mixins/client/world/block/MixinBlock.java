// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ cmt.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract cmt p();
    
    @Shadow
    public abstract cyt n();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)iw.f.b((Object)this.p());
    }
    
    @Override
    public boolean isAir() {
        final cmt block = this.p();
        return block == cmu.a || block == cmu.mA || block == cmu.mz;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.n();
    }
}
