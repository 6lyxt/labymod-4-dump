// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.entity.item;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.item.ItemStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.v1_12_2.mixins.client.entity.MixinEntity;

@Mixin({ acb.class })
public abstract class MixinEntityItemFrame extends MixinEntity implements ItemFrame
{
    @Shadow
    public abstract aip r();
    
    @Nullable
    @Override
    public ItemStack getItem() {
        return (ItemStack)this.r();
    }
}
