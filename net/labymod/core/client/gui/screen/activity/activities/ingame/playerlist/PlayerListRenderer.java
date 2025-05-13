// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.Minecraft;
import java.util.List;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.entity.player.badge.PositionType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.matrix.Stack;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.Color;
import net.labymod.core.client.world.rplace.RPlaceMapRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;

public class PlayerListRenderer
{
    private static final int MAX_PLAYERS = 80;
    private static final int ROW_HEIGHT = 8;
    private static final int PADDING = 1;
    private final PingIconRegistry pingIconRegistry;
    private final BadgeRegistry badgeRegistry;
    private final PlayerListOverlay playerList;
    private final ConfigProvider<LabyConfig> labyConfigProvider;
    private final RenderPipeline renderPipeline;
    private final Int2ObjectOpenHashMap<CachedComponent> exactPingComponents;
    private final PlayerListUser[] users;
    private final RPlaceMapRenderer rPlaceMapRenderer;
    private final int badPing;
    private final int okayPing;
    private final int greatPing;
    private int rowHeight;
    private int columnWidth;
    private int columns;
    private int rows;
    private int maxScoreWidth;
    private int maxPlayerNameWidth;
    private Color backgroundColor;
    private Color foregroundColor;
    private boolean showHeads;
    private boolean showServerBanner;
    private boolean showPing;
    private boolean exactPing;
    private boolean exactPingColored;
    private boolean showPercentage;
    private String serverBannerUrl;
    private String serverBannerHash;
    private Icon serverBanner;
    
    protected PlayerListRenderer(final PlayerListOverlay playerList) {
        this.users = new PlayerListUser[80];
        this.playerList = playerList;
        this.pingIconRegistry = Laby.references().pingIconRegistry();
        this.badgeRegistry = Laby.references().badgeRegistry();
        this.renderPipeline = Laby.references().renderPipeline();
        this.labyConfigProvider = LabyConfigProvider.INSTANCE;
        this.exactPingComponents = (Int2ObjectOpenHashMap<CachedComponent>)new Int2ObjectOpenHashMap();
        this.rPlaceMapRenderer = new RPlaceMapRenderer();
        this.initializeSettings();
        this.updateTheme();
        this.greatPing = NamedTextColor.GREEN.getValue();
        this.okayPing = NamedTextColor.RED.getValue();
        this.badPing = NamedTextColor.DARK_RED.getValue();
    }
    
    private void initializeSettings() {
        final TabListConfig tabListConfig = Laby.labyAPI().config().multiplayer().tabList();
        final PingConfig pingConfig = tabListConfig.ping();
        tabListConfig.backgroundColor().addChangeListener(value -> this.backgroundColor = value);
        tabListConfig.foregroundColor().addChangeListener(value -> this.foregroundColor = value);
        tabListConfig.labyModPercentage().addChangeListener(value -> this.showPercentage = value);
        tabListConfig.serverBanner().addChangeListener(value -> {
            this.showServerBanner = value;
            if (value) {
                this.updateServerBanner(this.serverBannerUrl, this.serverBannerHash);
            }
            return;
        });
        tabListConfig.showHeads().addChangeListener(value -> {
            this.showHeads = value;
            this.refreshColumnWidth();
            return;
        });
        pingConfig.enabled().addChangeListener(value -> {
            this.showPing = value;
            this.refreshColumnWidth();
            return;
        });
        pingConfig.exact().addChangeListener(value -> {
            this.exactPing = value;
            if (!value) {
                this.exactPingComponents.clear();
            }
            return;
        });
        pingConfig.exactColored().addChangeListener(value -> this.exactPingColored = value);
        this.backgroundColor = tabListConfig.backgroundColor().get();
        this.foregroundColor = tabListConfig.foregroundColor().get();
        this.showPercentage = tabListConfig.labyModPercentage().get();
        this.showServerBanner = tabListConfig.serverBanner().get();
        this.showHeads = tabListConfig.showHeads().get();
        this.showPing = pingConfig.enabled().get();
        this.exactPing = pingConfig.exact().get();
        this.exactPingColored = pingConfig.exactColored().get();
    }
    
