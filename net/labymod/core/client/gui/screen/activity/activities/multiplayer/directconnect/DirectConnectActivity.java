// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressableProgressBarWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.network.server.ServerInfoCache;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.DirectConnectServerInfoWidget;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@AutoActivity
@Links({ @Link("activity/multiplayer/direct-connect.lss"), @Link("activity/multiplayer/server-info.lss"), @Link("activity/multiplayer/server-list.lss") })
public class DirectConnectActivity extends SimpleActivity
{
    private static final ServerAddress EMPTY_SERVER_ADDRESS;
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.directConnect.";
    private final MultiplayerActivity multiplayerActivity;
    private final StorageServerData serverData;
    private final LabyNetServerInfoCache<StorageServerData> cache;
    private final DirectConnectServerInfoWidget serverInfoWidget;
    private final Task task;
    private TextFieldWidget addressWidget;
    private DivWidget serverInfoWrapper;
    private ButtonWidget saveButton;
    private ButtonWidget joinButton;
    private long lastCharacterTyped;
    private String lastSuccessfulAddress;
    
    public DirectConnectActivity(final MultiplayerActivity multiplayerActivity) {
        this.lastCharacterTyped = 0L;
        this.multiplayerActivity = multiplayerActivity;
        this.lastSuccessfulAddress = this.labyAPI.minecraft().options().getLastKnownServer();
        this.serverData = StorageServerData.of(null, this.lastSuccessfulAddress);
        this.cache = new LabyNetServerInfoCache<StorageServerData>(this.serverData, null);
        this.serverInfoWidget = new DirectConnectServerInfoWidget(this.serverData, this.cache);
        this.cache.setCallback(cache -> this.update());
        this.cache.update();
        this.task = Task.builder(() -> {
            if (this.lastCharacterTyped != 0L && TimeUtil.getMillis() - this.lastCharacterTyped >= 500L) {
                this.lastCharacterTyped = 0L;
                if (!this.lastSuccessfulAddress.equals(this.serverData.address().toString())) {
                    this.cache.update();
                }
            }
        }).repeat(500L, TimeUnit.MILLISECONDS).build();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ComponentWidget title = ComponentWidget.i18n(this.getTranslationKey("title"));
        title.addId("title", "center-x");
        ((AbstractWidget<ComponentWidget>)this.document).addChild(title);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container", "center-x");
        final FlexibleContentWidget inputWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)inputWrapper).addId("input-wrapper", "center-x", "wrapper");
        final ComponentWidget inputTitle = ComponentWidget.i18n(this.getTranslationKey("serverAddress.name"));
        inputTitle.addId("input-title");
        inputWrapper.addContent(inputTitle);
        final FlexibleContentWidget inputFieldWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)inputFieldWrapper).addId("input-field-wrapper", "wrapper");
        final String address = (this.addressWidget != null) ? this.addressWidget.getText() : this.lastSuccessfulAddress;
        (this.addressWidget = new TextFieldWidget()).addId("input-field");
        this.addressWidget.maximalLength(128);
        this.addressWidget.setText(address);
        this.addressWidget.updateListener(this::applyAddress);
        this.addressWidget.submitHandler(text -> this.serverData.copy().connect());
        this.addressWidget.placeholder(Component.translatable(this.getTranslationKey("serverAddress.placeholder"), new Component[0]));
        inputFieldWrapper.addFlexibleContent(this.addressWidget);
        ((AbstractWidget<Widget>)(this.saveButton = ButtonWidget.icon(Textures.SpriteCommon.EXPORT))).addId("save-button");
        this.saveButton.setHoverComponent(Component.translatable(this.getTranslationKey("save.description"), new Component[0]));
        this.saveButton.setPressable(() -> {
            final ServerAddress serverAddress = this.cache.serverAddress();
            this.labyAPI.serverController().serverList().add(StorageServerData.of(serverAddress.toString(), serverAddress));
            this.saveButton.setEnabled(this.canSave());
            return;
        });
        this.saveButton.setEnabled(!this.addressWidget.getText().trim().isEmpty() && this.canSave());
        inputFieldWrapper.addContent(this.saveButton);
        inputWrapper.addContent(inputFieldWrapper);
        container.addContent(inputWrapper);
        (this.serverInfoWrapper = new DivWidget()).addId("direct-connect-server-info-wrapper");
        this.serverInfoWrapper.addChild(new ProgressableProgressBarWidget(() -> {
            if (this.addressWidget.getText().trim().isEmpty()) {
                return 0.0f;
            }
            else {
                return this.serverInfoWidget.getProgress();
            }
        }).addId("progress-bar"));
        ((AbstractWidget<DirectConnectServerInfoWidget>)this.serverInfoWrapper).addChild(this.serverInfoWidget);
        container.addContent(this.serverInfoWrapper);
        final FlexibleContentWidget buttonWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)buttonWrapper).addId("button-wrapper", "center-x", "wrapper");
        ((AbstractWidget<Widget>)(this.joinButton = ButtonWidget.i18n("labymod.activity.multiplayer.joinServer"))).addId("action-button");
        this.joinButton.setPressable(() -> this.serverData.copy().connect());
        this.joinButton.setEnabled(!this.addressWidget.getText().trim().isEmpty());
        buttonWrapper.addContent(this.joinButton);
        final ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
        ((AbstractWidget<Widget>)cancelButton).addId("action-button");
        cancelButton.setPressable(() -> this.labyAPI.minecraft().minecraftWindow().displayScreen(this.multiplayerActivity));
        buttonWrapper.addContent(cancelButton);
        container.addContent(buttonWrapper);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        final String address = (this.addressWidget == null) ? this.lastSuccessfulAddress : this.addressWidget.getText();
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        if (!address.equals(options.getLastKnownServer())) {
            options.setLastKnownServer(address);
            options.save();
        }
        if (this.task.isRunning()) {
            this.task.cancel();
            this.lastCharacterTyped = 0L;
        }
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
    }
    
    private void applyAddress(String address) {
        if (address.equals(this.serverData.address().toString())) {
            return;
        }
        this.lastCharacterTyped = TimeUtil.getMillis();
        if (!this.task.isRunning()) {
            this.task.execute();
        }
        address = address.trim();
        if (address.isEmpty()) {
            this.lastSuccessfulAddress = "";
            this.cache.setAddress("");
            this.saveButton.setEnabled(false);
            this.joinButton.setEnabled(false);
            return;
        }
        this.cache.setAddress(address);
        this.serverInfoWidget.setProgressable(false);
        this.saveButton.setEnabled(this.canSave());
        this.joinButton.setEnabled(true);
    }
    
    private boolean canSave() {
        final ServerAddress address = this.cache.serverAddress();
        return address != null && !this.labyAPI.serverController().serverList().has(address);
    }
    
    private String getTranslationKey(final String key) {
        return "labymod.activity.directConnect." + key;
    }
    
    private void update() {
        final ServerInfo serverInfo = this.cache.serverInfo();
        if (!this.serverData.address().equals(serverInfo.address())) {
            return;
        }
        if (this.serverInfoWidget.opacity().get() != 0.0f) {
            this.serverInfoWidget.updateServerInfo(serverInfo);
        }
        if (serverInfo.getStatus() != ServerInfo.Status.LOADING) {
            this.lastSuccessfulAddress = this.serverData.address().toString();
            this.serverInfoWidget.setProgressable(true);
        }
    }
    
    static {
        EMPTY_SERVER_ADDRESS = new ServerAddress("", 0, true);
    }
}
