// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.event;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.labyconnect.object.lootbox.LootBoxObject;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.core.labyconnect.object.lootbox.LootBoxModel;
import net.labymod.core.labyconnect.object.lootbox.LootBox;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.labyconnect.object.lootbox.content.PoolCategory;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxShopItem;
import net.labymod.core.labyconnect.object.lootbox.LootBoxInventoryItem;
import java.util.List;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import java.util.Collections;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.labyconnect.object.lootbox.LootBoxService;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/loot-box.lss")
@AutoActivity
public class LootBoxActivity extends SimpleActivity
{
    public static final String SEASON_ID = "easter";
    private final LootBoxService lootBoxService;
    private final AnimationController availableController;
    private final AnimationController openedController;
    private LootBoxContent content;
    
    private LootBoxActivity() {
        this(null);
    }
    
    private LootBoxActivity(final LootBoxContent content) {
        this.content = content;
        this.lootBoxService = LabyMod.references().lootBoxService();
        final ModelService modelService = LabyMod.references().modelService();
        this.availableController = modelService.createAnimationController();
        this.openedController = modelService.createAnimationController();
    }
    
    @Override
    public void tick() {
        super.tick();
        this.lootBoxService.tick();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final LootBoxService service = LabyMod.references().lootBoxService();
        final LabyConnectSession session = LabyMod.references().labyConnect().getSession();
        final boolean isAuthenticated = session != null && session.isAuthenticated();
        final List<LootBoxInventoryItem> inventoryItems = isAuthenticated ? service.getInventoryItems() : Collections.emptyList();
        final DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        final FlexibleContentWidget containerView = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)containerView).addId("container-view");
        final FlexibleContentWidget header = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)header).addId("title-section");
        final Widget title = ComponentWidget.i18n(i18nKey("title"));
        title.addId("title");
        header.addContent(title);
        final ComponentWidget subTitle = ComponentWidget.i18n(i18nKey("subtitle"), ((StyleableBuilder<T, Style.Builder>)Style.builder()).clickEvent(ClickEvent.openUrl("https://www.labymod.net/shop")).build(), new Object[0]);
        subTitle.addId("subtitle");
        header.addFlexibleContent(subTitle);
        containerView.addContent(header);
        if (inventoryItems.isEmpty()) {
            final DivWidget wrapper = new DivWidget();
            wrapper.addId("status-wrapper");
            final ComponentWidget status = ComponentWidget.i18n(isAuthenticated ? i18nKey("empty") : "labymod.labyconnect.notConnected", NamedTextColor.RED);
            status.addId("status");
            ((AbstractWidget<ComponentWidget>)wrapper).addChild(status);
            containerView.addFlexibleContent(wrapper);
        }
        else {
            this.initializeInventory(service, containerView, inventoryItems);
        }
        final ButtonWidget doneButton = ButtonWidget.i18n("labymod.ui.button.done");
        ((AbstractWidget<Widget>)doneButton).addId("done-button");
        doneButton.setPressable(this::displayPreviousScreen);
        ((AbstractWidget<ButtonWidget>)containerWidget).addChild(doneButton);
        ((AbstractWidget<FlexibleContentWidget>)containerWidget).addChild(containerView);
        ((AbstractWidget<DivWidget>)this.document).addChild(containerWidget);
        if (this.content != null) {
            final LootBoxShopItem price = this.content.getPriceShopItem();
            final PoolCategory category = price.category();
            PopupWidget.builder().text(((BaseComponent<Component>)Component.translatable("labymod.activity.lootBox.price.congratulations", NamedTextColor.RED).append(Component.text("\n")).append(((BaseComponent<Component>)Component.translatable("labymod.activity.lootBox.price.text", new Component[0]).arguments(((BaseComponent<Component>)Component.text(price.name())).color(NamedTextColor.WHITE))).color(NamedTextColor.GRAY))).append(this.content.hasDuration() ? Component.text("\n").append(Component.translatable("labymod.activity.lootBox.price.duration", new Component[0]).color(NamedTextColor.GRAY).argument(this.content.getDurationComponent().color(NamedTextColor.WHITE))) : Component.empty())).title(category.getNameComponent().color(category.getTextColor()).decorate(TextDecoration.BOLD)).icon(price.getIcon()).cancelComponent(Component.translatable("labymod.ui.button.close", new Component[0])).cancelCallback(() -> this.content = null).build().displayInOverlay();
        }
    }
    
    private void initializeInventory(final LootBoxService service, final FlexibleContentWidget containerView, final List<LootBoxInventoryItem> inventoryItems) {
        final VerticalListWidget<Widget> lootBoxView = new VerticalListWidget<Widget>();
        lootBoxView.addId("loot-box-view");
        final TilesGridWidget<Widget> tilesGridWidget = new TilesGridWidget<Widget>();
        for (final LootBoxInventoryItem inventoryItem : inventoryItems) {
            final LootBox lootBox = inventoryItem.getLootBox();
            if (lootBox == null || lootBox.getModel() == null) {
                tilesGridWidget.addTile(this.createMissingContainer(inventoryItem, lootBox));
            }
            else {
                final boolean isAvailable = inventoryItem.isAvailable();
                final LootBoxModel model = lootBox.getModel();
                final ModelWidget widget = new ModelWidget(model.model().copy(), lootBox.getAnimationController(isAvailable), 32.0f, 32.0f);
                widget.setImmediate(true);
                widget.translation().set(0.0f, -8.0f, 0.0f);
                widget.rotation().set(0.0f, MathHelper.toRadiansFloat(25.0f), 0.0f);
                widget.addId("mystery-gift");
                widget.addId(isAvailable ? "available" : "opened");
                if (isAvailable) {
                    widget.setPressable(() -> this.openLootBox(inventoryItem.getType()));
                }
                tilesGridWidget.addTile(widget);
            }
        }
        lootBoxView.addChild(tilesGridWidget);
        final ScrollWidget scrollbarWidget = new ScrollWidget(lootBoxView);
        scrollbarWidget.addId("scrollbar");
        containerView.addFlexibleContent(scrollbarWidget);
    }
    
    private DivWidget createMissingContainer(final LootBoxInventoryItem item, final LootBox lootBox) {
        final DivWidget missingContainer = new DivWidget();
        missingContainer.addId("missing-container");
        final IconWidget lootBoxMissing = new IconWidget(Textures.SpriteCommon.QUESTION_MARK);
        lootBoxMissing.addId("missing");
        final int type = item.getType();
        if (lootBox == null) {
            lootBoxMissing.setHoverComponent(Component.translatable("labymod.activity.lootBox.errors.notFound", NamedTextColor.RED, Component.text(type)));
        }
        else {
            lootBoxMissing.setHoverComponent(Component.translatable("labymod.activity.lootBox.errors.modelNotFound", NamedTextColor.RED, Component.text(type)));
        }
        ((AbstractWidget<IconWidget>)missingContainer).addChild(lootBoxMissing);
        return missingContainer;
    }
    
    private void openLootBox(final int typeId) {
        if (this.isAlreadyOpeningALootBox()) {
            final Notification notification = Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0])).text(Component.translatable(i18nKey("alreadyOpening"), NamedTextColor.RED)).build();
            Laby.references().notificationController().push(notification);
            return;
        }
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        window.displayScreenRaw(null);
        final LootBoxService lootBoxService = LabyMod.references().lootBoxService();
        lootBoxService.requestIncentive(typeId);
    }
    
    private boolean isAlreadyOpeningALootBox() {
        final Player clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        final List<KeyValue<WorldObject>> objects = Laby.references().worldObjectRegistry().getElements();
        for (final KeyValue<WorldObject> object : objects) {
            final LootBoxObject value = object.getValue();
            if (value instanceof LootBoxObject) {
                final LootBoxObject lootBox = value;
                final Player owner = lootBox.getOwner();
                if (owner == clientPlayer) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public static LootBoxActivity overview() {
        return new LootBoxActivity();
    }
    
    public static LootBoxActivity showPrice(final LootBoxContent content) {
        return new LootBoxActivity(content);
    }
    
    public static String i18nKey(final String key) {
        return "labymod.activity.lootBox." + key;
    }
}
