// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model.type;

import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;
import net.labymod.core.main.user.shop.item.items.PetItem;
import net.labymod.core.main.user.shop.item.items.minecraft.MinecraftItem;
import net.labymod.core.main.user.shop.item.items.CosmeticItem;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.ItemProducer;

public enum ItemType
{
    COSMETIC(CosmeticItem::new), 
    MINECRAFT_ITEM(MinecraftItem::new), 
    FLYING_PET(PetItem::new), 
    SHOULDER_PET(PetItem::new), 
    WALKING_PET(WalkingPet::new);
    
    private final ItemProducer producer;
    
    private ItemType(final ItemProducer producer) {
        this.producer = producer;
    }
    
    public AbstractItem produce(final int listId, final ItemDetails details) {
        return this.producer.produce(listId, details);
    }
}
