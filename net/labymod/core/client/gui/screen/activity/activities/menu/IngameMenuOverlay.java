// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.menu;

import net.labymod.api.client.network.server.ServerInfoCache;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import net.labymod.api.client.Minecraft;
import net.labymod.core.client.gui.screen.activity.activities.ingame.event.LootBoxActivity;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.IngameMenuInitializeEvent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LiveLabyNetServerInfoWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;

@Links({ @Link(value = "activity/multiplayer/server-list.lss", priority = -64), @Link("activity/overlay/menu/ingame-menu.lss") })
@AutoActivity
public class IngameMenuOverlay extends AbstractLayerActivity
{
    private static final LabyNetServerInfoCache<StorageServerData> SERVER_INFO_CACHE;
    
    public IngameMenuOverlay(final ScreenInstance parentScreen) {
        super(parentScreen);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget container = new DivWidget();
        container.addId("container");
        final MultiplayerConfig.ServerInfoPosition position = this.labyAPI.config().multiplayer().showCurrentServerInfo().get();
        if (position != MultiplayerConfig.ServerInfoPosition.DISABLED) {
            final ServerData serverData = this.labyAPI.serverController().getCurrentServerData();
            if (serverData != null) {
                StorageServerData storageServerData;
                if (serverData instanceof final StorageServerData storageServerData2) {
                    storageServerData = storageServerData2;
                }
                else {
                    storageServerData = StorageServerData.of(serverData.getName(), serverData.address());
                }
                IngameMenuOverlay.SERVER_INFO_CACHE.apply(storageServerData);
                final LiveLabyNetServerInfoWidget widget = new LiveLabyNetServerInfoWidget(storageServerData, IngameMenuOverlay.SERVER_INFO_CACHE);
                widget.setDefaultCallback();
                widget.setQuickJoinEnabled(false);
                widget.setFriendHeadsEnabled(false);
                IngameMenuOverlay.SERVER_INFO_CACHE.update();
                widget.addId("serverinfo-preview");
                ((AbstractWidget<LiveLabyNetServerInfoWidget>)container).addChild(widget);
                if (position == MultiplayerConfig.ServerInfoPosition.ABOVE_BUTTONS) {
                    widget.addId("above");
                }
                else {
                    widget.addId("below");
                }
            }
        }
        ((AbstractWidget<DivWidget>)this.document).addChild(container);
        final IngameMenuInitializeEvent event = Laby.fireEvent(new IngameMenuInitializeEvent());
        final HorizontalListWidget buttonContainerLeft = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonContainerLeft).addId("button-container-left", "button-container");
        final ButtonWidget reportBugButton = new ButtonWidget();
        ((AbstractWidget<Widget>)reportBugButton).addId("report-bug-button");
        reportBugButton.icon().set(Textures.SpriteCommon.BUG);
        reportBugButton.updateComponent(Component.translatable("labymod.activity.settings.reportBug", new Component[0]));
        reportBugButton.setPressable(() -> {
            final Minecraft minecraft = this.labyAPI.minecraft();
            minecraft.chatExecutor().openUrl("https://www.labymod.net/ideas#category=clientbugs");
            return;
        });
        buttonContainerLeft.addEntry(reportBugButton);
        final ButtonWidget screenshotWidget = ButtonWidget.i18n("labymod.activity.menu.button.screenshots");
        ((AbstractWidget<Widget>)screenshotWidget).addId("screenshot-button");
        screenshotWidget.icon().set(Textures.SpriteCommon.PICTURE);
        screenshotWidget.setPressable(() -> this.labyAPI.minecraft().minecraftWindow().displayScreen(ScreenshotBrowserActivity.INSTANCE));
        buttonContainerLeft.addEntry(screenshotWidget);
        this.addButtons(IngameMenuInitializeEvent.PauseMenuButtonPosition.LEFT, event, buttonContainerLeft);
        ((AbstractWidget<HorizontalListWidget>)this.document).addChild(buttonContainerLeft);
        final HorizontalListWidget buttonContainerRight = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonContainerRight).addId("button-container-right", "button-container");
        if (LabyMod.references().lootBoxService().isFeatureAvailable() && Laby.labyAPI().config().ingame().lootBoxes().get()) {
            final ButtonWidget eventButton = new ButtonWidget();
            ((AbstractWidget<Widget>)eventButton).addId("loot-box-button");
            eventButton.icon().set(Textures.SpriteCommon.LOOTBOX);
            eventButton.updateComponent(Component.translatable(LootBoxActivity.i18nKey("lootbox"), new Component[0]));
            eventButton.setPressable(() -> {
                final Minecraft minecraft2 = this.labyAPI.minecraft();
                minecraft2.minecraftWindow().displayScreen(LootBoxActivity.overview());
                return;
            });
            buttonContainerRight.addEntry(eventButton);
        }
        this.addButtons(IngameMenuInitializeEvent.PauseMenuButtonPosition.RIGHT, event, buttonContainerRight);
        ((AbstractWidget<HorizontalListWidget>)this.document).addChild(buttonContainerRight);
    }
    
    private void addButtons(final IngameMenuInitializeEvent.PauseMenuButtonPosition position, final IngameMenuInitializeEvent event, final HorizontalListWidget widget) {
        event.forEachButton(position, button -> {
            final ButtonWidget buttonWidget = ButtonWidget.component(button.text(), button.getIcon());
            final Runnable runnable = button.onClick();
            buttonWidget.setEnabled(runnable != null);
            if (runnable != null) {
                final Object obj;
                Objects.requireNonNull(obj);
                final AbstractWidget abstractWidget;
                abstractWidget.setPressable(obj::run);
            }
            widget.addEntry(buttonWidget);
        });
    }
    
    static {
        SERVER_INFO_CACHE = new LabyNetServerInfoCache<StorageServerData>(StorageServerData.of("", ""), null);
    }
}
