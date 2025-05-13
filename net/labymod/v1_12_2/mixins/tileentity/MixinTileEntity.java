// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.tileentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.blockentity.BlockEntity;

@Mixin({ avj.class })
public abstract class MixinTileEntity implements BlockEntity
{
    @Shadow
    public abstract et w();
    
    @Shadow
    public abstract boolean y();
    
    @NotNull
    @Override
    public BlockPosition position() {
        return (BlockPosition)this.w();
    }
    
    @Override
    public boolean isRemoved() {
        return this.y();
    }
}
