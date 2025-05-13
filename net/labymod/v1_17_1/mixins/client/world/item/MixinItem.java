// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.world.item;

import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ bqm.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    
    @Shadow
    public abstract int l();
    
    @Shadow
    public abstract int m();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final ww minecraftIdentifier = gw.Z.b((Object)this);
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
        return this.m();
    }
    
    @Override
    public boolean isAir() {
        return this == bqs.a;
    }
}
