// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.world.level.block.entity;

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

@Mixin({ dqt.class })
public abstract class MixinSignBlockEntity implements SignBlockEntity
{
    @Shadow
    private dqu e;
    @Shadow
    private dqu f;
    
    @Insert(method = { "loadAdditional" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPreLoadEvent(final us tag, final jk.a provider, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final dpj entity = (dpj)this;
        final dse state = entity.n();
        final dfb block = state.b();
        if (block instanceof dob || block instanceof doa) {
            final je facing = (je)state.c((dth)((block instanceof dob) ? dob.b : doa.b));
            return switch (facing) {
                default -> throw new MatchException(null, null);
                case c,  b,  a -> 0.0f;
                case f -> -90.0f;
                case d -> 180.0f;
                case e -> 90.0f;
            };
        }
        if (block instanceof dmw || block instanceof dga) {
            final Integer rotation = (Integer)state.c((dth)((block instanceof dmw) ? dmw.b : dga.b));
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
        final xp[] messages = ((side == SignSide.FRONT) ? this.e : this.f).b(false);
        final Component[] lines = new Component[messages.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((messages[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(messages[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final dpj entity = (dpj)this;
        final dse state = entity.n();
        final dfb block = state.b();
        if (block instanceof dmw) {
            return SignType.STANDING_SIGN;
        }
        if (block instanceof dob) {
            return SignType.WALL_SIGN;
        }
        if (block instanceof dga) {
            return SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof doa) {
            return SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
