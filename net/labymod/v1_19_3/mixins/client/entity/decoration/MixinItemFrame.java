// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.entity.decoration;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.v1_19_3.mixins.client.entity.MixinEntity;

@Mixin({ btb.class })
@Implements({ @Interface(iface = ItemFrame.class, prefix = "itemFrame$", remap = Interface.Remap.NONE) })
public abstract class MixinItemFrame extends MixinEntity implements ItemFrame
{
    @Shadow
    public abstract cdt shadow$x();
    
    @Intrinsic
    @Nullable
    public ItemStack itemFrame$getItem() {
        return (ItemStack)this.shadow$x();
    }
}
