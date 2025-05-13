// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client.world.block;

import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.Block;

@Mixin({ cpi.class })
public abstract class MixinBlock implements Block
{
    @Shadow
    protected abstract cpi q();
    
    @Shadow
    public abstract dbq o();
    
    @Override
    public ResourceLocation id() {
        return (ResourceLocation)ja.f.b((Object)this.q());
    }
    
    @Override
    public boolean isAir() {
        final cpi block = this.q();
        return block == cpj.a || block == cpj.mY || block == cpj.mX;
    }
    
    @Override
    public BlockState defaultState() {
        return (BlockState)this.o();
    }
}
