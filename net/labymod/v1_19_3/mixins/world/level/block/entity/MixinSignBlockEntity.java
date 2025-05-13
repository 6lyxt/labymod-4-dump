// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.world.level.block.entity;

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

@Mixin({ cxp.class })
public abstract class MixinSignBlockEntity implements SignBlockEntity
{
    @Shadow
    @Final
    private ss[] f;
    
    @Insert(method = { "load" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(final qp tag, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final cwl entity = (cwl)this;
        final cyt state = entity.q();
        final cmt block = state.b();
        if (block instanceof cvj) {
            final gv facing = (gv)state.c((czw)cvj.a);
            return switch (facing) {
                default -> throw new MatchException(null, null);
                case c,  b,  a -> 0.0f;
                case f -> -90.0f;
                case d -> 180.0f;
                case e -> 90.0f;
            };
        }
        if (block instanceof cuh) {
            final Integer rotation = (Integer)state.c((czw)cuh.a);
            return 180.0f - rotation * 360 / 16.0f;
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
        final Component[] lines = new Component[this.f.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((this.f[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(this.f[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final cwl entity = (cwl)this;
        final cyt state = entity.q();
        final cmt block = state.b();
        if (block instanceof cuh) {
            return SignType.STANDING_SIGN;
        }
        if (block instanceof cvj) {
            return SignType.WALL_SIGN;
        }
        if (block instanceof cnp) {
            return SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof cvi) {
            return SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