    public void updateServerBanner(final String url, final String hash) {
        if (url == null || hash == null) {
            this.serverBannerUrl = null;
            this.serverBannerHash = null;
            this.serverBanner = null;
            return;
        }
        this.serverBannerUrl = url;
        this.serverBannerHash = hash;
        if (!this.showServerBanner) {
            return;
        }
        final CompletableResourceLocation completableResourceLocation = Laby.references().textureRepository().loadCacheResourceAsync("labymod", hash, url, Textures.EMPTY);
        completableResourceLocation.addCompletableListener(() -> {
            if (completableResourceLocation.hasResult()) {
                this.serverBanner = Icon.texture(completableResourceLocation.getCompleted());
            }
        });
    }
    
    public void tick() {
        if (this.exactPing) {
            for (final CachedComponent pingComponent : this.exactPingComponents.values()) {
                pingComponent.tick();
            }
        }
        if (this.playerList.isDoubleTapped() && this.rPlaceMapRenderer.isFeatureAvailable()) {
            this.rPlaceMapRenderer.update();
        }
    }
    
    public void render(final Stack stack, final LabyAPI labyAPI, final Bounds bounds, final boolean update) {
        if (this.playerList.isDoubleTapped() && this.rPlaceMapRenderer.isFeatureAvailable()) {
            this.renderRPlaceMap(stack, bounds);
            return;
        }
        if (update) {
            this.refresh();
        }
        for (final PositionType value : PositionType.VALUES) {
            this.badgeRegistry.beginRender(stack, value);
        }
        this.refreshMaxWidth();
        final int screenWidth = (int)bounds.getWidth(BoundsType.INNER);
        float backgroundWidth;
        final float columnsWidth = backgroundWidth = (float)(this.columnWidth * this.columns + (this.columns - 1) * 5);
        final RenderableComponent headerRenderableComponent = this.playerList.header.renderableComponent();
        if (headerRenderableComponent != null) {
            backgroundWidth = Math.max(backgroundWidth, headerRenderableComponent.getWidth());
        }
        final RenderableComponent footerRenderableComponent = this.playerList.footer.renderableComponent();
        if (footerRenderableComponent != null) {
            backgroundWidth = Math.max(backgroundWidth, footerRenderableComponent.getWidth());
        }
        final int x = (int)(screenWidth / 2.0f - backgroundWidth / 2.0f);
        int y = 9;
        if (this.showServerBanner && this.serverBanner != null) {
            this.serverBanner.render(stack, screenWidth / 2.0f - 100.0f, (float)y, 200.0f, 40.0f);
            y += 42;
        }
        this.renderBackground(stack, columnsWidth, screenWidth, backgroundWidth, x, y, headerRenderableComponent, footerRenderableComponent);
        this.renderUsers(labyAPI, stack, x, y);
        for (final PositionType value2 : PositionType.VALUES) {
            this.badgeRegistry.endRender(stack, value2);
        }
        if (this.showPercentage) {
            this.renderLabyModStats(stack, backgroundWidth, x, y);
        }
    }
    
    private void renderLabyModStats(final Stack stack, final float backgroundWidth, final int x, final int y) {
        final int onlineCount = this.playerList.users.size();
        int userCount = 0;
        for (final PlayerListUser playerInfo : this.playerList.users) {
            if (playerInfo.gameUser().isUsingLabyMod()) {
                ++userCount;
            }
        }
        final Component component = ((BaseComponent<Component>)Component.text(userCount, NamedTextColor.GRAY).append(Component.text("/", NamedTextColor.DARK_GRAY)).append(Component.text(onlineCount, NamedTextColor.GRAY)).append(Component.space())).append(Component.text("" + MathHelper.ceil(100.0f / onlineCount * userCount), NamedTextColor.GREEN));
        final float fontSize = FontSize.PredefinedFontSize.SMALL.fontSize().getFontSize();
        final RenderableComponent renderableComponent = RenderableComponent.of(component);
        this.renderPipeline.componentRenderer().builder().text(renderableComponent).scale(fontSize).pos(x + backgroundWidth + 2.0f - renderableComponent.getWidth() * fontSize, y - renderableComponent.getHeight() * fontSize).color(-1).render(stack);
    }
    
