// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ dno.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract dno o();
    
    @Shadow
    public abstract ebq m();
    
    @Override
    public ResourceLocation id() {
        final alr key = mh.e.b((Object)this.o());
        return (ResourceLocation)key;
    }
    
    @Override
    public boolean isAir() {
        final dno block = this.o();
        return block == dnq.a || block == dnq.nI || block == dnq.nH;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.m();
    }
}
