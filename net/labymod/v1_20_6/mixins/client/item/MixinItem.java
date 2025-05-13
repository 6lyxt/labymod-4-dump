// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.item;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ cum.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    
    @Shadow
    public abstract int q();
    
    @Shadow
    public abstract ki p();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final alf minecraftIdentifier = lp.h.b((Object)this);
            this.labyMod$identifier = (ResourceLocation)minecraftIdentifier;
        }
        return this.labyMod$identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.q();
    }
    
    @Override
    public int getMaximumDamage() {
        return (int)this.p().a(km.d, (Object)0);
    }
    
    @Override
    public boolean isAir() {
        return this == cuu.a;
    }
}
