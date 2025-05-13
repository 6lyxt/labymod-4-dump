// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.world.level.block.entity;

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

@Mixin({ dav.class })
public abstract class MixinSignBlockEntity implements SignBlockEntity
{
    @Shadow
    private daw e;
    @Shadow
    private daw f;
    
    @Insert(method = { "load" }, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(final qr tag, final InsertInfo ci) {
        Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound)tag));
    }
    
    @Override
    public float getRotationYaw() {
        final czn entity = (czn)this;
        final dcb state = entity.q();
        final cpn block = state.b();
        if (block instanceof cyl || block instanceof cyk) {
            final ha facing = (ha)state.c((dde)((block instanceof cyl) ? cyl.a : cyk.a));
            return switch (facing) {
                default -> throw new MatchException(null, null);
                case c,  b,  a -> 0.0f;
                case f -> -90.0f;
                case d -> 180.0f;
                case e -> 90.0f;
            };
        }
        if (block instanceof cxi || block instanceof cql) {
            final Integer rotation = (Integer)state.c((dde)((block instanceof cxi) ? cxi.a : cql.a));
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
        final sw[] messages = ((side == SignSide.FRONT) ? this.e : this.f).b(false);
        final Component[] lines = new Component[messages.length];
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = ((messages[i] != null) ? Laby.references().componentMapper().fromMinecraftComponent(messages[i]) : Component.empty());
        }
        return lines;
    }
    
    @Override
    public SignType getType() {
        final czn entity = (czn)this;
        final dcb state = entity.q();
        final cpn block = state.b();
        if (block instanceof cxi) {
            return SignType.STANDING_SIGN;
        }
        if (block instanceof cyl) {
            return SignType.WALL_SIGN;
        }
        if (block instanceof cql) {
            return SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof cyk) {
            return SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
