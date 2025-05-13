// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import java.util.Objects;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.function.Consumer;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.main.user.shop.emote.model.DailyEmoteFlat;
import net.labymod.core.main.user.shop.spray.model.SprayPacks;
import net.labymod.core.main.user.group.GroupIdentifier;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GameUserData
{
    @SerializedName("c")
    private List<GameUserItem> items;
    @SerializedName("e")
    private List<Integer> emotes;
    @SerializedName("g")
    private List<GroupIdentifier> groups;
    @SerializedName("st")
    private SprayPacks sprayPacks;
    @SerializedName("f")
    @Nullable
    private DailyEmoteFlat dailyEmoteFlat;
    
    public GameUserData() {
        this.items = new ArrayList<GameUserItem>();
        this.emotes = new ArrayList<Integer>();
        this.groups = new ArrayList<GroupIdentifier>();
        this.sprayPacks = new SprayPacks();
    }
    
    @NotNull
    public List<GameUserItem> getItems() {
        return this.items;
    }
    
    @NotNull
    public List<Integer> getEmotes() {
        return this.emotes;
    }
    
    @NotNull
    public List<GroupIdentifier> getGroups() {
        return this.groups;
    }
    
    public SprayPacks getSprayPacks() {
        return this.sprayPacks;
    }
    
    @Nullable
    public DailyEmoteFlat getDailyEmoteFlat() {
        return this.dailyEmoteFlat;
    }
    
    public boolean hasItem(final int id) {
        return this.getItem(id) != null;
    }
    
    @Nullable
    public GameUserItem getItem(final int id) {
        for (final GameUserItem item : this.items) {
            if (item.item().getIdentifier() == id) {
                return item;
            }
        }
        return null;
    }
    
    public void setData(final GameUserData other, final Consumer<AbstractItem> itemConsumer) {
        if (other == null) {
            return;
        }
        final Iterator<GameUserItem> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            final GameUserItem userItem = iterator.next();
            final AbstractItem item = userItem.item();
            final int identifier = item.getIdentifier();
            final GameUserItem otherItem = other.getItem(identifier);
            if (otherItem == null) {
                item.dispose();
                iterator.remove();
            }
            else {
                final ItemMetadata previousItemMetadata = userItem.itemMetadata();
                final ItemMetadata otherItemMetadata = otherItem.itemMetadata();
                userItem.itemMetadata(otherItemMetadata);
                if (Objects.equals(previousItemMetadata.getTextureDetails(), otherItemMetadata.getTextureDetails()) || itemConsumer == null) {
                    continue;
                }
                itemConsumer.accept(item);
            }
        }
        for (final GameUserItem item2 : other.getItems()) {
            final GameUserItem thisItem = this.getItem(item2.item().getIdentifier());
            if (thisItem != null) {
                continue;
            }
            this.items.add(item2);
        }
        if (this.items.isEmpty()) {
            this.items = other.getItems();
        }
        this.emotes = other.getEmotes();
        this.groups = other.getGroups();
        this.sprayPacks = other.getSprayPacks();
        this.dailyEmoteFlat = other.getDailyEmoteFlat();
    }
}