    private void renderRPlaceMap(final Stack stack, final Bounds bounds) {
        final float y = 1.0f;
        final float maxHeight = bounds.getBottom() - 64.0f - y;
        final float aspectRatio = this.rPlaceMapRenderer.getAspectRatio();
        final float mapHeight = Math.min(maxHeight, bounds.getWidth(BoundsType.INNER) / aspectRatio);
        final float mapWidth = mapHeight * aspectRatio;
        this.rPlaceMapRenderer.render(stack, bounds.getCenterX() - mapWidth / 2.0f, y, mapWidth, mapHeight);
    }
    
    private void renderBackground(final Stack stack, final float columnsWidth, final int screenWidth, final float backgroundWidth, final int x, int y, final RenderableComponent header, final RenderableComponent footer) {
        final BatchRectangleRenderer batch = this.renderPipeline.rectangleRenderer().beginBatch(stack);
        final int maxX = x + (int)backgroundWidth + 2;
        final int backgroundColor = this.backgroundColor.get();
        final int foregroundColor = this.foregroundColor.get();
        int headerY = 0;
        if (!this.isComponentEmpty(header)) {
            headerY = y + 1;
            final int headerHeight = 1 + (int)header.getHeight() + 1;
            batch.build((float)x, (float)y, (float)maxX, (float)(y + headerHeight), backgroundColor);
            y += headerHeight;
        }
        final int rows = Math.max(this.rows, 1);
        batch.build((float)x, (float)y, (float)maxX, (float)(y + 1 + rows * 9), backgroundColor);
        final int playerX = (int)(screenWidth / 2.0f - columnsWidth / 2.0f);
        for (int i = 0; i < this.users.length; ++i) {
            final PlayerListUser user = this.users[i];
            if (user == null) {
                break;
            }
            final int indexX = i / rows;
            final int indexY = i % rows;
            final int entryX = playerX + indexX * this.columnWidth + indexX * 5 + 1;
            final int entryY = y + 1 + indexY * 9;
            user.x = entryX;
            user.y = entryY;
            batch.build((float)entryX, (float)entryY, (float)(entryX + this.columnWidth), (float)(entryY + 8), foregroundColor);
        }
        final int footerY = y + rows * 9 + 1;
        if (!this.isComponentEmpty(footer)) {
            final int footerHeight = 1 + (int)footer.getHeight() + 1;
            batch.build((float)x, (float)footerY, (float)maxX, (float)(footerY + footerHeight), backgroundColor);
        }
        batch.upload();
        if (header == null && footer == null) {
            return;
        }
        final ComponentRenderer componentRenderer = this.renderPipeline.componentRenderer();
        if (header != null) {
            componentRenderer.builder().text(header).pos(x + backgroundWidth / 2.0f + 1.0f, (float)(headerY + 1)).centered(true).render(stack);
        }
        if (footer != null) {
            componentRenderer.builder().text(footer).pos(x + backgroundWidth / 2.0f + 1.0f, (float)(footerY + 1)).centered(true).render(stack);
        }
        if (this.rPlaceMapRenderer.isFeatureAvailable()) {
            componentRenderer.builder().text(Component.translatable("labymod.command.command.rplaceoverlay.tabHint", NamedTextColor.YELLOW)).pos(x + backgroundWidth / 2.0f + 1.0f, footerY + 1 + footer.getHeight() + 2.0f).centered(true).render(stack);
        }
    }
    
    public void refresh() {
        final int size = this.playerList.users.size();
        for (int i = 0; i < size && i < 80; ++i) {
            final PlayerListUser user = this.playerList.users.get(i);
            this.users[i] = user;
        }
        if (size < 80) {
            for (int i = size; i < 80; ++i) {
                this.users[i] = null;
            }
        }
        this.refreshColumns();
    }
    
    public void updateTheme() {
        this.rowHeight = MathHelper.ceil(this.renderPipeline.componentRenderer().height());
        this.refreshColumnWidth();
        for (final CachedComponent value : this.exactPingComponents.values()) {
            value.refresh();
        }
    }
    
    public void refreshColumns() {
        int rows;
        int playerAmount;
        int columns;
        for (playerAmount = (rows = Math.min(this.playerList.users.size(), 80)), columns = 1; rows > 20; rows = (playerAmount + columns - 1) / columns) {
            ++columns;
        }
        this.columns = columns;
        this.rows = rows;
        this.refreshColumnWidth();
    }
    
