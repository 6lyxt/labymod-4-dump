// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.world.level.block.entity;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.blockentity.BlockEntity;

@Implements({ @Interface(prefix = "labyMod$", iface = BlockEntity.class, remap = Interface.Remap.NONE) })
@Mixin({ ccj.class })
public abstract class MixinBlockEntity implements BlockEntity
{
    @Shadow
    protected boolean f;
    
    @Shadow
    public abstract fx o();
    
    @Shadow
    public abstract boolean q();
    
    @Shadow
    public abstract ceh p();
    
    @NotNull
    @Override
    public BlockPosition position() {
        return (BlockPosition)this.o();
    }
    
    @Intrinsic
    public boolean labyMod$isRemoved() {
        return this.q();
    }
}
