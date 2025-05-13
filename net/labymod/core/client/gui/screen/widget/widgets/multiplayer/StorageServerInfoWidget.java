// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import java.io.File;
import net.labymod.api.util.concurrent.task.Task;
import java.io.IOException;
import java.util.Base64;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import net.labymod.api.Laby;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;
import java.util.UUID;
import java.util.Map;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.models.OperatingSystem;
import java.util.Locale;
import net.labymod.accountmanager.storage.loader.external.model.ExternalAccount;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.core.client.gui.navigation.elements.AccountNavigationElement;
import net.labymod.core.main.LabyMod;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.Objects;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.storage.StorageServerData;

@AutoWidget
public class StorageServerInfoWidget extends LabyNetServerInfoWidget<StorageServerData>
{
    public static final String CUSTOM_BACKGROUND_KEY = "CustomBackgroundImage";
    private final Consumer<StorageServerInfoWidget> delete;
    private final Runnable save;
    
    public StorageServerInfoWidget(@NotNull final StorageServerData serverData, @NotNull final LabyNetServerInfoCache<StorageServerData> cache, @NotNull final Consumer<StorageServerInfoWidget> delete, @NotNull final Runnable save) {
        super(serverData, cache);
        Objects.requireNonNull(delete, "Delete consumer cannot be null!");
        Objects.requireNonNull(save, "Save runnable cannot be null!");
        this.delete = delete;
        this.save = save;
    }
    
    @Override
    protected IconWidget serverIconWidget(final boolean loading) {
        return super.serverIconWidget(loading);
    }
    
