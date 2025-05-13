// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.world.level.block.entity;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.blockentity.SignBlockEntity;

@Mixin({ dwk.class })
public abstract class MixinSignBlockEntity implements SignBlockEntity
{
    @Shadow
    private dwl e;
    @Shadow
    private dwl f;
    
    @Insert(method = { "loadAdditional" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPreLoadEvent(final ux tag, final js.a provider, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final dux entity = (dux)this;
        final dxv state = entity.m();
        final dkm block = state.b();
        if (block instanceof dtp || block instanceof dto) {
            final jm facing = (jm)state.c((dyx)((block instanceof dtp) ? dtp.b : dto.b));
            return switch (facing) {
                default -> throw new MatchException(null, null);
                case c,  b,  a -> 0.0f;
                case f -> -90.0f;
                case d -> 180.0f;
                case e -> 90.0f;
            };
        }
        if (block instanceof dsk || block instanceof dlm) {
            final Integer rotation = (Integer)state.c((dyx)((block instanceof dsk) ? dsk.b : dlm.b));
            return 180.0f - rotation * 360 / 16.0f;
        }
        return 0.0f;
    }
    
    @Override
    public boolean hasSide(@NotNull final SignSide side) {
        return side == SignSide.FRONT || side == SignSide.BACK;
    }
    
    @NotNull
    @Override
    public Component[] getLines(@NotNull final SignSide side) {
        if (side != SignSide.FRONT && side != SignSide.BACK) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        final xv[] messages = ((side == SignSide.FRONT) ? this.e : this.f).b(false);
        final Component[] lines = new Component[messages.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((messages[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(messages[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final dux entity = (dux)this;
        final dxv state = entity.m();
        final dkm block = state.b();
        if (block instanceof dsk) {
            return SignType.STANDING_SIGN;
        }
        if (block instanceof dtp) {
            return SignType.WALL_SIGN;
        }
        if (block instanceof dlm) {
            return SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof dto) {
            return SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
