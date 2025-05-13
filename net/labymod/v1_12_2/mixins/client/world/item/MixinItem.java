// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.world.item;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.world.item.Item;

@Mixin({ ain.class })
public abstract class MixinItem implements Item
{
    private ResourceLocation labyMod$identifier;
    @Shadow
    @Final
    public static fh<nf, ain> g;
    
    @Shadow
    public abstract int j();
    
    @Shadow
    public abstract int l();
    
    @Override
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            final nf minecraftIdentifier = (nf)MixinItem.g.b((Object)this);
            this.labyMod$identifier = (ResourceLocation)minecraftIdentifier;
        }
        return this.labyMod$identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return this.j();
    }
    
    @Override
    public int getMaximumDamage() {
        return this.l();
    }
    
    @Override
    public boolean isAir() {
        return this.getIdentifier().getPath().equals("air");
    }
}
