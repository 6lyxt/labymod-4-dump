// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.item;

import java.util.Objects;
import net.labymod.api.nbt.NBTTag;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public interface ItemStack extends Item
{
    @Deprecated(forRemoval = true, since = "4.1.18")
    public static final ResourceLocation SHIELD_IDENTIFIER = ResourceLocation.create("minecraft", "shield");
    @Deprecated(forRemoval = true, since = "4.1.18")
    public static final ResourceLocation BOW_IDENTIFIER = ResourceLocation.create("minecraft", "bow");
    
    @NotNull
    Item getAsItem();
    
    int getCurrentDamageValue();
    
    @Deprecated(forRemoval = true, since = "4.2.14")
    default int getUseDuration() {
        return this.getUseDuration(null);
    }
    
    int getUseDuration(final LivingEntity p0);
    
    boolean isSword();
    
    boolean isItem();
    
    boolean isBlock();
    
    boolean isFood();
    
    boolean isFishingTool();
    
    default boolean isShield() {
        return this.getIdentifier().equals(VanillaItems.SHIELD.identifier());
    }
    
    default boolean isBow() {
        return this.getIdentifier().equals(VanillaItems.BOW.identifier());
    }
    
    int getSize();
    
    void setSize(final int p0);
    
    Component getDisplayName();
    
    default boolean hasDataComponentContainer() {
        return this.getDataComponentContainer() != DataComponentContainer.EMPTY;
    }
    
    @NotNull
    DataComponentContainer getDataComponentContainer();
    
    @NotNull
    DataComponentContainer getOrCreateDataComponentContainer();
    
    default void applyPatchToDataComponent(@NotNull final DataComponentPatch patch) {
        final DataComponentContainer destination = this.getOrCreateDataComponentContainer();
        destination.apply(patch);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    default boolean hasNBTTag() {
        return this.getNBTTag() != null;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    @Nullable
    NBTTagCompound getNBTTag();
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    @NotNull
    NBTTagCompound getOrCreateNBTTag();
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    default void setNBTTag(final NBTTagCompound source) {
        final NBTTagCompound destination = this.getOrCreateNBTTag();
        destination.clear();
        for (final String key : source.keySet()) {
            final NBTTag<?> value = source.get(key);
            if (value != null) {
                destination.set(key, value);
            }
        }
    }
    
    boolean matches(final ItemStack p0);
    
    ItemStack copy();
    
    default int getLegacyItemData() {
        return -1;
    }
    
    default void setLegacyItemData(final int legacyData) {
    }
    
    default ItemData toItemData() {
        return ItemData.from(this);
    }
    
    default boolean matches(final ItemStack itemStack1, final ItemStack itemStack2) {
        return itemStack1 == itemStack2 || (itemStack1 != null && itemStack1.matches(itemStack2));
    }
    
    default boolean isSameItem(final ItemStack itemStack1, final ItemStack itemStack2) {
        return Objects.equals(itemStack1, itemStack2) || itemStack1.getAsItem() == itemStack2.getAsItem();
    }
    
    default boolean isSameItemSameTags(final ItemStack itemStack1, final ItemStack itemStack2) {
        if (Objects.equals(itemStack1, itemStack2)) {
            return true;
        }
        final DataComponentContainer dataComponentContainer1 = itemStack1.getDataComponentContainer();
        final DataComponentContainer dataComponentContainer2 = itemStack2.getDataComponentContainer();
        return isSameItem(itemStack1, itemStack2) && Objects.equals(dataComponentContainer1, dataComponentContainer2);
    }
}
