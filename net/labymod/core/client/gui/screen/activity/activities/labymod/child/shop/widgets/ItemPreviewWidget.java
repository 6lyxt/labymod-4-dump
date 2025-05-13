// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.core.shop.ShoppingCart;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import net.labymod.core.shop.models.ItemType;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.core.shop.event.ShopItemOwnedStateUpdateEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.shop.models.ShopItem;
import java.util.function.BiConsumer;
import net.labymod.api.user.GameUser;
import java.util.function.Supplier;
import net.labymod.core.shop.ShopController;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ItemPreviewWidget extends AbstractWidget<Widget>
{
    private static final String TRANSLATION_KEY = "labymod.activity.shop.item.cart.";
    private final ShopController shopController;
    private final Supplier<GameUser> userSupplier;
    private final BiConsumer<ShopItem, Cosmetic> optionUpdateListener;
    private ShopItem previewedItem;
    
    public ItemPreviewWidget(final ShopController shopController, final Supplier<GameUser> userSupplier, final BiConsumer<ShopItem, Cosmetic> optionUpdateListener) {
        this.shopController = shopController;
        this.previewedItem = shopController.previewedItem().get();
        this.userSupplier = userSupplier;
        this.optionUpdateListener = optionUpdateListener;
        shopController.previewedItem().addChangeListener(value -> {
            this.previewedItem = value;
            this.reInitialize();
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.previewedItem == null) {
            return;
        }
        final ShoppingCart shoppingCart = this.shopController.shoppingCart();
        final boolean notOwned = !this.previewedItem.isOwned();
        final boolean isInCart = notOwned && shoppingCart.has(this.previewedItem);
        final boolean connectedToLabyConnect = this.shopController.connectedToLabyConnect().get();
        final HorizontalListWidget horizontalListWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)horizontalListWidget).addId("preview-button-wrapper");
        final ButtonWidget settingsButton = ButtonWidget.advancedSettings();
        ((AbstractWidget<Widget>)settingsButton).addId("settings-button");
        settingsButton.setVisible(false);
        settingsButton.setHoverComponent(Component.translatable("labymod.activity.shop.settingsTooltip", new Component[0]));
        horizontalListWidget.addEntry(settingsButton);
        String translationKeySuffix;
        if (this.previewedItem.isFree()) {
            translationKeySuffix = "redeem";
        }
        else {
            translationKeySuffix = (isInCart ? "remove" : "add");
        }
        final ButtonWidget cartButton = ButtonWidget.i18n("labymod.activity.shop.item.cart." + translationKeySuffix);
        if (isInCart) {
            ((AbstractWidget<Widget>)cartButton).addId("destroy-button");
        }
        else {
            ((AbstractWidget<Widget>)cartButton).addId("accent-button");
        }
        ((AbstractWidget<Widget>)cartButton).addId("manage-cart-button");
        cartButton.setEnabled(notOwned && connectedToLabyConnect);
        if (!notOwned) {
            cartButton.setHoverComponent(Component.translatable("labymod.activity.shop.item.cart.ownedTooltip", Component.text(this.previewedItem.getType().getNiceName())));
        }
        else if (!connectedToLabyConnect) {
            cartButton.setHoverComponent(Component.translatable("labymod.activity.shop.notConnected", Component.text(this.previewedItem.getType().getNiceName())));
        }
        cartButton.setPressable(() -> {
            cartButton.setEnabled(false);
            if (this.previewedItem.isFree()) {
                this.previewedItem.setOwned(true);
                Laby.fireEvent(new ShopItemOwnedStateUpdateEvent(this.previewedItem));
                return;
            }
            else {
                final ResultCallback<ShoppingCart.CartItem> callback = result -> {
                    if (!result.isPresent()) {
                        Laby.references().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.shop.name", new Component[0])).text(Component.translatable("labymod.activity.shop.itemNotAdded", new Component[0]).argument(Component.text(this.previewedItem.getName()))).build());
                    }
                    Laby.labyAPI().minecraft().executeOnRenderThread(this::reInitialize);
                    return;
                };
                if (isInCart) {
                    shoppingCart.removeItem(this.previewedItem, callback);
                }
                else {
                    shoppingCart.addItem(this.previewedItem, callback);
                }
                return;
            }
        });
        horizontalListWidget.addEntry(cartButton);
        CosmeticConfigurationWidget cosmeticConfigurationWidget = null;
        if (this.previewedItem.getType() == ItemType.COSMETIC) {
            Cosmetic cosmetic = null;
            cosmeticConfigurationWidget = new CosmeticConfigurationWidget(cosmetic -> {
                final GameUser user = this.userSupplier.get();
                ItemMetadataUtil.updateGameUser(cosmetic, user, null);
                this.optionUpdateListener.accept(this.previewedItem, cosmetic);
                return;
            });
            cosmeticConfigurationWidget.addId("cosmetic-settings");
            try {
                if (!cosmeticConfigurationWidget.isIdMatching(this.previewedItem.getId())) {
                    cosmeticConfigurationWidget.update(this.previewedItem.asCosmetic());
                }
                cosmetic = this.previewedItem.asCosmetic();
                if (cosmetic != null && cosmetic.getOptions() != null && cosmetic.getOptions().length > 0) {
                    settingsButton.setVisible(true);
                }
            }
            catch (final IllegalStateException e) {
                System.out.println("Could not get cosmetic settings for item " + this.previewedItem.getId());
            }
            ((AbstractWidget<CosmeticConfigurationWidget>)this).addChild(cosmeticConfigurationWidget);
        }
        final CosmeticConfigurationWidget finalWidget = cosmeticConfigurationWidget;
        if (finalWidget != null) {
            settingsButton.setPressable(() -> {
                if (finalWidget.hasId("hidden")) {
                    finalWidget.removeId("hidden");
                }
                else {
                    finalWidget.addId("hidden");
                }
                return;
            });
        }
        ((AbstractWidget<HorizontalListWidget>)this).addChild(horizontalListWidget);
    }
    
    public void refreshPreviewedItem() {
        if (this.previewedItem == null) {
            return;
        }
        this.reInitialize();
    }
}