    @Override
    protected void startInitialize(final Parent parent) {
        final StorageServerData serverData = this.serverData();
        if (this.serverInfo().getStatus() == ServerInfo.Status.LOADING || !serverData.hasMetadata()) {
            super.startInitialize(parent);
            return;
        }
        final String customBackgroundData = serverData.metadata().get("CustomBackgroundImage");
        if (customBackgroundData == null) {
            super.startInitialize(parent);
            return;
        }
        final Icon icon = Icon.url(customBackgroundData);
        final IconWidget backgroundWidget = new IconWidget(icon);
        backgroundWidget.addId("server-background");
        ((AbstractWidget<IconWidget>)this).addChild(backgroundWidget);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.createContextMenuLazy(contextMenu -> {
            final ContextMenuEntry deleteContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.TRASH).text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry -> {
                this.delete.accept(this);
                return true;
            }).build();
            contextMenu.addEntry(deleteContextMenuEntry);
            if (StorageServerInfoWidget.SERVER_LIST_SETTINGS.get().richServerList().get()) {
                final StorageServerData serverData = this.serverData();
                final Account[] accounts = LabyMod.getInstance().getAccountManager().getAccounts();
                if (accounts.length != 0) {
                    final ContextMenuEntry accountContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.MULTIPLAYER).text(Component.translatable("labymod.activity.multiplayer.private.account.title", new Component[0])).hoverComponent(Component.translatable("labymod.activity.multiplayer.private.account.description", new Component[0])).disabled(() -> {
                        int validAccounts = 0;
                        int i = 0;
                        for (int length = accounts.length; i < length; ++i) {
                            final Account account = accounts[i];
                            if (!(!AccountNavigationElement.isDisplayableAccount(account))) {
                                ++validAccounts;
                            }
                        }
                        return validAccounts == 0;
                    }).subMenu(() -> {
                        final ContextMenu subMenu = new ContextMenu();
                        final UUID boundAccountUuid = serverData.getBoundUniqueId();
                        int j = 0;
                        for (int length2 = accounts.length; j < length2; ++j) {
                            final Account account2 = accounts[j];
                            if (account2 instanceof ExternalAccount) {
                                if (!(!AccountNavigationElement.isDisplayableAccount(account2))) {
                                    final boolean selected = account2.getUUID().equals(boundAccountUuid);
                                    subMenu.addEntry(ContextMenuEntry.builder().text(Component.text(account2.getUsername())).icon(Icon.head(account2.getUUID())).subIcon(() -> selected ? Textures.SpriteCommon.GREEN_CHECKED : null).clickHandler(entry -> {
                                        final Map<String, String> metadata = serverData.metadata();
                                        if (selected) {
                                            metadata.remove("LabyMod-BoundAccount");
                                        }
                                        else {
                                            metadata.put("LabyMod-BoundAccount", account.getUUID().toString());
                                        }
                                        this.save.run();
                                        return true;
                                    }).build());
                                }
                            }
                        }
                        return subMenu;
                    }).build();
                    contextMenu.addEntry(accountContextMenuEntry);
                    final boolean backgroundSet = serverData.hasMetadata() && serverData.metadata().containsKey("CustomBackgroundImage");
                    final boolean lastScreenshotAvailable = serverData.getLastScreenshotFile() != null;
                    final ContextMenuEntry backgroundContextMenuEntry = ContextMenuEntry.builder().text(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.private.background.%s.title", backgroundSet ? "reset" : "set"), new Component[0])).hoverComponent(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.private.background.%s.description", backgroundSet ? "reset" : (lastScreenshotAvailable ? "set" : "unavailable")), new Component[0])).icon(Textures.SpriteCommon.PICTURE).disabled(!backgroundSet && !lastScreenshotAvailable).clickHandler(entry -> {
                        this.updateCustomBackground(serverData, backgroundSet, lastScreenshotAvailable);
                        return true;
                    }).build();
                    contextMenu.addEntry(backgroundContextMenuEntry);
                }
                if (this.userStatsUrl != null) {
                    final ContextMenuEntry userStatsEntry = ContextMenuEntry.builder().clickHandler(entry -> {
                        OperatingSystem.getPlatform().openUrl(this.userStatsUrl.replace("{userName}", this.labyAPI.getName()).replace("{uuid}", this.labyAPI.getUniqueId().toString()) + (this.userStatsUrl.contains("?") ? "&" : "?") + "utm_source=labymod&utm_medium=client&utm_campaign=serverlist");
                        return true;
                    }).icon(Textures.SpriteCommon.WORKBENCH).text(Component.translatable("labymod.activity.multiplayer.private.user_stats.title", new Component[0])).build();
                    contextMenu.addEntry(userStatsEntry);
                }
            }
        });
    }
    
    @Override
    public void reInitialize() {
        this.setContextMenu(null);
        super.reInitialize();
    }
    
    @Override
    public void connect(final String command) {
        final UUID boundUniqueId = this.serverData().getBoundUniqueId();
        if (boundUniqueId == null) {
            super.connect(command);
            return;
        }
        Account boundAccount = null;
        final LabyMod labyMod = LabyMod.getInstance();
        for (final Account account : labyMod.getAccountManager().getAccounts()) {
            if (AccountNavigationElement.isDisplayableAccount(account) && account.getUUID().equals(boundUniqueId)) {
                boundAccount = account;
                break;
            }
        }
        if (boundAccount != null) {
            final SessionAccessor sessionAccessor = labyMod.minecraft().sessionAccessor();
            final Session session = sessionAccessor.getSession();
            if (session == null || !session.getUniqueId().equals(boundAccount.getUUID()) || !Objects.equals(session.getAccessToken(), boundAccount.getAccessToken())) {
                ((DefaultAbstractSessionAccessor)sessionAccessor).updateSession(boundAccount);
            }
        }
        super.connect(command);
    }
    
    private void updateCustomBackground(final StorageServerData serverData, final boolean backgroundSet, final boolean lastScreenshotAvailable) {
        if (backgroundSet) {
            serverData.metadata().remove("CustomBackgroundImage");
            this.save.run();
            this.reInitialize();
            return;
        }
        if (!lastScreenshotAvailable) {
            return;
        }
        final File file = serverData.getLastScreenshotFile();
        if (file != null) {
            Task.builder(() -> {
                try {
                    final InputStream inputStream = IOUtil.newInputStream(file.toPath());
                    try {
                        final GameImage screenshot = Laby.references().gameImageProvider().getImage(inputStream);
                        final GameImage backgroundImage = this.makeBackgroundImage(screenshot);
                        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        try {
                            backgroundImage.write("PNG", stream);
                            final String encoded = "data:image/png;base64," + Base64.getEncoder().encodeToString(stream.toByteArray());
                            serverData.metadata().put("CustomBackgroundImage", encoded);
                            stream.close();
                        }
                        catch (final Throwable t) {
                            try {
                                stream.close();
                            }
                            catch (final Throwable exception2) {
                                t.addSuppressed(exception2);
                            }
                            throw t;
                        }
                        this.save.run();
                        this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                    catch (final Throwable t2) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            }
                            catch (final Throwable exception3) {
                                t2.addSuppressed(exception3);
                            }
                        }
                        throw t2;
                    }
                }
                catch (final IOException exception) {
                    exception.printStackTrace();
                }
            }).build().execute();
        }
    }
    
    private GameImage makeBackgroundImage(final GameImage screenshot) {
        final GameImage backgroundImage = Laby.references().gameImageProvider().createImage(Math.min(640, screenshot.getWidth()), Math.min(360, screenshot.getHeight()));
        final int sx = screenshot.getWidth() / 2 - backgroundImage.getWidth() / 2;
        final int sy = screenshot.getHeight() / 2 - backgroundImage.getHeight() / 2;
        backgroundImage.drawImage(screenshot, 0, 0, sx, sy, backgroundImage.getWidth(), backgroundImage.getHeight());
        return backgroundImage;
    }
}
