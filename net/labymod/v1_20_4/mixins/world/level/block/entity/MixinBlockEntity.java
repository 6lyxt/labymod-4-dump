// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.world.level.block.entity;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.block.BlockPosition;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.blockentity.BlockEntity;

@Implements({ @Interface(prefix = "labyMod$", iface = BlockEntity.class, remap = Interface.Remap.NONE) })
@Mixin({ dgv.class })
public abstract class MixinBlockEntity implements BlockEntity
{
    @Shadow
    protected boolean q;
    
    @Shadow
    public abstract hx aB_();
    
    @Shadow
    public abstract boolean s();
    
    @Shadow
    public abstract djh r();
    
    @NotNull
    @Override
    public BlockPosition position() {
        return (BlockPosition)this.aB_();
    }
    
    @Intrinsic
    public boolean labyMod$isRemoved() {
        return this.s();
    }
}
