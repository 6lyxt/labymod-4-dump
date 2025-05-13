// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.world.level.block.entity;

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

@Mixin({ cuk.class })
public abstract class MixinSignBlockEntity implements SignBlockEntity
{
    @Shadow
    @Final
    private rq[] d;
    
    @Insert(method = { "load" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(final pj tag, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final cti entity = (cti)this;
        final cvo state = entity.q();
        final cjt block = state.b();
        if (block instanceof csf) {
            final gy facing = (gy)state.c((cwr)csf.d);
            return switch (facing) {
                default -> throw new MatchException(null, null);
                case c,  b,  a -> 0.0f;
                case f -> -90.0f;
                case d -> 180.0f;
                case e -> 90.0f;
            };
        }
        if (block instanceof cre) {
            final Integer rotation = (Integer)state.c((cwr)cre.d);
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
        final Component[] lines = new Component[this.d.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((this.d[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(this.d[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final cti entity = (cti)this;
        final cvo state = entity.q();
        final cjt block = state.b();
        if (block instanceof cre) {
            return SignType.STANDING_SIGN;
        }
        if (block instanceof csf) {
            return SignType.WALL_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
