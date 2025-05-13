// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.tileentity;

import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ akw.class })
public class MixinTileEntityUpdateEvent
{
    @Insert(method = { "readFromNBT" }, at = @At("HEAD"))
    private void labyMod$fireTileEntityPreLoadEvent(final dn tag, final InsertInfo ci) {
        Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity)this, (NBTTagCompound)tag));
    }
    
    @Insert(method = { "markDirty" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityUpdateEvent(final InsertInfo ci) {
        Laby.fireEvent(new BlockEntityUpdateEvent((BlockEntity)this));
    }
}
