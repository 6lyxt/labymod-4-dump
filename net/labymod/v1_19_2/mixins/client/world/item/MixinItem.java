// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.world.item;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ cat.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    
    @Shadow
    public abstract int m();
    
    @Shadow
    public abstract int n();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final abb minecraftIdentifier = hm.Y.b((Object)this);
            this.labyMod$identifier = (ResourceLocation)minecraftIdentifier;
        }
        return this.labyMod$identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.m();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.n();
    }
    
    @Override
    public boolean isAir() {
        return this == caz.a;
    }
}
