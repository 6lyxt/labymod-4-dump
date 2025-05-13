// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.item;

import net.labymod.api.Laby;
import java.util.Objects;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.component.data.DataComponentPatch;
import java.util.Iterator;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.component.data.DataComponentKey;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import com.google.gson.annotations.SerializedName;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.NBTFactory;

public final class ItemData
{
    private static final NBTFactory NBT_FACTORY;
    private static final ItemStackFactory ITEM_STACK_FACTORY;
    private final String namespace;
    private final String path;
    private final NBTTagCompound nbt;
    private final NBTTagCompound components;
    @SerializedName("legacy_data")
    private final int legacyData;
    
    public ItemData(final String namespace, final String path, final NBTTagCompound nbt, final NBTTagCompound components, final int legacyData) {
        this.namespace = namespace;
        this.path = path;
        this.nbt = nbt;
        this.components = components;
        this.legacyData = legacyData;
    }
    
    public static ItemData from(final ItemStack stack) {
        final ResourceLocation identifier = stack.getIdentifier();
        final String namespace = identifier.getNamespace();
        final String path = identifier.getPath();
        if (PlatformEnvironment.ITEM_COMPONENTS) {
            return new ItemData(namespace, path, toNbtCompound(DataComponentContainer.EMPTY), toNbtCompound(stack.getDataComponentContainer()), stack.getLegacyItemData());
        }
        return new ItemData(namespace, path, toNbtCompound(stack.getDataComponentContainer()), toNbtCompound(DataComponentContainer.EMPTY), stack.getLegacyItemData());
    }
    
    private static NBTTagCompound toNbtCompound(final DataComponentContainer container) {
        final NBTTagCompound compound = ItemData.NBT_FACTORY.compound();
        if (container.isEmpty()) {
            return compound;
        }
        for (final DataComponentKey key : container.keySet()) {
            final NBTTag tag = container.get(key);
            compound.set(key.name(), tag);
        }
        return compound;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public NBTTagCompound nbt() {
        return this.nbt;
    }
    
    public NBTTagCompound components() {
        return this.components;
    }
    
    @SerializedName("legacy_data")
    public int legacyData() {
        return this.legacyData;
    }
    
    public ItemStack toItemStack() {
        final ItemStack stack = ItemData.ITEM_STACK_FACTORY.create(this.namespace, this.path);
        stack.setLegacyItemData(this.legacyData);
        final DataComponentPatch patch = DataComponentPatch.createPatch(this.createComponentContainer());
        stack.applyPatchToDataComponent(patch);
        return stack;
    }
    
    public ResourceLocation toResourceLocation() {
        return ResourceLocation.create(this.namespace, this.path);
    }
    
    private DataComponentContainer createComponentContainer() {
        NBTTagCompound compound = PlatformEnvironment.ITEM_COMPONENTS ? this.components : this.nbt;
        if (compound == null) {
            compound = ItemData.NBT_FACTORY.compound();
        }
        return new NbtDataComponentContainer(compound);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final ItemData that = (ItemData)obj;
        return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.path, that.path) && Objects.equals(this.nbt, that.nbt) && Objects.equals(this.components, that.components) && this.legacyData == that.legacyData;
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(this.namespace);
        result = 31 * result + Objects.hashCode(this.path);
        result = 31 * result + Objects.hashCode(this.nbt);
        result = 31 * result + Objects.hashCode(this.components);
        result = 31 * result + this.legacyData;
        return result;
    }
    
    @Override
    public String toString() {
        return "ItemData{namespace='" + this.namespace + "', path='" + this.path + "', nbt=" + String.valueOf(this.nbt) + ", components=" + String.valueOf(this.components) + ", legacyData=" + this.legacyData;
    }
    
    static {
        NBT_FACTORY = Laby.references().nbtFactory();
        ITEM_STACK_FACTORY = Laby.references().itemStackFactory();
    }
}
