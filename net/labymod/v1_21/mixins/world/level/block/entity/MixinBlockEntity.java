// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.world.level.block.entity;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.blockentity.BlockEntity;

@Implements({ @Interface(prefix = "labyMod$", iface = BlockEntity.class, remap = Interface.Remap.NONE) })
@Mixin({ dqh.class })
public abstract class MixinBlockEntity implements BlockEntity
{
    @Shadow
    protected boolean p;
    
    @Shadow
    public abstract jd aD_();
    
    @Shadow
    public abstract boolean o();
    
    @Shadow
    public abstract dtc n();
    
    @NotNull
    @Override
    public BlockPosition position() {
        return (BlockPosition)this.aD_();
    }
    
    @Intrinsic
    public boolean labyMod$isRemoved() {
        return this.o();
    }
}
