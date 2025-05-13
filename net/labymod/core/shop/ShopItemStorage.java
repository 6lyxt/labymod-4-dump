// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop;

import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.user.GameUser;
import java.util.Objects;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Iterator;
import net.labymod.core.main.LabyMod;
import net.labymod.core.shop.models.ShopItem;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.HashSet;
import net.labymod.core.main.user.GameUserItem;
import java.util.Map;
import java.util.Set;
import net.labymod.api.util.logging.Logging;

public class ShopItemStorage
{
    private static final Logging LOGGER;
    private final Set<Integer> items;
    private final Map<Integer, GameUserItem> customItems;
    private final Runnable updateListener;
    private boolean fireUpdate;
    
    public ShopItemStorage() {
        this(() -> {});
    }
    
    public ShopItemStorage(final Runnable updateListener) {
        this.items = new HashSet<Integer>();
        this.customItems = new HashMap<Integer, GameUserItem>();
        this.fireUpdate = true;
        this.updateListener = updateListener;
    }
    
    public Set<Integer> getItems() {
        return this.items;
    }
    
    public void forEach(final Consumer<ShopItem> consumer) {
        for (final Integer id : this.items) {
            final ShopItem item = LabyMod.references().shopController().getItem(id);
            if (item != null) {
                consumer.accept(item);
            }
        }
    }
    
    public void forEachGameItem(final Consumer<GameUserItem> consumer) {
        this.forEach(shopItem -> {
            if (this.customItems.containsKey(shopItem.getId())) {
                consumer.accept(this.customItems.get(shopItem.getId()));
            }
            else {
                final GameUserItem gameUserItem = shopItem.asGameUserItem();
                if (gameUserItem != null) {
                    consumer.accept(gameUserItem);
                }
            }
        });
    }
    
    public boolean canFitItem(final ShopItem item) {
        final AtomicBoolean result = new AtomicBoolean(true);
        this.forEach(stored -> {
            if (!this.canBeCombined(stored, item)) {
                result.set(false);
            }
            return;
        });
        return result.get();
    }
    
    public Collection<ShopItem> makeItemFit(final ShopItem item) {
        final Collection<ShopItem> remove = new HashSet<ShopItem>();
        this.forEach(stored -> {
            if (!this.canBeCombined(stored, item)) {
                remove.add(stored);
            }
            return;
        });
        for (final ShopItem rem : remove) {
            this.removeItem(rem);
        }
        return remove;
    }
    
    private boolean canBeCombined(final ShopItem a, final ShopItem b) {
        return !Objects.equals(a.getCategory(), b.getCategory());
    }
    
    public boolean hasItem(final ShopItem item) {
        return this.items.contains(item.getId());
    }
    
    public void addItem(final ShopItem item) {
        if (this.items.add(item.getId()) && this.fireUpdate) {
            this.updateListener.run();
        }
    }
    
    public void addItem(final GameUserItem item) {
        this.customItems.put(item.item().getIdentifier(), item);
        if (this.items.add(item.item().getIdentifier()) && this.fireUpdate) {
            this.updateListener.run();
        }
    }
    
    public void removeItem(final ShopItem item) {
        if (this.items.remove(item.getId())) {
            this.updateListener.run();
        }
    }
    
    public void wrapUpdate(final Runnable runnable) {
        this.fireUpdate = false;
        runnable.run();
        this.fireUpdate = true;
        this.updateListener.run();
    }
    
    public void useItemsFromUser(final GameUser user) {
        this.wrapUpdate(() -> {
            final GameUserData userData = ((DefaultGameUser)user).getUserData();
            this.items.clear();
            this.customItems.clear();
            userData.getItems().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final GameUserItem item = iterator.next();
                this.addItem(item);
            }
        });
    }
    
    static {
        LOGGER = Logging.create(ShopItemStorage.class);
    }
}
