// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.tileentity;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.blockentity.SignBlockEntity;

@Mixin({ awc.class })
public abstract class MixinTileEntitySign implements SignBlockEntity
{
    @Shadow
    @Final
    public hh[] a;
    
    @Insert(method = { "readFromNBT" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(final fy tag, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final avj entity = (avj)this;
        final aow block = entity.x();
        final awt state = entity.D().o(entity.w());
        if (block == aox.ax) {
            final fa facing = (fa)state.c((axj)auw.b);
            switch (facing) {
                case c:
                case b:
                case a: {
                    return 0.0f;
                }
                case f: {
                    return -90.0f;
                }
                case d: {
                    return 180.0f;
                }
                case e: {
                    return 90.0f;
                }
            }
        }
        if (block == aox.an) {
            final Integer rotation = (Integer)state.c((axj)aue.b);
            if (rotation != null) {
                return 180.0f - rotation * 360 / 16.0f;
            }
        }
        return 0.0f;
    }
    
    @Override
    public boolean hasSide(@NotNull final SignSide side) {
        return side == SignSide.FRONT;
    }
    
    @NotNull
    @Override
    public Component[] getLines(@NotNull final SignSide side) {
        if (side != SignSide.FRONT) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        final Component[] lines = new Component[this.a.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((this.a[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(this.a[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final avj entity = (avj)this;
        final aow block = entity.x();
        if (block == aox.an) {
            return SignType.STANDING_SIGN;
        }
        if (block == aox.ax) {
            return SignType.WALL_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
