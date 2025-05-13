// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.api.Laby;
import net.labymod.core.main.user.shop.item.ItemUtil;
import java.util.function.Function;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.Iterator;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.core.main.user.shop.item.model.type.ItemType;
import net.labymod.core.main.user.shop.item.items.PetItem;
import net.labymod.api.user.GameUser;
import net.labymod.api.tag.Tag;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.annotations.NotNull;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.core.main.user.shop.item.items.legacy.head.EyelidsItem;
import net.labymod.core.main.user.shop.item.texture.ItemTexture;
import net.labymod.core.main.user.shop.item.items.pet.PetDataStorage;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class GameUserItemStorage
{
    private final Int2ObjectMap<AnimationStorage> animationStorages;
    private final Int2ObjectMap<PetDataStorage> petDataStorages;
    private final Int2ObjectMap<PetDataStorage> screenContextPetDataStorages;
    private final Int2ObjectMap<PetDataStorage> minecraftEntityPetDataStorages;
    private final Int2ObjectMap<ItemTexture> textures;
    @Deprecated
    private final Int2ObjectMap<EyelidsItem.AnimationData> eyelidsAnimations;
    private int maxPets;
    
    public GameUserItemStorage() {
        this.animationStorages = (Int2ObjectMap<AnimationStorage>)new Int2ObjectOpenHashMap();
        this.petDataStorages = (Int2ObjectMap<PetDataStorage>)new Int2ObjectOpenHashMap();
        this.screenContextPetDataStorages = (Int2ObjectMap<PetDataStorage>)new Int2ObjectOpenHashMap();
        this.minecraftEntityPetDataStorages = (Int2ObjectMap<PetDataStorage>)new Int2ObjectOpenHashMap();
        this.textures = (Int2ObjectMap<ItemTexture>)new Int2ObjectOpenHashMap();
        this.eyelidsAnimations = (Int2ObjectMap<EyelidsItem.AnimationData>)new Int2ObjectOpenHashMap();
    }
    
    public void prepare(final DefaultGameUser user, @NotNull final GameUserData userData) {
        this.maxPets = 0;
        final Object2IntMap<AttachmentPoint> priorityCounter = (Object2IntMap<AttachmentPoint>)new Object2IntOpenHashMap();
        user.removeTag(GameUser.HIDE_CAPE, GameUser.HIDE_SHIELD, GameUser.CUSTOM_ITEM);
        for (GameUserItem entry : userData.getItems()) {
            final AbstractItem item = entry.item();
            final int identifier = item.getIdentifier();
            this.fillMap(this.textures, identifier, texture -> (texture == null) ? ItemTexture.create(item) : ItemTexture.copyOf(texture));
            this.fillMap(this.animationStorages, identifier, storage -> (storage == null) ? AnimationStorage.create() : AnimationStorage.copyOf(storage));
            if (identifier == 36) {
                this.fillMap(this.eyelidsAnimations, identifier, data -> {
                    EyelidsItem.AnimationData copy = null;
                    if (data == null) {
                        new(net.labymod.core.main.user.shop.item.items.legacy.head.EyelidsItem.AnimationData.class)();
                        new EyelidsItem.AnimationData();
                    }
                    else {
                        copy = EyelidsItem.AnimationData.copyOf(data);
                    }
                    return copy;
                });
            }
            if (item instanceof PetItem) {
                this.fillMap(this.petDataStorages, identifier, storage -> (storage == null) ? PetDataStorage.create() : PetDataStorage.copyOf(storage));
                this.fillMap(this.screenContextPetDataStorages, identifier, storage -> (storage == null) ? PetDataStorage.create() : PetDataStorage.copyOf(storage));
                this.fillMap(this.minecraftEntityPetDataStorages, identifier, storage -> (storage == null) ? PetDataStorage.create() : PetDataStorage.copyOf(storage));
            }
            final ItemDetails itemDetails = item.itemDetails();
            final AttachmentPoint attachmentPoint = itemDetails.getAttachmentPoint();
            int level = priorityCounter.containsKey((Object)attachmentPoint) ? priorityCounter.getInt((Object)attachmentPoint) : priorityCounter.put((Object)attachmentPoint, 0);
            item.setPriorityLevel(level++);
            priorityCounter.put((Object)attachmentPoint, level);
            if (itemDetails.getType() == ItemType.MINECRAFT_ITEM) {
                user.setTag(GameUser.CUSTOM_ITEM);
            }
            if (itemDetails.isHideCape()) {
                user.setTag(GameUser.HIDE_CAPE);
            }
            if (itemDetails.getType() == ItemType.WALKING_PET) {
                ++this.maxPets;
            }
        }
    }
    
    public <T> void fillMap(final Int2ObjectMap<T> map, final int index, final Function<T, T> constructorFactory) {
        map.put(index, (Object)constructorFactory.apply((T)map.get(index)));
    }
    
    public AnimationStorage getAnimationStorage(@NotNull final AbstractItem item) {
        return (AnimationStorage)this.animationStorages.get(item.getIdentifier());
    }
    
    public ItemTexture getTexture(@NotNull final AbstractItem item) {
        return (ItemTexture)this.textures.get(item.getIdentifier());
    }
    
    public PetDataStorage getPetDataStorage(@NotNull final AbstractItem item) {
        if (ItemUtil.isSkipFlyingPets()) {
            return (PetDataStorage)this.minecraftEntityPetDataStorages.get(item.getIdentifier());
        }
        if (Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().isScreenContext()) {
            return (PetDataStorage)this.screenContextPetDataStorages.get(item.getIdentifier());
        }
        return (PetDataStorage)this.petDataStorages.get(item.getIdentifier());
    }
    
    public int getMaxPets() {
        return this.maxPets;
    }
    
    @Deprecated
    public EyelidsItem.AnimationData getEyelidsAnimationData(@NotNull final AbstractItem item) {
        return this.getEyelidsAnimationData(item.getIdentifier());
    }
    
    @Deprecated
    public EyelidsItem.AnimationData getEyelidsAnimationData(final int id) {
        return (EyelidsItem.AnimationData)this.eyelidsAnimations.get(id);
    }
    
    public void clear() {
        this.animationStorages.clear();
        this.petDataStorages.clear();
        this.screenContextPetDataStorages.clear();
        this.textures.clear();
        this.eyelidsAnimations.clear();
    }
}
