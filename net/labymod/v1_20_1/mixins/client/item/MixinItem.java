// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.item;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ cfu.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    
    @Shadow
    public abstract int l();
    
    @Shadow
    public abstract int n();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final acq minecraftIdentifier = jb.i.b((Object)this);
            this.labyMod$identifier = (ResourceLocation)minecraftIdentifier;
        }
        return this.labyMod$identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.l();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.n();
    }
    
    @Override
    public boolean isAir() {
        return this == cgc.a;
    }
}