    private void renderUsers(final LabyAPI labyAPI, final Stack stack, final int x, final int y) {
        final boolean isOnlineMode = this.showHeads && !labyAPI.serverController().isOfflineMode();
        final ResourceRenderer resourceRenderer = this.renderPipeline.resourceRenderer();
        final ComponentRenderer componentRenderer = this.renderPipeline.componentRenderer();
        final Scoreboard scoreboard = labyAPI.minecraft().getScoreboard();
        final ScoreboardObjective scoreObjective = (scoreboard == null) ? null : scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
        for (final PlayerListUser user : this.users) {
            if (user == null) {
                break;
            }
            int entryX = user.x;
            final int entryY = user.y;
            final int columnWidth = user.x + this.columnWidth - 1;
            final NetworkPlayerInfo networkPlayerInfo = user.playerInfo();
            if (isOnlineMode) {
                final Player player = labyAPI.minecraft().clientWorld().getPlayer(user.getUniqueId()).orElse(null);
                boolean showHat;
                if (MinecraftVersions.V24w44a.orNewer()) {
                    showHat = user.playerInfo().showHat();
                }
                else {
                    showHat = (player != null && player.isShownModelPart(PlayerClothes.HAT));
                }
                resourceRenderer.head().pos((float)entryX, (float)entryY).size(8.0f).player(networkPlayerInfo.profile()).wearingHat(showHat).render(stack);
                entryX += 9;
            }
            final boolean isSpectator = networkPlayerInfo.gameMode() == GameMode.SPECTATOR;
            final int badgeLeftToNameWidth = this.badgeRegistry.render(stack, PositionType.LEFT_TO_NAME, (float)entryX, (float)entryY, networkPlayerInfo);
            if (badgeLeftToNameWidth > 0) {
                entryX += badgeLeftToNameWidth + 1;
            }
            final RenderableComponent component = user.renderableComponent();
            if (component != null) {
                componentRenderer.builder().text(component).pos((float)entryX, (float)entryY).color(isSpectator ? -1862270977 : -1).render(stack);
                this.badgeRegistry.render(stack, PositionType.RIGHT_TO_NAME, entryX + component.getWidth() + 1.0f, (float)entryY, networkPlayerInfo);
                this.badgeRegistry.render(stack, PositionType.OVERWRITE_PING, (float)(columnWidth - 10), (float)user.y, networkPlayerInfo);
                if (scoreObjective != null && !isSpectator) {
                    final int scoreRight = user.x + this.columnWidth - 8;
                    final int scoreLeft = scoreRight - this.maxScoreWidth;
                    if (scoreRight - scoreLeft > 5) {
                        this.renderScoreboardObjective(stack, scoreObjective, networkPlayerInfo, scoreLeft, entryY);
                    }
                }
            }
        }
        if (!this.showPing) {
            return;
        }
        PingIconRegistry pingRenderer;
        if (this.exactPing) {
            pingRenderer = null;
        }
        else {
            pingRenderer = this.pingIconRegistry.createMultiResourceRenderer(stack);
        }
        for (final PlayerListUser user2 : this.users) {
            if (user2 == null) {
                break;
            }
            final NetworkPlayerInfo player2 = user2.playerInfo();
            final int badgeWidth = this.badgeRegistry.getWidth(PositionType.OVERWRITE_PING, player2);
            if (badgeWidth == 0) {
                final int entryX2 = user2.x + this.columnWidth - 1;
                if (pingRenderer == null) {
                    final int ping = player2.getCurrentPing();
                    final int simplifiedPing = (ping <= 0) ? 0 : ping;
                    CachedComponent cachedPingComponent = (CachedComponent)this.exactPingComponents.get(simplifiedPing);
                    if (cachedPingComponent == null) {
                        cachedPingComponent = new CachedComponent(Component.text((ping == 0) ? "?" : ("" + ping)));
                        this.exactPingComponents.put(simplifiedPing, (Object)cachedPingComponent);
                    }
                    final RenderableComponent pingComponent = cachedPingComponent.renderableComponent();
                    int color;
                    if (this.exactPingColored) {
                        if (ping < 150) {
                            color = this.greatPing;
                        }
                        else if (ping < 300) {
                            color = this.okayPing;
                        }
                        else {
                            color = this.badPing;
                        }
                    }
                    else {
                        color = this.greatPing;
                    }
                    componentRenderer.builder().pos(entryX2 - pingComponent.getWidth() * 0.5f - 1.0f, user2.y + this.rowHeight / 4.0f).text(pingComponent).color(color).scale(0.5f).render(stack);
                }
                else {
                    pingRenderer.render(PingType.PLAYER_PING, player2.getCurrentPing(), (float)(entryX2 - 10), (float)(user2.y + 1));
                }
            }
        }
        if (pingRenderer != null) {
            pingRenderer.upload();
        }
    }
    
