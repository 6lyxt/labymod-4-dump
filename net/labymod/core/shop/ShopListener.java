// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop;

import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.labyconnect.LabyConnectSession;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.Laby;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import java.util.Iterator;
import net.labymod.core.shop.models.ItemType;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.labymodnet.models.UserItems;
import java.util.ArrayList;
import net.labymod.core.main.LabyMod;
import net.labymod.core.labymodnet.models.Emote;
import net.labymod.core.labymodnet.models.Cosmetic;
import java.util.List;

public class ShopListener
{
    private final ShopController shopController;
    private List<Cosmetic> cosmetics;
    private List<Emote> emotes;
    
    ShopListener(final ShopController shopController) {
        this.shopController = shopController;
    }
    
    public void refreshOwnedShopItems() {
        final UserItems userItems = LabyMod.references().labyModNetService().getUserItems();
        if (userItems == null) {
            this.cosmetics = new ArrayList<Cosmetic>();
            this.emotes = new ArrayList<Emote>();
            return;
        }
        this.cosmetics = userItems.getCosmetics();
        if (this.cosmetics == null) {
            this.cosmetics = new ArrayList<Cosmetic>();
        }
        this.emotes = userItems.getEmotes();
        if (this.emotes == null) {
            this.emotes = new ArrayList<Emote>();
        }
    }
    
    public boolean isOwned(final ShopItem item) {
        final ItemType type = item.getType();
        final int id = item.getItemId();
        boolean owned = false;
        if (type == ItemType.COSMETIC) {
            for (final Cosmetic cosmetic : this.cosmetics) {
                if (cosmetic.getItemId() == id) {
                    owned = true;
                    break;
                }
            }
        }
        else if (type == ItemType.EMOTE) {
            for (final Emote emote : this.emotes) {
                if (emote.getItemId() == id) {
                    owned = true;
                    break;
                }
            }
        }
        return owned;
    }
    
    @Subscribe
    public void onLabyModNet(final LabyModNetRefreshEvent event) {
        this.refreshOwnedShopItems();
        for (final ShopItem shopItem : this.shopController.getShopItems()) {
            shopItem.setOwned(this.isOwned(shopItem));
        }
        final DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService)LabyMod.references().labyModNetService();
        if (labyModNetService.getState() != LabyModNetService.State.OK) {
            return;
        }
        final LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session == null) {
            return;
        }
        if (session.self().getUniqueId() == labyModNetService.getUserUniqueId()) {
            this.shopController.connectedToLabyConnect().set(true);
        }
    }
    
    @Subscribe
    public void updateOutfit(final UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        if (event.gameUser().getUniqueId().equals(Laby.labyAPI().getUniqueId())) {
            this.shopController.currentOutfit().useItemsFromUser(event.gameUser());
        }
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        if (event.previousSession().getUniqueId().equals(event.newSession().getUniqueId())) {
            return;
        }
        if (this.shopController.shoppingCart().clear()) {
            final Notification notification = Notification.builder().title(Component.text("LabyMod Shop")).text(Component.translatable("labymod.activity.shop.cart.notification.sessionClear", new Component[0])).build();
            Laby.references().notificationController().push(notification);
        }
    }
    
    @Subscribe
    public void onLabyConnect(final LabyConnectStateUpdateEvent event) {
        if (event.state() != LabyConnectState.PLAY) {
            this.shopController.connectedToLabyConnect().set(false);
            this.shopController.setLabyPlus(false);
            return;
        }
        final LabyConnectSession session = Laby.references().labyConnect().getSession();
        this.shopController.setLabyPlus(session != null && session.self().isLabyPlus());
        final DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService)LabyMod.references().labyModNetService();
        this.shopController.connectedToLabyConnect().set(labyModNetService.getState() == LabyModNetService.State.OK && labyModNetService.getUserUniqueId().equals(Laby.labyAPI().getUniqueId()));
    }
}
