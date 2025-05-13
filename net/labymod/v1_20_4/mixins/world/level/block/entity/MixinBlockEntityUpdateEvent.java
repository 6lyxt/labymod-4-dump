// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.world.level.block.entity;

import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dgv.class })
public class MixinBlockEntityUpdateEvent
{
    @Insert(method = { "load" }, at = @At("HEAD"))
    private void labyMod$fireTileEntityPreLoadEvent(final sn tag, final InsertInfo ci) {
        Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity)this, (NBTTagCompound)tag));
    }
    
    @Insert(method = { "setChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V" }, at = @At("TAIL"))
    private static void labyMod$fireTileEntityUpdateEvent(final ctp level, final hx pos, final djh state, final InsertInfo ci) {
        final dgv blockEntity = level.c_(pos);
        if (blockEntity instanceof final BlockEntity blockEntity2) {
            Laby.fireEvent(new BlockEntityUpdateEvent(blockEntity2));
        }
    }
}