    private void renderScoreboardObjective(final Stack stack, final ScoreboardObjective scoreObjective, final NetworkPlayerInfo networkPlayerInfo, final int x, final int y) {
        if (scoreObjective.getRenderType() == ObjectiveRenderType.HEARTS) {
            this.renderPipeline.resourceRenderer().heartRenderer().renderHealthBar(stack, (float)x, (float)y, 9, networkPlayerInfo.getHealth(), 20);
        }
        else {
            final ScoreboardScore score = scoreObjective.getScore(networkPlayerInfo.profile().getUsername());
            final Component points = ScoreboardScore.formatValue(score, StyledFormat.PLAYER_LIST_DEFAULT);
            final RenderableComponent renderableComponent = RenderableComponent.of(points);
            this.renderPipeline.componentRenderer().builder().text(renderableComponent).pos((float)x, (float)y).color(NamedTextColor.YELLOW.value()).allowColors(false).render(stack);
        }
    }
    
    private boolean refreshMaxWidth() {
        final List<PlayerListUser> users = this.playerList.users;
        int maxPlayerNameWidth = 0;
        for (final PlayerListUser user : users) {
            int playerLength = (int)user.renderableComponent().getWidth();
            for (final PositionType value : PositionType.VALUES) {
                if (value.isExpanding()) {
                    int width = this.badgeRegistry.getWidth(value, user.playerInfo(), false);
                    if (width > 0) {
                        ++width;
                    }
                    playerLength += width;
                }
            }
            maxPlayerNameWidth = Math.max(maxPlayerNameWidth, playerLength);
        }
        final int prevMaxPlayerNameWidth = this.maxPlayerNameWidth;
        if (prevMaxPlayerNameWidth != (this.maxPlayerNameWidth = maxPlayerNameWidth)) {
            this.refreshColumnWidth();
            return true;
        }
        return false;
    }
    
    private void refreshColumnWidth() {
        final List<PlayerListUser> users = this.playerList.users;
        if (users.isEmpty()) {
            return;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final Minecraft minecraft = labyAPI.minecraft();
        final Scoreboard scoreboard = minecraft.getScoreboard();
        final ScoreboardObjective scoreObjective = (scoreboard == null) ? null : scoreboard.objective(DisplaySlot.PLAYER_LIST);
        int maxScoreValueWidth = 0;
        for (final PlayerListUser user : users) {
            if (scoreObjective != null && scoreObjective.getRenderType() != ObjectiveRenderType.HEARTS) {
                final ScoreboardScore score = scoreObjective.getScore(user.getUserName());
                final Component points = ScoreboardScore.formatValue(score, StyledFormat.PLAYER_LIST_DEFAULT);
                final int scoreValueWidth = (int)this.renderPipeline.componentRenderer().width(points);
                maxScoreValueWidth = Math.max(maxScoreValueWidth, scoreValueWidth);
            }
        }
        int maxScoreWidth;
        if (scoreObjective != null) {
            if (scoreObjective.getRenderType() == ObjectiveRenderType.HEARTS) {
                maxScoreWidth = 90;
            }
            else {
                maxScoreWidth = maxScoreValueWidth;
            }
        }
        else {
            maxScoreWidth = 0;
        }
        final boolean isOnlineMode = this.showHeads && !labyAPI.serverController().isOfflineMode();
        final int screenWidth = minecraft.minecraftWindow().getScaledWidth();
        this.maxScoreWidth = maxScoreWidth;
        this.columnWidth = Math.min(this.columns * ((isOnlineMode ? 9 : 0) + this.maxPlayerNameWidth + maxScoreWidth + (this.showPing ? 13 : 0)), screenWidth - 50) / this.columns;
    }
    
    private boolean isComponentEmpty(final RenderableComponent component) {
        return component == null || component == CachedComponent.EMPTY_COMPONENT;
    }
}
