// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.core;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.BlockPosition;

@Mixin({ hx.class })
@Implements({ @Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinBlockPos implements BlockPosition
{
    @Intrinsic
    public int labyMod$getX() {
        return ((jb)this).a;
    }
    
    @Intrinsic
    public int labyMod$getY() {
        return ((jb)this).b;
    }
    
    @Intrinsic
    public int labyMod$getZ() {
        return ((jb)this).c;
    }
}
