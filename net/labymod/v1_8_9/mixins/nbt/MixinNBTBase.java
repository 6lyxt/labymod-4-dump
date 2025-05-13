// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.nbt;

import org.spongepowered.asm.mixin.Intrinsic;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_8_9.nbt.VersionedNBTTagLongArray;
import net.labymod.api.nbt.NBTTagType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import java.io.DataOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.nbt.NBTTag;

@Implements({ @Interface(iface = NBTTag.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
@Mixin({ eb.class })
public abstract class MixinNBTBase implements NBTTag
{
    @Shadow
    public abstract void a(final DataOutput p0);
    
    @Inject(method = { "createNewByType" }, at = { @At("HEAD") }, cancellable = true)
    private static void createNewByType(final byte id, final CallbackInfoReturnable<eb> cir) {
        if (id == NBTTagType.LONG_ARRAY.getId()) {
            cir.setReturnValue((Object)new VersionedNBTTagLongArray());
        }
    }
    
    @Intrinsic
    public void labyMod$write(@NotNull final DataOutput output) {
        this.a(output);
    }
}
