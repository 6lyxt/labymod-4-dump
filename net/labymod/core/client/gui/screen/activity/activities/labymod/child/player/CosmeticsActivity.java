// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import java.util.function.Consumer;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.user.GameUser;
import java.util.Collection;
import java.util.ArrayList;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.DefaultGameUser;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.TileWidget;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.List;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.api.util.StringUtil;
import net.labymod.core.labymodnet.models.Cosmetic;
import java.util.Locale;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import java.util.LinkedHashMap;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import java.util.function.Predicate;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.api.util.TextFormat;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.LabyMod;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import net.labymod.core.client.render.model.CosmeticModelFocus;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import java.util.Map;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;

@AutoActivity
@Link("activity/player/cosmetics.lss")
public class CosmeticsActivity extends PlayerActivity.Child
{
    private static final String IDENTIFIER = "cosmetics";
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.customization.cosmetics.status.";
    private final ListSession<?> listSession;
    private final DefaultLabyModNetService labyModNetService;
    private final Map<String, Boolean> expandedCategories;
    private final TextFieldWidget searchFieldWidget;
    private final ComponentWidget statusWidget;
    private final CosmeticModelFocus cosmeticModelFocus;
    private final ItemTextureListener itemTextureListener;
    private VerticalListWidget<Widget> listWidget;
    private String previousQuery;
    private boolean showEnabled;
    
