// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.shop.event.CurrencyUpdateEvent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.shop.models.PriceItem;
import java.util.List;
import net.labymod.core.shop.models.config.ShopConfig;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.shop.models.cart.PromoCodeResponse;
import net.labymod.core.shop.models.config.ShopCurrency;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.TextComponent;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.shop.ShoppingCart;
import net.labymod.core.shop.ShopController;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import java.text.DecimalFormat;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
@Link("activity/shop/cart.lss")
public class CartActivity extends Activity
{
    private static final DecimalFormat FORMAT;
    private final ListSession<Widget> listSession;
    private final ShopController shopController;
    private final ShoppingCart cart;
    private boolean checkingOut;
    
    public CartActivity(final ShopController shopController) {
        this.listSession = new ListSession<Widget>();
        this.shopController = shopController;
        this.cart = shopController.shoppingCart();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ShopCurrency selectedCurrency = this.shopController.getSelectedCurrency();
        final boolean cartEmpty = this.cart.isEmpty();
        final boolean showInfo = cartEmpty || this.checkingOut;
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("cart-container");
        if (showInfo) {
            final DivWidget infoContainer = new DivWidget();
            infoContainer.addId("cart-info-container");
            if (cartEmpty) {
                final ComponentWidget info = ComponentWidget.i18n("labymod.activity.shop.cart.empty");
                info.addId("cart-info");
                ((AbstractWidget<ComponentWidget>)infoContainer).addChild(info);
            }
            else {
                final VerticalListWidget<Widget> checkingOutWrapper = new VerticalListWidget<Widget>();
                checkingOutWrapper.addId("cart-checking-out-wrapper");
                final ComponentWidget info2 = ComponentWidget.i18n("labymod.activity.shop.cart.checkingOut");
                info2.addId("cart-info");
                checkingOutWrapper.addChild(info2);
                final ButtonWidget cancelButton = ButtonWidget.i18n("labymod.activity.shop.cart.cancelCheckout");
                ((AbstractWidget<Widget>)cancelButton).addId("cart-cancel");
                cancelButton.setPressable(() -> {
                    this.checkingOut = false;
                    this.cart.clear();
                    this.reload();
                    return;
                });
                checkingOutWrapper.addChild(cancelButton);
                ((AbstractWidget<VerticalListWidget<Widget>>)infoContainer).addChild(checkingOutWrapper);
            }
            container.addFlexibleContent(infoContainer);
        }
        else {
            final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>(this.listSession);
            list.addId("cart-list");
            this.cart.forEachShopItem(shopItem -> {
                final HorizontalListWidget itemContainer = new HorizontalListWidget();
                ((AbstractWidget<Widget>)itemContainer).addId("cart-item-container");
                final IconWidget icon = new IconWidget(shopItem.getPrimaryIcon());
                icon.addId("item-icon");
                itemContainer.addEntry(icon);
                final ComponentWidget name = ComponentWidget.text(shopItem.getName());
                name.addId("item-name");
                itemContainer.addEntry(name);
                final ButtonWidget removeButton = ButtonWidget.icon(Textures.SpriteCommon.X);
                ((AbstractWidget<Widget>)removeButton).addId("item-remove");
                removeButton.setPressable(() -> {
                    removeButton.setEnabled(false);
                    this.cart.removeItem(shopItem, result -> {
                        if (!result.isPresent()) {
                            Laby.references().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.shop.name", new Component[0])).text(Component.translatable("labymod.activity.shop.itemNotRemoved", new Component[0]).argument(Component.text(shopItem.getName()))).build());
                        }
                    });
                    return;
                });
                itemContainer.addEntry(removeButton);
                if (selectedCurrency != null) {
                    final PriceItem priceFor = selectedCurrency.getPriceFor(shopItem);
                    if (priceFor != null) {
                        String priceString;
                        if (priceFor.isOnlyLifetime()) {
                            priceString = "" + priceFor.getLifetime();
                        }
                        else {
                            priceString = "-1";
                        }
                        itemContainer.addEntry(ComponentWidget.text(priceString + selectedCurrency.getSymbol()).addId("item-price"));
                    }
                }
                list.addChild(itemContainer);
                return;
            });
            container.addFlexibleContent(new ScrollWidget(list));
        }
        if (!this.checkingOut) {
            final FlexibleContentWidget footerContainer = new FlexibleContentWidget();
            ((AbstractWidget<Widget>)footerContainer).addId("cart-footer");
            final HorizontalListWidget footerTop = new HorizontalListWidget();
            ((AbstractWidget<Widget>)footerTop).addId("top-footer");
            final AtomicReference<Float> totalPrice = new AtomicReference<Float>(0.0f);
            final AtomicReference<Float> totalSalePrice = new AtomicReference<Float>(0.0f);
            final PromoCodeResponse.Code promoCode = this.cart.getPromoCode();
            final int shopItemId = (promoCode == null) ? -1 : promoCode.getShopItemId();
            final float saleMultiplier = (promoCode == null) ? 1.0f : promoCode.getMultiplier();
            if (selectedCurrency != null) {
                this.cart.forEachShopItem(item -> {
                    final PriceItem priceFor2 = selectedCurrency.getPriceFor(item);
                    if (priceFor2 == null) {
                        return;
                    }
                    else {
                        if (priceFor2.isOnlyLifetime()) {
                            totalPrice.updateAndGet(v -> v + priceFor.getLifetime());
                            totalSalePrice.updateAndGet(v -> {
                                if (shopItemId == -1 || shopItemId == item.getId()) {
                                    return Float.valueOf(v + priceFor.getLifetime() * saleMultiplier);
                                }
                                else {
                                    return Float.valueOf(v + priceFor.getLifetime());
                                }
                            });
                        }
                        return;
                    }
                });
            }
            final String currencySymbol = (selectedCurrency == null) ? "?" : selectedCurrency.getSymbol();
            final String totalPriceString = this.floatToCurrency(totalPrice.get());
            final String totalSalePriceString = this.floatToCurrency(totalSalePrice.get());
            Component totalArgument;
            if (totalPriceString.equals(totalSalePriceString)) {
                totalArgument = Component.text(totalPriceString + currencySymbol);
            }
            else {
                totalArgument = ((BaseComponent<Component>)Component.empty().append(((BaseComponent<Component>)Component.text(totalPriceString + currencySymbol).decorate(TextDecoration.STRIKETHROUGH)).color(NamedTextColor.RED)).append(Component.space())).append(Component.text(totalSalePriceString + currencySymbol));
            }
            final ComponentWidget totalWidget = ComponentWidget.component(Component.translatable("labymod.activity.shop.cart.total", totalArgument));
            totalWidget.addId("cart-total");
            footerTop.addEntry(totalWidget);
            Component promocodeComponent;
            if (this.cart.getPromoCode() == null) {
                promocodeComponent = Component.translatable("labymod.activity.shop.cart.promocode.add.button", new Component[0]);
            }
            else {
                promocodeComponent = Component.translatable("labymod.activity.shop.cart.promocode.remove.button", Component.text(this.cart.getPromoCode().getName()));
            }
            final ButtonWidget promocodeButton = ButtonWidget.component(promocodeComponent);
            ((AbstractWidget<Widget>)promocodeButton).addId("cart-promocode");
            promocodeButton.setPressable(() -> {
                if (this.cart.getPromoCode() != null) {
                    promocodeButton.setEnabled(false);
                    this.cart.removePromoCode(result -> {
                        promocodeButton.setEnabled(true);
                        if (!result.hasException()) {
                            this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove.success");
                        }
                        else {
                            final Throwable exception = result.exception().getCause();
                            if (exception instanceof final ShoppingCart.ShoppingCartKeywordException keywordException) {
                                this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove." + keywordException.getMessage());
                            }
                            else {
                                exception.printStackTrace();
                                this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove.error");
                            }
                        }
                    });
                    return;
                }
                else {
                    final TextFieldWidget textField = new TextFieldWidget();
                    textField.setFocused(true);
                    PopupWidget.builder().title(Component.translatable("labymod.activity.shop.cart.promocode.popup.title", new Component[0])).widgetSupplier(() -> textField).confirmComponent(Component.translatable("labymod.activity.shop.cart.promocode.popup.redeem", new Component[0])).confirmCallback(() -> {
                        final String text = textField.getText().trim();
                        if (!text.isEmpty()) {
                            promocodeButton.setEnabled(false);
                            this.cart.applyPromoCode(text.toUpperCase(Locale.ROOT), result -> {
                                promocodeButton.setEnabled(true);
                                if (!result.hasException()) {
                                    this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add.success");
                                }
                                else {
                                    final Throwable exception2 = result.exception().getCause();
                                    if (exception2 instanceof final ShoppingCart.ShoppingCartKeywordException keywordException2) {
                                        this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add." + keywordException2.getMessage());
                                    }
                                    else {
                                        exception2.printStackTrace();
                                        this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add.undefined_error");
                                    }
                                }
                            });
                        }
                    }).build().displayInOverlay();
                    return;
                }
            });
            footerTop.addEntry(promocodeButton);
            footerContainer.addContent(footerTop);
            final HorizontalListWidget footerBottom = new HorizontalListWidget();
            ((AbstractWidget<Widget>)footerBottom).addId("bottom-footer");
            final ShopConfig config = this.shopController.getConfig();
            if (config != null) {
                final DropdownWidget<ShopCurrency> currencyDropdown = new DropdownWidget<ShopCurrency>();
                final List<ShopCurrency> currencies = config.getCurrencies();
                final ShopCurrency autoCurrency = new ShopCurrency(config.getDefaultCurrency());
                currencyDropdown.add(autoCurrency);
                ShopCurrency currency = selectedCurrency;
                final String preferredCurrency = Laby.labyAPI().config().other().preferredCurrency().get();
                if (preferredCurrency.equals("null")) {
                    currency = autoCurrency;
                }
                currencyDropdown.addAll(currencies);
                currencyDropdown.setSelected(currency);
                currencyDropdown.setChangeListener(newCurrency -> {
                    final ConfigProperty<String> currencyProperty = Laby.labyAPI().config().other().preferredCurrency();
                    if (newCurrency.isDummy()) {
                        currencyProperty.set("null");
                    }
                    else {
                        currencyProperty.set(newCurrency.getCode());
                    }
                    return;
                });
                footerBottom.addEntry(currencyDropdown);
            }
            final ButtonWidget checkoutButton = ButtonWidget.i18n("labymod.activity.shop.cart.checkout");
            ((AbstractWidget<Widget>)checkoutButton).addId("accent-button");
            checkoutButton.removeId("primary-button");
            ((AbstractWidget<Widget>)checkoutButton).addId("cart-checkout");
            checkoutButton.setEnabled(!cartEmpty && this.cart.getCartId() != null);
            checkoutButton.setPressable(() -> {
                this.checkingOut = true;
                String urlSuffix = "?cart_uuid=" + String.valueOf(this.cart.getCartId());
                if (selectedCurrency != null) {
                    urlSuffix = urlSuffix + "&currency=" + selectedCurrency.getCode();
                }
                final PromoCodeResponse.Code code = this.cart.getPromoCode();
                if (code instanceof PromoCodeResponse.RefCode) {
                    urlSuffix = urlSuffix + "&ref=" + code.getCode();
                }
                Laby.labyAPI().minecraft().chatExecutor().openUrl("https://www.labymod.net/checkout" + urlSuffix);
                this.reload();
                return;
            });
            footerBottom.addEntry(checkoutButton);
            footerContainer.addContent(footerBottom);
            container.addContent(footerContainer);
        }
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Subscribe
    public void onCurrencyUpdate(final CurrencyUpdateEvent event) {
        this.reload();
    }
    
    public void onCartUpdate(final ShopCartUpdateEvent event) {
        Laby.labyAPI().minecraft().executeOnRenderThread(this::reload);
    }
    
    private String floatToCurrency(final float price) {
        return CartActivity.FORMAT.format(price);
    }
    
    static {
        FORMAT = new DecimalFormat("#.##");
    }
}
