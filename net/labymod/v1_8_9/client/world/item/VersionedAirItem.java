// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;

public class VersionedAirItem implements Item
{
    public static final Item AIR;
    private final ResourceLocation identifier;
    
    private VersionedAirItem() {
        this.identifier = ResourceLocation.create("minecraft", "air");
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }
    
    @Override
    public int getMaximumStackSize() {
        return 0;
    }
    
    @Override
    public int getMaximumDamage() {
        return 0;
    }
    
    @Override
    public boolean isAir() {
        return true;
    }
    
    static {
        AIR = new VersionedAirItem();
    }
}
