// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.core;

import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.block.BlockPosition;

@Mixin({ gg.class })
@Implements({ @Interface(iface = BlockPosition.class, prefix = "labyMod$", remap = Interface.Remap.NONE) })
public abstract class MixinBlockPos implements BlockPosition
{
    @Intrinsic
    public int labyMod$getX() {
        return ((hb)this).a;
    }
    
    @Intrinsic
    public int labyMod$getY() {
        return ((hb)this).b;
    }
    
    @Intrinsic
    public int labyMod$getZ() {
        return ((hb)this).c;
    }
}
