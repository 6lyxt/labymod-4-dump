// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.shop.ShoppingCart.CartStorage.ChangeListener;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import java.util.function.Consumer;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.client.gui.background.CameraTransitionUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.I18n;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.core.shop.models.ItemCategory;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.shop.ShoppingCart;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.shop.models.ItemType;
import java.util.function.Supplier;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.ItemPreviewWidget;
import net.labymod.core.client.render.model.CosmeticModelFocus;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.CartActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.ShopOverviewActivity;
import net.labymod.core.shop.ShopController;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity;

@AutoActivity
@Link("activity/shop/shop.lss")
public class ShopActivity extends AbstractSidebarActivity
{
    public static final String ROOT_TRANSLATION_KEY = "labymod.activity.shop.";
    private static final String TRANSLATION_KEY = "labymod.activity.shop.overview.";
    private final ShopController shopController;
    private final ShopOverviewActivity shopOverviewActivity;
    private final CartActivity cartActivity;
    private final PlayerModelWidget modelWidget;
    private final CosmeticModelFocus modelFocus;
    private ItemPreviewWidget itemPreviewWidget;
    private ComponentWidget cartCountWidget;
    private HorizontalListWidget header;
    private DivWidget modelWrapper;
    private ComponentWidget titleWidget;
    private String searchQuery;
    
    public ShopActivity() {
        super(false);
        this.modelFocus = new CosmeticModelFocus();
        this.shopController = LabyMod.references().shopController();
        this.shopController.connectedToLabyConnect().addChangeListener(connected -> {
            if (this.currentLabyScreen() == this && this.isOpen()) {
                this.reload();
            }
            return;
        });
        this.shopOverviewActivity = new ShopOverviewActivity(this.shopController, new Supplier<String>() {
            @Override
            public String get() {
                return ShopActivity.this.searchQuery;
            }
        }, new Runnable() {
            @Override
            public void run() {
                AbstractSidebarActivity.this.recheckSidebarButtons();
            }
        });
        this.cartActivity = new CartActivity(this.shopController);
        this.shopOverviewActivity.focusType(ItemType.FEATURED);
        this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
        this.screenRendererWidget.addPreDisplayListener(screenInstance -> {
            final ScreenRendererWidget screenRendererWidget2 = this.screenRendererWidget;
            final boolean previousIsCart = screenRendererWidget2.currentLabyScreen() instanceof CartActivity;
            final boolean newIsCart = screenInstance instanceof CartActivity;
            if (!previousIsCart && !newIsCart) {
                return;
            }
            else {
                if (newIsCart) {
                    this.previewCartCosmetics();
                }
                else {
                    this.previewCosmetic(this.shopController.previewedItem().get());
                }
                this.refreshActivityType(screenInstance);
                return;
            }
        });
        this.modelWidget = new PlayerModelWidget();
        final ShoppingCart.CartStorage storage = this.shopController.shoppingCart().storage();
        final Supplier<ScreenRendererWidget> screenRendererWidget = new Supplier<ScreenRendererWidget>() {
            @Override
            public ScreenRendererWidget get() {
                return ShopActivity.this.screenRendererWidget;
            }
        };
        final PlayerModelWidget modelWidget = this.modelWidget;
        Objects.requireNonNull(modelWidget);
        storage.setChangeListener(new ShopActivityChangeListener(screenRendererWidget, modelWidget::gameUser, new Runnable() {
            @Override
            public void run() {
                ShopActivity.this.previewCartCosmetics();
            }
        }));
        this.shopController.previewedItem().addChangeListener(this::updatePreviewedItem);
        Task.builder(new Runnable() {
            @Override
            public void run() {
                LabyMod.references().shopController().reload();
                Task.builder(new Runnable() {
                    @Override
                    public void run() {
                        ShopActivity.this.reload();
                    }
                }).build().executeOnRenderThread();
            }
        }).build().execute();
    }
    
