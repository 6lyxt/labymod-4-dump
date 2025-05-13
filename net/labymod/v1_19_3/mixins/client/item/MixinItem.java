// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.item;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ cdp.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    
    @Shadow
    public abstract int n();
    
    @Shadow
    public abstract int o();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final acf minecraftIdentifier = iw.i.b((Object)this);
            this.labyMod$identifier = (ResourceLocation)minecraftIdentifier;
        }
        return this.labyMod$identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.n();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.o();
    }
    
    @Override
    public boolean isAir() {
        return this == cdw.a;
    }
}
