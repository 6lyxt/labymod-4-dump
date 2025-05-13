// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.tileentity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.blockentity.BlockEntity;

@Mixin({ akw.class })
public abstract class MixinTileEntity implements BlockEntity
{
    @Shadow
    public abstract cj v();
    
    @Shadow
    public abstract boolean x();
    
    @NotNull
    @Override
    public BlockPosition position() {
        return (BlockPosition)this.v();
    }
    
    @Override
    public boolean isRemoved() {
        return this.x();
    }
}
