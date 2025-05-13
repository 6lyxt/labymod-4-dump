// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ cjt.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract cjt o();
    
    @Shadow
    public abstract cvo m();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)hm.V.b((Object)this.o());
    }
    
    @Override
    public boolean isAir() {
        final cjt block = this.o();
        return block == cju.a || block == cju.lN || block == cju.lM;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.m();
    }
}