    public CosmeticsActivity(final PlayerActivity playerActivity, final String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "cosmetics.", "cosmetics");
        this.listSession = new ListSession<Object>();
        this.cosmeticModelFocus = new CosmeticModelFocus();
        this.itemTextureListener = playerActivity.itemTextureListener();
        this.expandedCategories = new HashMap<String, Boolean>();
        this.labyModNetService = (DefaultLabyModNetService)LabyMod.references().labyModNetService();
        (this.statusWidget = ComponentWidget.empty()).addId("status");
        final Map<String, Boolean> expandedCategories = new HashMap<String, Boolean>();
        (this.searchFieldWidget = new TextFieldWidget()).addId("search-field");
        this.searchFieldWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchFieldWidget.updateListener(text -> {
            final String trim = text.trim();
            if (this.previousQuery == null && !trim.isEmpty()) {
                this.previousQuery = trim;
                expandedCategories.clear();
                if (this.listWidget != null) {
                    this.listWidget.getChildren().iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final Widget child = iterator.next();
                        if (child instanceof FoldingWidget) {
                            expandedCategories.put(child.getIds().get(0).toString(), ((FoldingWidget)child).isExpanded());
                        }
                    }
                }
            }
            else if (this.previousQuery != null && trim.isEmpty()) {
                this.previousQuery = null;
                this.expandedCategories.clear();
                this.expandedCategories.putAll(expandedCategories);
            }
            this.updateStatus();
            this.initializeCosmetics();
        });
    }
    
    @Override
    public String getTranslationKeyPrefix() {
        return this.translationKeyPrefix;
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (!this.showEnabled && this.searchFieldWidget.getText().trim().isEmpty()) {
            this.expandedCategories.clear();
            if (this.listWidget != null) {
                for (final Widget child : this.listWidget.getChildren()) {
                    if (child instanceof final FoldingWidget foldingWidget) {
                        this.expandedCategories.put(child.getIds().get(0).toString(), foldingWidget.isExpanded());
                    }
                }
            }
        }
        super.initialize(parent);
        ((AbstractWidget<ComponentWidget>)this.document).addChild(this.statusWidget);
        final FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)contentWidget).addId("content");
        final HorizontalListWidget contentHeader = new HorizontalListWidget();
        ((AbstractWidget<Widget>)contentHeader).addId("content-header");
        contentHeader.addEntry(this.searchFieldWidget);
        final ButtonWidget collapseAllButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_UP_SHADOW);
        ((AbstractWidget<Widget>)collapseAllButton).addId("collapse-button", "icon-button");
        collapseAllButton.setHoverComponent(Component.translatable(this.translationKeyPrefix + "collapse", new Component[0]));
        collapseAllButton.setPressable(() -> {
            if (this.listWidget == null) {
                return;
            }
            else {
                this.listWidget.getChildren().iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final Widget child2 = iterator2.next();
                    if (child2 instanceof final FoldingWidget foldingWidget2) {
                        foldingWidget2.setExpanded(false);
                    }
                }
                return;
            }
        });
        contentHeader.addEntry(collapseAllButton);
        final ButtonWidget expandAllButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_DOWN_SHADOW);
        ((AbstractWidget<Widget>)expandAllButton).addId("expand-button", "icon-button");
        expandAllButton.setHoverComponent(Component.translatable(this.translationKeyPrefix + "expand", new Component[0]));
        expandAllButton.setPressable(() -> {
            if (this.listWidget == null) {
                return;
            }
            else {
                this.listWidget.getChildren().iterator();
                final Iterator iterator3;
                while (iterator3.hasNext()) {
                    final Widget child3 = iterator3.next();
                    if (child3 instanceof final FoldingWidget foldingWidget3) {
                        foldingWidget3.setExpanded(true);
                    }
                }
                return;
            }
        });
        contentHeader.addEntry(expandAllButton);
        final CheckBoxWidget onlyShowEnabled = new CheckBoxWidget();
        onlyShowEnabled.addId("only-show-enabled");
        onlyShowEnabled.setState(this.showEnabled ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
        onlyShowEnabled.setPressable(() -> {
            this.showEnabled = !this.showEnabled;
            this.initializeCosmetics();
            return;
        });
        final ButtonWidget onlyShowEnabledButton = ButtonWidget.i18n(this.translationKeyPrefix + "onlyShowEnabled");
        ((AbstractWidget<Widget>)onlyShowEnabledButton).addId("only-show-enabled-button");
        final ButtonWidget buttonWidget = onlyShowEnabledButton;
        final CheckBoxWidget obj = onlyShowEnabled;
        Objects.requireNonNull(obj);
        buttonWidget.setActionListener(obj::onPress);
        onlyShowEnabledButton.addEntry(onlyShowEnabled);
        contentHeader.addEntry(onlyShowEnabledButton);
        contentWidget.addContent(contentHeader);
        (this.listWidget = new VerticalListWidget<Widget>()).addId("cosmetic-list");
        final ScrollWidget scrollWidget = new ScrollWidget(this.listWidget, this.listSession);
        scrollWidget.addId("cosmetic-list-scroll", "player-scroll");
        contentWidget.addFlexibleContent(scrollWidget);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(contentWidget);
        this.updateStatus();
        this.initializeCosmetics();
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        this.cosmeticModelFocus.applyFocus(this.playerActivity.modelWidget());
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean flag = super.mouseClicked(mouse, mouseButton);
        if (!flag) {
            this.closeOpenOptions();
            this.resetModelFocus();
        }
        return flag;
    }
    
    @Override
    public void onCloseScreen() {
        final PlayerModelWidget modelWidget = this.playerActivity.modelWidget();
        modelWidget.translation().set(0.0f, 0.0f, 0.0f);
        modelWidget.setMaxTranslation(0.0f, 0.0f);
        modelWidget.scale().set(1.0f, 1.0f, 1.0f);
        modelWidget.setMaxScale(1.0f);
        super.onCloseScreen();
    }
    
    private void updateStatus() {
        final LabyModNetService.State state = this.labyModNetService.getState();
        final String suffix = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
        String translationKey;
        if (suffix.equals("loading")) {
            translationKey = "labymod.misc.loading";
        }
        else {
            translationKey = "labymod.activity.customization.cosmetics.status." + suffix;
        }
        this.statusWidget.setComponent(Component.translatable(translationKey, new Component[0]));
        final boolean hasQuery = this.searchFieldWidget != null && !this.searchFieldWidget.getText().trim().isEmpty();
        if (hasQuery) {
            this.statusWidget.setComponent(Component.translatable(this.translationKeyPrefix + "notFoundSearch", new Component[0]));
        }
        else {
            this.statusWidget.setComponent(Component.translatable(this.translationKeyPrefix + "notFound", new Component[0]));
        }
    }
    
    public CosmeticSettingsWidget getOpenSettings() {
        final DivWidget modelWrapper = this.playerActivity.getModelExtraContainer();
        if (modelWrapper == null) {
            return null;
        }
        final DivWidget divWidget = modelWrapper;
        final Class<CosmeticSettingsWidget> obj = CosmeticSettingsWidget.class;
        Objects.requireNonNull(obj);
        final Widget existingSettings = divWidget.findFirstChildIf((Predicate<Widget>)obj::isInstance);
        if (existingSettings instanceof final CosmeticSettingsWidget cosmeticSettingsWidget) {
            return cosmeticSettingsWidget;
        }
        return null;
    }
    
    public void closeOpenOptions() {
        final CosmeticSettingsWidget existingSettings = this.getOpenSettings();
        if (existingSettings != null) {
            ((AbstractWidget<CosmeticSettingsWidget>)this.playerActivity.getModelExtraContainer()).removeChild(existingSettings);
            existingSettings.onCloseSettings(existingSettings);
        }
    }
    
    public void displayOptions(final CosmeticSettingsWidget widget) {
        this.closeOpenOptions();
        widget.onOpenSettings(widget);
        this.playerActivity.addWidgetToModelWrapper(widget);
    }
    
    private void initializeCosmetics() {
        final LabyModNetService.State state = this.labyModNetService.getState();
        if (state != LabyModNetService.State.OK) {
            return;
        }
        this.listWidget.removeChildIf(widget -> true);
        final WidgetOptionService widgetOptionService = this.labyModNetService.widgetOptionService();
        final List<Cosmetic> cosmetics = this.labyModNetService.getUserItems().getCosmetics();
        final DefaultGameUserService gameUserService = (DefaultGameUserService)Laby.references().gameUserService();
        final Map<String, TilesGridWidget<CosmeticWidget>> categoryWidgets = new LinkedHashMap<String, TilesGridWidget<CosmeticWidget>>();
        final TilesGridWidget<CosmeticWidget> uncategorized = new TilesGridWidget<CosmeticWidget>();
        categoryWidgets.put("Uncategorized", uncategorized);
        final String query = this.searchFieldWidget.getText().trim().toLowerCase(Locale.ROOT);
        final CosmeticSettingsWidget openSettings = this.getOpenSettings();
        int openSettingsId;
        if (openSettings == null) {
            openSettingsId = -1;
        }
        else {
            openSettingsId = openSettings.cosmetic().getItemId();
        }
        for (final Cosmetic cosmetic : cosmetics) {
            boolean matches = !this.showEnabled || cosmetic.isEnabled();
            if (matches) {
                matches = (query.length() == 0 || StringUtil.toLowercase(cosmetic.getName()).contains(query));
            }
            if (!matches) {
                continue;
            }
            final AbstractItem item = gameUserService.itemService().getItemById(cosmetic.getItemId());
            final CosmeticWidget widget = new CosmeticWidget(this, widgetOptionService, cosmetic, item, this::onButtonPress);
            widget.setItemTextureListener(this.itemTextureListener);
            if (openSettingsId != -1 && cosmetic.getItemId() == openSettingsId) {
                widget.onOpenSettings(openSettings);
            }
            if (item == null || item.itemDetails().getCategory() == null) {
                uncategorized.addTile(widget);
            }
            else {
                final String identifier = item.itemDetails().getCategory();
                categoryWidgets.computeIfAbsent(identifier, s -> new TilesGridWidget()).addTile(widget);
            }
        }
        for (Map.Entry<String, TilesGridWidget<CosmeticWidget>> entry : categoryWidgets.entrySet()) {
            final int size = entry.getValue().getChildren().size();
            if (size == 0) {
                continue;
            }
            final DivWidget divWidget = new DivWidget();
            divWidget.addId("category-wrapper");
            ((AbstractWidget<ComponentWidget>)divWidget).addChild(ComponentWidget.text(this.getAsTitle(entry.getKey())));
            final String identifier2 = "category-" + entry.getKey().toLowerCase(Locale.ROOT).replace("_", "-");
            final Boolean expanded = this.expandedCategories.get(identifier2);
            final FoldingWidget foldingWidget = new FoldingWidget(divWidget, entry.getValue(), this.showEnabled || query.length() != 0 || ((expanded != null) ? expanded : (size < 6)));
            foldingWidget.addId(identifier2, "category");
            if (((Document)this.document).isInitialized()) {
                this.listWidget.addChildInitialized(foldingWidget, false);
            }
            else {
                this.listWidget.addChild(foldingWidget, false);
            }
        }
        this.updateFilter();
    }
    
    private void onButtonPress(final Cosmetic cosmetic) {
        if (cosmetic.getGroupId() == null) {
            return;
        }
        for (final Widget child : this.listWidget.getChildren()) {
            if (child instanceof final FoldingWidget widget) {
                final Widget contentWidget = widget.contentWidget();
                if (!(contentWidget instanceof TilesGridWidget)) {
                    continue;
                }
                final TilesGridWidget<?> gridWidget = (TilesGridWidget<?>)contentWidget;
                for (final TileWidget<?> tileWidget : gridWidget.getChildren()) {
                    final Widget o = (Widget)tileWidget.childWidget();
                    if (o instanceof final CosmeticWidget cosmeticWidget) {
                        final Cosmetic otherCosmetic = cosmeticWidget.cosmetic();
                        if (otherCosmetic.getGroupId() == null) {
                            continue;
                        }
                        if (otherCosmetic.getId() == cosmetic.getId()) {
                            continue;
                        }
                        if (!Objects.equals(otherCosmetic.getGroupId(), cosmetic.getGroupId())) {
                            continue;
                        }
                        cosmeticWidget.updateCosmeticEnabled(false, true);
                    }
                }
            }
        }
    }
    
    private void updateFilter() {
        final AtomicInteger count = new AtomicInteger();
        final String query = this.searchFieldWidget.getText().trim().toLowerCase(Locale.ROOT);
        this.forCosmeticWidgetRecursive(this.listWidget, cosmeticWidget -> {
            boolean matches = !this.showEnabled || cosmeticWidget.cosmetic().isEnabled();
            if (matches) {
                matches = (query.length() == 0 || cosmeticWidget.cosmetic().getName().toLowerCase(Locale.ROOT).contains(query));
            }
            cosmeticWidget.setVisible(matches);
            if (matches) {
                count.incrementAndGet();
            }
            return;
        });
        this.statusWidget.setVisible(count.get() == 0);
    }
    
    private String getAsTitle(final String text) {
        final StringBuilder builder = new StringBuilder();
        final String[] split;
        final String[] s = split = text.toLowerCase(Locale.ROOT).split(" ");
        for (final String word : split) {
            builder.append(' ');
            if (word.length() != 0) {
                builder.append(Character.toUpperCase(word.charAt(0)));
                builder.append(word.substring(1));
            }
        }
        return builder.substring();
    }
    
    public void updateModelFocus(final AbstractItem item, final Cosmetic cosmetic) {
        final GameUserItem userItem = ((DefaultGameUser)Laby.references().gameUserService().clientGameUser()).getUserData().getItem(cosmetic.getItemId());
        this.cosmeticModelFocus.updateModelFocus(this.playerActivity.modelWidget(), item, cosmetic, userItem);
    }
    
    public void resetModelFocus() {
        this.cosmeticModelFocus.reset(this.playerActivity.modelWidget());
    }
    
    public int getOpenSettingsId() {
        final CosmeticSettingsWidget openSettings = this.getOpenSettings();
        return (openSettings == null) ? -1 : openSettings.cosmetic().getItemId();
    }
    
    @Subscribe
    public void refreshCosmetics(final LabyModNetRefreshEvent event) {
        this.reload();
    }
    
    @Subscribe
    public void onUserDataUpdate(final UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final GameUser gameUser = event.gameUser();
        if (!gameUser.getUniqueId().equals(this.uniqueId)) {
            return;
        }
        if (this.labyModNetService.getState() != LabyModNetService.State.OK) {
            return;
        }
        final DefaultGameUser defaultGameUser = (DefaultGameUser)gameUser;
        final List<GameUserItem> items = defaultGameUser.getUserData().getItems();
        final List<GameUserItem> unknownCosmetics = new ArrayList<GameUserItem>(items);
        this.forCosmeticWidgetRecursive(this.listWidget, widget -> {
            final Cosmetic widgetCosmetic = widget.cosmetic();
            final int itemId = widgetCosmetic.getItemId();
            boolean found = false;
            items.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final GameUserItem item = iterator.next();
                if (item.item().getIdentifier() == itemId) {
                    unknownCosmetics.remove(item);
                    found = true;
                    break;
                }
            }
            if (widget.isWaitingForResponse()) {
                return;
            }
            else {
                widget.setCosmeticActive(found);
                return;
            }
        });
        if (!unknownCosmetics.isEmpty()) {
            this.labyModNetService.reload();
        }
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.HELLO || event.state() == LabyConnectState.LOGIN) {
            return;
        }
        this.reload();
    }
    
    private void forCosmeticWidgetRecursive(final Widget widget, final Consumer<CosmeticWidget> cosmeticWidget) {
        if (widget == null) {
            return;
        }
        for (Widget actualWidget : widget.getChildren()) {
            final Widget child = actualWidget;
            if (actualWidget instanceof final WrappedWidget wrappedWidget) {
                actualWidget = wrappedWidget.childWidget();
            }
            if (actualWidget instanceof final FoldingWidget foldingWidget) {
                actualWidget = foldingWidget.contentWidget();
            }
            if (actualWidget instanceof final CosmeticWidget cosmeticWidget2) {
                cosmeticWidget.accept(cosmeticWidget2);
            }
            else {
                this.forCosmeticWidgetRecursive(actualWidget, cosmeticWidget);
            }
        }
    }
}