    private void refreshActivityType() {
        this.refreshActivityType(this.screenRendererWidget.currentLabyScreen());
    }
    
    private void refreshActivityType(final ScreenInstance screenInstance) {
        Component titleComponent;
        if (screenInstance instanceof CartActivity) {
            this.itemPreviewWidget.setVisible(false);
            titleComponent = Component.translatable("labymod.activity.shop.overview.cart", new Component[0]);
        }
        else {
            this.itemPreviewWidget.setVisible(true);
            titleComponent = Component.translatable("labymod.activity.shop.name", new Component[0]);
        }
        if (this.titleWidget.component() == titleComponent) {
            return;
        }
        this.titleWidget.setComponent(titleComponent);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget containerWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)containerWrapper).addId("container-outer-wrapper");
        (this.modelWrapper = new DivWidget()).addId("model-wrapper");
        final ShopController shopController = this.shopController;
        final PlayerModelWidget modelWidget = this.modelWidget;
        Objects.requireNonNull(modelWidget);
        this.itemPreviewWidget = new ItemPreviewWidget(shopController, modelWidget::gameUser, (shopItem, cosmetic) -> {
            if (shopItem == null || shopItem.getType() == ItemType.EMOTE) {
                return;
            }
            else {
                this.modelFocus.updateModelFocus(this.modelWidget, shopItem.asGameUserItem().item(), cosmetic);
                return;
            }
        });
        ((AbstractWidget<ItemPreviewWidget>)this.modelWrapper).addChild(this.itemPreviewWidget);
        final UUID uniqueId = this.labyAPI.getUniqueId();
        this.modelWidget.setModel(uniqueId);
        this.updatePreviewedItem(this.shopController.previewedItem().get());
        this.modelWidget.update();
        this.modelWidget.addId("model");
        this.modelWidget.draggable().set(true);
        ((AbstractWidget<PlayerModelWidget>)this.modelWrapper).addChild(this.modelWidget);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        ((AbstractWidget<Widget>)(this.header = new HorizontalListWidget())).addId("header");
        this.header.addEntry(this.searchWidget);
        this.titleWidget = ComponentWidget.empty();
        this.refreshActivityType();
        this.titleWidget.addId("title");
        this.header.addEntry(this.titleWidget);
        final SidebarButtonWidget cartButton = SidebarButtonWidget.i18n("labymod.activity.shop.overview.cart", Textures.SpriteCommon.CART, screen -> this.searchQuery == null && screen instanceof CartActivity);
        ((AbstractWidget<Widget>)cartButton).addId("accent-button");
        ((AbstractWidget<Widget>)cartButton).addId("cart-button");
        cartButton.removeId("primary-button");
        cartButton.setPressable(() -> {
            if (this.searchQuery != null) {
                this.searchWidget.setText("");
            }
            this.shopController.previewedItem().set(null);
            this.screenRendererWidget.displayScreen(this.cartActivity);
            this.titleWidget.setComponent(Component.translatable("labymod.activity.shop.overview.cart", new Component[0]));
            return;
        });
        (this.cartCountWidget = ComponentWidget.text("")).addId("cart-count");
        this.updateCartCount();
        cartButton.addEntry(this.cartCountWidget);
        if (this.shopController.connectedToLabyConnect().get()) {
            this.header.addEntry(cartButton);
        }
        final ButtonWidget reload = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        ((AbstractWidget<Widget>)reload).addId("reload-button");
        reload.setPressable(new Pressable() {
            @Override
            public void press() {
                reload.setEnabled(false);
                Task.builder(new Runnable() {
                    @Override
                    public void run() {
                        ShopActivity.this.shopController.reload();
                        reload.setEnabled(true);
                        Task.builder(new Runnable() {
                            @Override
                            public void run() {
                                ShopActivity.this.reload();
                            }
                        }).build().executeOnRenderThread();
                    }
                }).build().execute();
            }
        });
        this.header.addEntry(reload);
        container.addContent(this.header);
        final FlexibleContentWidget innerContainer = ((AbstractWidget<FlexibleContentWidget>)this.document).getChild("container");
        innerContainer.removeId("container");
        ((AbstractWidget<Widget>)innerContainer).addId("container-inner-wrapper");
        ((AbstractWidget<FlexibleContentWidget>)this.document).removeChild(innerContainer);
        if (this.shopController.getShopItems().isEmpty()) {
            innerContainer.removeChildIf(entry -> entry.childWidget() == this.screenRendererWidget);
            final DivWidget infoWrapper = new DivWidget();
            infoWrapper.addId("info-wrapper");
            final ComponentWidget infoWidget = ComponentWidget.i18n("labymod.activity.shop.notLoaded");
            infoWidget.addId("info");
            ((AbstractWidget<ComponentWidget>)infoWrapper).addChild(infoWidget);
            innerContainer.addFlexibleContent(infoWrapper);
        }
        container.addFlexibleContent(innerContainer);
        containerWrapper.addFlexibleContent(container);
        containerWrapper.addContent(this.modelWrapper);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(containerWrapper);
        this.recheckSidebarButtons();
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        this.modelFocus.applyFocus(this.modelWidget);
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        final UUID uniqueId = event.newSession().getUniqueId();
        this.modelWidget.setModel(uniqueId);
        this.modelWidget.update();
    }
    
    @Override
    public void onCategoryListInitialize(final VerticalListWidget<Widget> categoryList) {
        final boolean hasFeatured = this.shopController.findCategoryByType(ItemType.FEATURED) != null;
        if (hasFeatured) {
            this.addTypeButton(categoryList, ItemType.FEATURED);
        }
        boolean dynamicFocusType = !hasFeatured;
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.shop.overview.cosmetics").addId("sub-category"));
        boolean addedCosmeticCategories = false;
        for (final ItemCategory category : this.shopController.getCategories()) {
            if (category.getType() != ItemType.COSMETIC) {
                continue;
            }
            if (dynamicFocusType) {
                this.shopOverviewActivity.focusType(category.getType());
                dynamicFocusType = false;
            }
            addedCosmeticCategories = true;
            this.addCategoryButton(categoryList, category);
        }
        if (!addedCosmeticCategories) {
            final ButtonWidget buttonWidget = ButtonWidget.i18n("labymod.activity.shop.overview.manage");
            buttonWidget.setPressListener(() -> this.openPlayerActivity(CosmeticsActivity.class));
            categoryList.addChild(buttonWidget);
        }
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.shop.overview.emotes").addId("sub-category"));
        boolean addedEmoteCategories = false;
        for (final ItemCategory category2 : this.shopController.getCategories()) {
            if (category2.getType() != ItemType.EMOTE) {
                continue;
            }
            addedEmoteCategories = true;
            this.addCategoryButton(categoryList, category2);
        }
        if (!addedEmoteCategories) {
            final ButtonWidget buttonWidget2 = ButtonWidget.i18n("labymod.activity.shop.overview.manage");
            buttonWidget2.setPressListener(() -> this.openPlayerActivity(EmotesActivity.class));
            categoryList.addChild(buttonWidget2);
        }
    }
    
    @Override
    public void onSearchUpdateListener(final String searchContent) {
        if (searchContent.isEmpty()) {
            this.searchQuery = null;
        }
        else {
            this.searchQuery = searchContent;
        }
        if (!(this.screenRendererWidget.currentLabyScreen() instanceof ShopOverviewActivity)) {
            this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
        }
        else {
            this.shopOverviewActivity.reload();
        }
        this.shopOverviewActivity.getSectionedListWidget().listSession().setScrollPositionY(0.0f);
        this.recheckSidebarButtons();
    }
    
    private boolean openPlayerActivity(final Class<? extends PlayerActivity.Child> childClass) {
        final LabyModActivity labyModActivity = LabyModActivity.getFromNavigationRegistry();
        if (labyModActivity == null) {
            return false;
        }
        final PlayerActivity playerActivity = labyModActivity.switchTab((Class<? extends PlayerActivity>)PlayerActivity.class);
        if (playerActivity == null) {
            return false;
        }
        playerActivity.displayChild(childClass);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(labyModActivity);
        return true;
    }
    
    private void addTypeButton(final VerticalListWidget<Widget> categoryList, final ItemType type) {
        String key = I18n.getTranslation("labymod.activity.shop.overview." + String.valueOf(type), new Object[0]);
        if (key == null) {
            key = type.toString();
        }
        for (final ItemCategory category : this.shopController.getCategories()) {
            if (category.getType() == type) {
                if (category.getSubType() != null) {
                    continue;
                }
                category.setLocalizedIdentifier(key);
            }
        }
        final ButtonWidget categoryWidget = SidebarButtonWidget.text(key, screenInstance -> {
            if (this.searchQuery != null) {
                return false;
            }
            else if (screenInstance instanceof final ShopOverviewActivity activity) {
                return activity.getItemType() == type;
            }
            else {
                return false;
            }
        });
        categoryWidget.icon().set(type.getIcon());
        categoryWidget.setPressable(() -> {
            if (this.searchQuery != null) {
                this.searchWidget.setText("");
            }
            final ScreenInstance screen = this.screenRendererWidget.getScreen();
            if (screen == null || screen != this.shopOverviewActivity) {
                this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
            }
            this.shopOverviewActivity.focusType(type);
            this.recheckSidebarButtons();
            this.titleWidget.setComponent(Component.translatable("labymod.activity.shop.name", new Component[0]));
            return;
        });
        categoryList.addChild(categoryWidget);
    }
    
    @Override
    protected void recheckSidebarButtons(final ScreenInstance screen) {
        super.recheckSidebarButtons(screen);
        this.recheckButtonsFor(this.header, screen);
    }
    
    private void addCategoryButton(final VerticalListWidget<Widget> categoryList, final ItemCategory category) {
        String key = I18n.getTranslation("labymod.activity.shop.overview." + String.valueOf(category.getType()) + "." + category.getIdentifier(), new Object[0]);
        if (key == null) {
            key = StringUtil.capitalizeWords(category.getIdentifier());
        }
        category.setLocalizedIdentifier(key);
        final ButtonWidget categoryWidget = SidebarButtonWidget.text(key, screenInstance -> {
            if (this.searchQuery != null) {
                return false;
            }
            else if (screenInstance instanceof final ShopOverviewActivity activity) {
                return activity.getFocusedCategory() == category;
            }
            else {
                return false;
            }
        });
        categoryWidget.icon().set(category.getIcon());
        categoryWidget.setPressable(new Pressable() {
            @Override
            public void press() {
                if (ShopActivity.this.searchQuery != null) {
                    ShopActivity.this.searchWidget.setText("");
                }
                final ScreenInstance screen = ShopActivity.this.screenRendererWidget.getScreen();
                if (screen == null || screen != ShopActivity.this.shopOverviewActivity) {
                    ShopActivity.this.screenRendererWidget.displayScreen(ShopActivity.this.shopOverviewActivity);
                }
                ShopActivity.this.shopOverviewActivity.focusCategory(category, new Runnable() {
                    @Override
                    public void run() {
                        AbstractSidebarActivity.this.recheckSidebarButtons();
                    }
                });
            }
        });
        categoryList.addChild(categoryWidget);
    }
    
    @Subscribe
    public void onCartUpdate(final ShopCartUpdateEvent event) {
        this.updateCartCount();
        this.itemPreviewWidget.refreshPreviewedItem();
        this.cartActivity.reload();
    }
    
    private void updatePreviewedItem(final ShopItem shopItem) {
        if (this.screenRendererWidget.currentLabyScreen() instanceof CartActivity) {
            this.previewCartCosmetics();
            return;
        }
        try {
            this.previewCosmetic(shopItem);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    private void previewCosmetic(final ShopItem shopItem) {
        if (shopItem == null) {
            this.modelWidget.stopEmote(null);
            this.modelFocus.reset(this.modelWidget);
            return;
        }
        this.overwriteModelCosmetics(userData -> {
            this.modelWidget.stopEmote(null);
            if (shopItem.getType() == ItemType.EMOTE) {
                this.modelFocus.reset(this.modelWidget);
                final EmoteItem emote = LabyMod.references().emoteService().getEmote(shopItem.getItemId());
                if (emote != null) {
                    this.modelWidget.playEmote(emote);
                }
            }
            else {
                final GameUserItem gameUserItem = shopItem.asGameUserItem();
                if (gameUserItem != null) {
                    userData.getItems().add(gameUserItem);
                    this.modelFocus.updateModelFocus(this.modelWidget, gameUserItem.item(), shopItem.asCosmetic());
                    final Location location = DynamicBackgroundController.SHOP_PLAYER_CAMERA;
                    final double pitch = (location.getPitch() + this.getPitch(location.getY(), this.modelFocus.translation().getY())) / 180.0;
                    CameraTransitionUtil.execute(location.getX(), location.getY(), location.getZ(), location.getYaw(), pitch, location.getRoll());
                }
            }
        });
    }
    
    private double getPitch(final double y1, final double y2) {
        final double diff = y1 - y2;
        return Math.atan2(diff, 1.0) * 57.29577951308232;
    }
    
    private void previewCartCosmetics() {
        this.overwriteModelCosmetics(new Consumer<GameUserData>() {
            @Override
            public void accept(final GameUserData userData) {
                ShopActivity.this.shopController.shoppingCart().forEachGameItem(new Consumer<GameUserItem>(this) {
                    @Override
                    public void accept(final GameUserItem gameItem) {
                        userData.getItems().add(gameItem);
                    }
                });
            }
        });
    }
    
    public void overwriteModelCosmetics(final Consumer<GameUserData> userDataConsumer) {
        final DefaultGameUser user = (DefaultGameUser)this.modelWidget.gameUser();
        final GameUserData userData = user.getUserData();
        userData.getItems().clear();
        userDataConsumer.accept(userData);
        user.getUserItemStorage().prepare(user, userData);
        LabyMod.references().shopItemLayer().resetAnimations(user, true);
    }
    
    private void updateCartCount() {
        if (this.cartCountWidget == null) {
            return;
        }
        final int size = this.shopController.shoppingCart().size();
        this.cartCountWidget.setComponent(Component.text(size));
        if (size > 0) {
            this.cartCountWidget.addId("cart-count-active");
        }
        else {
            this.cartCountWidget.removeId("cart-count-active");
        }
    }
    
    record ShopActivityChangeListener(Supplier<ScreenRendererWidget> screenRendererWidget, Supplier<GameUser> gameUser, Runnable previewCartCosmetics) implements ShoppingCart.CartStorage.ChangeListener {
        @Override
        public void onChange(final ChangeType type, final int cosmeticId) {
            final ScreenRendererWidget screenRendererWidget = this.screenRendererWidget.get();
            if (screenRendererWidget == null) {
                return;
            }
            if (!(screenRendererWidget.currentLabyScreen() instanceof CartActivity)) {
                return;
            }
            switch (type) {
                case ADD: {
                    break;
                }
                case REMOVE: {
                    final DefaultGameUser user = this.gameUser.get();
                    this.removeItem(user, cosmeticId);
                    break;
                }
                case CLEAR: {
                    this.previewCartCosmetics.run();
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
                }
            }
        }
        
        private void removeItem(final DefaultGameUser user, final int cosmeticId) {
            final GameUserData userData = user.getUserData();
            final GameUserItem item = userData.getItem(cosmeticId);
            if (item == null) {
                return;
            }
            userData.getItems().remove(item);
        }
    }
}
