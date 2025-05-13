// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.world.level.block.entity;

import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dux.class })
public class MixinBlockEntityUpdateEvent
{
    @Insert(method = { "loadAdditional" }, at = @At("HEAD"))
    private void labyMod$fireTileEntityPreLoadEvent(final ux tag, final js.a provider, final InsertInfo ci) {
        Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity)this, (NBTTagCompound)tag));
    }
    
    @Insert(method = { "setChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V" }, at = @At("TAIL"))
    private static void labyMod$fireTileEntityUpdateEvent(final dhi level, final jh pos, final dxv state, final InsertInfo ci) {
        final dux blockEntity = level.c_(pos);
        if (blockEntity instanceof final BlockEntity blockEntity2) {
            Laby.fireEvent(new BlockEntityUpdateEvent(blockEntity2));
        }
    }
}
