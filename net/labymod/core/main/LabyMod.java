// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main;

import net.labymod.api.service.Registry;
import net.labymod.api.client.render.gl.GlInformation;
import net.labymod.api.util.ThreadSafe;
import io.sentry.Scope;
import java.util.Optional;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.entity.player.tag.tags.LanguageFlagTag;
import net.labymod.core.main.user.group.tag.GroupIconTag;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.core.main.user.group.tag.GroupTextTag;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.SettingsActivity;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.client.session.Session;
import java.util.UUID;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.function.Function;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.BuildData;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.addon.AddonService;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.mapping.MappingService;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.event.labymod.LabyModRefreshEvent;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import java.util.function.Consumer;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.util.crashtracker.CrashTracker;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.core.localization.DefaultInternationalization;
import net.labymod.core.client.gui.lss.DefaultStyleSheetLoader;
import net.labymod.core.client.resources.texture.DefaultAbstractTextureRepository;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.core.client.options.LegacyConfigConvertListener;
import net.labymod.core.client.gui.hud.overlay.HudWidgetOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.spray.SprayWheelOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionMenuOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.emote.EmoteWheelOverlay;
import net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist.PlayerListOverlay;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.ChatOverlay;
import net.labymod.core.client.screenshot.ScreenshotNotificationListener;
import net.labymod.api.platform.launcher.MinecraftLauncher;
import net.labymod.core.client.world.rplace.RPlaceListener;
import net.labymod.core.client.component.IconComponentSerializer;
import net.labymod.core.main.listener.LoginExecutorListener;
import net.labymod.core.main.listener.MotionBlurListener;
import net.labymod.core.main.listener.InventoryMenuBlurListener;
import net.labymod.core.main.listener.EntityRenderTracker;
import net.labymod.core.client.entity.NickHiderListener;
import net.labymod.core.main.listener.TotemListener;
import net.labymod.core.client.world.DefaultHeartTracker;
import net.labymod.core.main.listener.FieldOfViewModifierListener;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.chat.DefaultChatProvider;
import net.labymod.core.main.user.shop.bridge.CustomItemRenderer;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.core.main.listener.HotkeySettingListener;
import net.labymod.core.main.listener.SettingListener;
import net.labymod.core.client.network.server.ServerScreenshotHandler;
import net.labymod.core.main.debug.DebugListener;
import net.labymod.core.main.account.AccountManagerController;
import net.labymod.api.models.OperatingSystem;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.core.main.listener.ImGuiListener;
import net.labymod.core.configuration.labymod.DefaultWidgetConfigAccessor;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.main.listener.TranslationReloadListener;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.client.gfx.pipeline.shader.DefaultShaderPipeline;
import net.labymod.core.main.user.permission.DefaultPermissionRegistry;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.main.listener.ScreenshotListener;
import net.labymod.core.configuration.settings.RevisionVisitHandler;
import net.labymod.core.configuration.settings.HotkeySwitchSettingHandler;
import net.labymod.core.main.listener.ResourcePackScannerListener;
import net.labymod.core.util.ServiceLoadEventHandler;
import net.labymod.core.configuration.ConfigurationEventListenerRegistry;
import net.labymod.core.client.resources.SplashLoader;
import org.spongepowered.asm.mixin.MixinEnvironment;
import net.labymod.api.Constants;
import net.labymod.core.platform.launcher.communication.LauncherCommunicationClient;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.api.Laby;
import javax.inject.Inject;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.core.thirdparty.DefaultSentryService;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.core.main.debug.filewatcher.DebugFileWatcher;
import net.labymod.core.client.world.WorldEnterEventHandler;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.core.flint.FlintController;
import net.labymod.api.client.zoom.ZoomController;
import net.labymod.api.configuration.labymod.WidgetConfigAccessor;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.api.util.concurrent.task.TaskExecutor;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.labynet.insight.controller.InsightWriter;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.core.main.announcement.DefaultAnnouncementService;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.core.main.user.badge.BadgeService;
import net.labymod.core.main.user.subtitle.SubtitleService;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.client.os.OperatingSystemAccessorFactory;
import net.labymod.api.client.Minecraft;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.lss.injector.LssInjector;
import net.labymod.api.client.gui.lss.style.StyleHelper;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.core.platform.PlatformHandler;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;

@Singleton
@Implements(LabyAPI.class)
public class LabyMod implements LabyAPI
{
    protected static final Logging LOGGER;
    private static LabyAPI instance;
    private PlatformHandler platformHandler;
    private GFXRenderPipeline gfxRenderPipeline;
    private RenderPipeline renderPipeline;
    private StyleHelper styleHelper;
    private LssInjector lssInjector;
    private EventBus eventBus;
    private Internationalization internationalization;
    private final AbstractSettingRegistry coreSettingRegistry;
    private Minecraft minecraft;
    private OperatingSystemAccessorFactory operatingSystemAccessorFactory;
    private OperatingSystemAccessor operatingSystemAccessor;
    private MinecraftLauncherFactory minecraftLauncherFactory;
    private AsyncAccountManager accountManager;
    private DefaultLabyConnect labyConnect;
    private ServerController serverController;
    private SubtitleService subtitleService;
    private BadgeService badgeService;
    private ThemeService themeService;
    private IngameActivityOverlay ingameOverlay;
    private DefaultAnnouncementService announcementService;
    private OverlayRegistry overlayRegistry;
    private NavigationRegistry navigationRegistry;
    private TagRegistry tagRegistry;
    private HudWidgetRegistry hudWidgetRegistry;
    private CommandService commandService;
    private OldAnimationRegistry oldAnimationRegistry;
    private InsightWriter insightWriter;
    private PermissionRegistry permissionRegistry;
    private ScreenOverlayHandler screenOverlayHandler;
    private ScreenService screenService;
    private final TaskExecutor taskExecutor;
    private ShopItemLayer shopItemLayer;
    private LabyNetController labyNetController;
    private ChatProvider chatProvider;
    private ConfigProvider<LabyConfig> labyConfig;
    private ConfigProvider<WidgetConfigAccessor> widgetConfig;
    private ZoomController zoomController;
    private FlintController flintController;
    private WidgetRegistry widgetRegistry;
    private WorldEnterEventHandler worldEnterEventHandler;
    private LibraryLoader libraryLoader;
    private boolean fullyInitialized;
    private final long startupTime;
    private final DebugFileWatcher debugFileWatcher;
    private StyleSheetLoader styleSheetLoader;
    private ThirdPartyService thirdPartyService;
    private InteractionMenuRegistry interactionMenuRegistry;
    private DefaultSentryService sentryService;
    
    @Inject
    public LabyMod() {
        this.coreSettingRegistry = RootSettingRegistry.labymod("settings").holdable(false);
        this.startupTime = TimeUtil.getNanos();
        (this.debugFileWatcher = new DebugFileWatcher()).collectDirectories();
        this.debugFileWatcher.startFileWatcherThread();
        this.taskExecutor = new TaskExecutor();
    }
    
    public static LabyMod getInstance() {
        if (LabyMod.instance == null) {
            LabyMod.instance = Laby.references().labyAPI();
        }
        return (LabyMod)LabyMod.instance;
    }
    
    public void sendLauncherPacket(final LauncherPacket packet) {
        final DefaultLauncherService launcherService = (DefaultLauncherService)Laby.references().launcherService();
        final LauncherCommunicationClient communicator = launcherService.getCommunicator();
        if (communicator == null) {
            return;
        }
        communicator.sendPacket(packet);
    }
    
    public void onPreGameStarted() {
        Laby.setInitialized();
        if (Constants.SystemProperties.isMixinAudit()) {
            MixinEnvironment.getCurrentEnvironment().audit();
        }
        LabyMod.LOGGER.info("LabyMod Version: " + this.getVersion(), new Object[0]);
        SplashLoader.INSTANCE.loadSplashDates();
        this.sentryService = (DefaultSentryService)Laby.references().sentryService();
        this.platformHandler = references().platformHandler();
        this.eventBus = Laby.references().eventBus();
        ConfigurationEventListenerRegistry.register();
        this.eventBus.registerListener(this.sentryService);
        this.internationalization = Laby.references().internationalization();
        this.eventBus.registerListener(this);
        this.platformHandler.onInitialization(this);
        this.worldEnterEventHandler = references().worldEnterEventHandler();
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        this.eventBus.registerListener(new ServiceLoadEventHandler());
        this.eventBus.registerListener(new ResourcePackScannerListener());
        this.eventBus.registerListener(Laby.references().resourcesReloadWatcher());
        Laby.references().keyMapper().initialize();
        this.eventBus.registerListener(new HotkeySwitchSettingHandler());
        this.eventBus.registerListener(new RevisionVisitHandler());
        this.eventBus.registerListener(references().sharedLanWorldService());
        this.eventBus.registerListener(new ScreenshotListener(this));
        ((DefaultAddonService)this.addonService()).enableAddons();
        this.permissionRegistry = Laby.references().permissionRegistry();
        ((DefaultPermissionRegistry)this.permissionRegistry).registerDefaultPermissions();
        this.gfxRenderPipeline = references().gfxRenderPipeline();
        this.eventBus.registerListener(this.gfxRenderPipeline);
        final ShaderPipeline shaderPipeline = this.gfxRenderPipeline.renderEnvironmentContext().shaderPipeline();
        if (shaderPipeline instanceof final DefaultShaderPipeline defaultShaderPipeline) {
            defaultShaderPipeline.fireShaderPipelineContextEvent();
        }
        this.renderPipeline = Laby.references().renderPipeline();
        this.styleHelper = Laby.references().styleHelper();
        this.lssInjector = Laby.references().lssInjector();
        references().updater().initialize();
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.PRE_GAME_STARTED));
        final ResourcePackRepository resourcePackRepository = this.renderPipeline.resourcePackRepository();
        this.eventBus.registerListener(new TranslationReloadListener());
        resourcePackRepository.registerPack("labymod");
        (this.labyConfig = LabyConfigProvider.INSTANCE).loadJson();
        (this.widgetConfig = DefaultWidgetConfigAccessor.WidgetConfigProvider.INSTANCE).loadJson();
        (this.thirdPartyService = Laby.references().thirdPartyService()).initialize();
        (this.labyNetController = Laby.references().labyNetController()).loadServerData();
        this.eventBus.registerListener(new ImGuiListener());
    }
    
    @Subscribe(-127)
    public void onAfterResourceManagerInitialization(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION) {
            return;
        }
        Laby.references().resourceTransformerRegistry();
        ((DefaultAddonService)this.addonService()).registerAddonResourcePacks();
    }
    
    @Subscribe
    public void gameAfterPostStartup(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_STARTUP) {
            return;
        }
        this.gfxRenderPipeline.gfx().initializeCapabilities();
        this.sentryService.configureScope(scope -> ThreadSafe.executeOnRenderThread(() -> {
            final GlInformation glInformation = Laby.references().glInformation();
            scope.setExtra("glRenderer", glInformation.getGlRenderer());
            scope.setExtra("glVersion", glInformation.getGlVersion());
            scope.setExtra("glVendor", glInformation.getGlVendor());
        }));
        this.interactionMenuRegistry = Laby.references().interactionMenuRegistry();
        ((DefaultGameUserService)Laby.references().gameUserService()).prepareAsynchronously();
        (this.announcementService = (DefaultAnnouncementService)references().announcementService()).prepareAsynchronously();
        this.minecraftLauncherFactory = Laby.references().minecraftLauncherFactory();
        final MinecraftLauncher launcher = this.minecraftLauncherFactory.create(LauncherVendorType.MOJANG);
        this.operatingSystemAccessor = this.operatingSystemAccessorFactory.createAccessor();
        this.accountManager = new AsyncAccountManager(IOUtil.toFile(Constants.Files.ACCOUNTS), ((AbstractOperatingSystemAccessor)this.operatingSystemAccessor).credentialsAccessor());
        if (launcher != null && !OperatingSystem.isOSX()) {
            this.accountManager.registerMinecraftStorages(launcher.getDirectory());
        }
        final AccountManagerController controller = (AccountManagerController)references().accountService();
        controller.initialize(this.accountManager);
        (this.themeService = Laby.references().themeService()).initialize();
        this.eventBus.registerListener(new DebugListener());
        this.eventBus.registerListener(new ServerScreenshotHandler());
        this.ingameOverlay = Laby.references().ingameActivityOverlay();
        this.tagRegistry = Laby.references().tagRegistry();
        this.widgetRegistry = Laby.references().widgetRegistry();
        this.eventBus.registerListener(new SettingListener());
        this.eventBus.registerListener(new HotkeySettingListener());
        this.coreSettingRegistry.addSettings(this.labyConfig.get());
        this.screenOverlayHandler = Laby.references().screenOverlayHandler();
        this.overlayRegistry = Laby.references().overlayRegistry();
        this.navigationRegistry = Laby.references().navigationRegistry();
        this.shopItemLayer = references().shopItemLayer();
        this.eventBus.registerListener(new CustomItemRenderer());
        this.serverController = Laby.references().serverController();
        this.chatProvider = Laby.references().chatProvider();
        ((DefaultChatProvider)this.chatProvider).initialize();
        (this.labyConnect = (DefaultLabyConnect)Laby.references().labyConnect()).prepareAsynchronously();
        MultiplayerActivity.INSTANCE.cacheServerList();
        this.eventBus.registerListener(new FieldOfViewModifierListener());
        this.eventBus.registerListener(new DefaultHeartTracker());
        this.hudWidgetRegistry = Laby.references().hudWidgetRegistry();
        this.commandService = Laby.references().commandService();
        this.subtitleService = references().subtitleService();
        this.badgeService = references().badgeService();
        this.zoomController = references().zoomController();
        (this.flintController = references().flintController()).setup();
        references().shopController();
        (this.oldAnimationRegistry = references().oldAnimationRegistry()).registerAnimations();
        this.insightWriter = new InsightWriter();
        this.eventBus.registerListener(this.insightWriter);
        this.platformHandler.onPostStartup();
        this.eventBus.registerListener(new TotemListener(this));
        this.eventBus.registerListener(new NickHiderListener(this));
        this.eventBus.registerListener(new EntityRenderTracker());
        this.eventBus.registerListener(new InventoryMenuBlurListener());
        this.eventBus.registerListener(new MotionBlurListener(this.labyConfig.get()));
        this.eventBus.registerListener(Laby.references().frameTimer());
        this.eventBus.registerListener(new LoginExecutorListener(this));
        this.eventBus.registerListener(references().canvasRegistry());
        this.eventBus.registerListener(new IconComponentSerializer());
        this.eventBus.registerListener(references().markerService());
        this.eventBus.registerListener(new RPlaceListener());
        this.registerDefaultNametags();
        this.fullyInitialized = true;
        this.hudWidgetRegistry.registerDefaults();
        references().sprayRenderer();
    }
    
    @Subscribe(64)
    public void postGameStarted(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            return;
        }
        this.eventBus.registerListener(new ScreenshotNotificationListener());
        this.ingameOverlay.registerActivity((IngameOverlayActivity)Laby.references().chatAccessor());
        this.ingameOverlay.registerActivity(new PlayerListOverlay());
        this.ingameOverlay.registerActivity(new EmoteWheelOverlay());
        this.ingameOverlay.registerActivity(new InteractionMenuOverlay());
        this.ingameOverlay.registerActivity(new SprayWheelOverlay());
        this.ingameOverlay.registerActivity(new HudWidgetOverlay());
        this.eventBus.registerListener(references().advancedChatController());
        this.eventBus.registerListener(references().labyModNetService());
        this.eventBus.registerListener(new LegacyConfigConvertListener());
        this.coreSettingRegistry.initialize();
    }
    
    @Subscribe(126)
    public void postEnableAddons(final GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            return;
        }
        ((DefaultAddonService)this.addonService()).postEnableAddons();
    }
    
    @Subscribe
    public void tick(final GameTickEvent event) {
        this.worldEnterEventHandler.tick();
        final boolean labyModDevelopmentEnvironment = this.labyModLoader().isLabyModDevelopmentEnvironment();
        if (labyModDevelopmentEnvironment && event.phase() == Phase.PRE) {
            ((DefaultAbstractTextureRepository)this.renderPipeline.resources().textureRepository()).onTick();
            ((DefaultStyleSheetLoader)this.styleSheetLoader).onTick();
            ((DefaultInternationalization)this.internationalization).onTick();
        }
    }
    
    @Subscribe(126)
    public void onShutdown(final GameShutdownEvent event) {
        if (event.isCrash()) {
            CrashTracker.findCause(event.getCause());
        }
        try {
            ((DefaultAbstractTextureRepository)this.renderPipeline.resources().textureRepository()).onShutdown();
        }
        catch (final Throwable throwable) {
            LabyMod.LOGGER.error("Failed to clean up texture resources.", throwable);
        }
        if (!this.saveLastWindowDimension()) {
            LabyMod.LOGGER.error("Failed to save the last window dimension", new Object[0]);
        }
        try {
            final GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
            renderPipeline.getDefaultBufferBuilder().close();
            renderPipeline.renderBuffers().close();
        }
        catch (final Throwable t) {}
        try {
            Laby.fireEvent(new ConfigurationSaveEvent());
        }
        catch (final Throwable exception) {
            LabyMod.LOGGER.error("Failed to save the configuration", exception);
        }
        try {
            this.hudWidgetRegistry.saveConfig();
        }
        catch (final Throwable throwable) {
            LabyMod.LOGGER.error("Failed to save the hud widgets", throwable);
        }
        try {
            this.debugFileWatcher.close();
        }
        catch (final Throwable t2) {}
    }
    
    private boolean saveLastWindowDimension() {
        try {
            final Minecraft minecraft = this.minecraft();
            if (minecraft == null) {
                return false;
            }
            final Window window = minecraft.minecraftWindow();
            final LabyConfig labyConfig = LabyConfigProvider.INSTANCE.get();
            if (window == null || labyConfig == null) {
                return false;
            }
            final OtherConfig other = labyConfig.other();
            if (other == null) {
                return false;
            }
            other.lastWidth().set(window.getRawWidth());
            other.lastHeight().set(window.getRawHeight());
            return true;
        }
        catch (final Throwable throwable) {
            return false;
        }
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS || event.isCancelled()) {
            return;
        }
        final Object currentVersionedScreen = this.minecraft.minecraftWindow().getCurrentVersionedScreen();
        if (currentVersionedScreen != null) {
            return;
        }
        final HotkeysConfig hotkeys = this.config().hotkeys();
        final Key key = event.key();
        boolean handled = false;
        if (key == hotkeys.friendsKey().get()) {
            this.minecraft.minecraftWindow().displayScreen(LabyConnectNavigationElement.ACTIVITY);
            handled = true;
        }
        else if (key == hotkeys.widgetEditorKey().get()) {
            final LabyModActivity labyModActivity = LabyModActivity.getFromNavigationRegistry();
            if (labyModActivity != null) {
                labyModActivity.switchTab((Class<? extends ScreenInstance>)WidgetsEditorActivity.class);
                this.minecraft.minecraftWindow().displayScreen(labyModActivity);
                handled = true;
            }
        }
        else if (key == hotkeys.toggleHitBox().get()) {
            final EntityRenderDispatcher entityRenderDispatcher = this.minecraft.entityRenderDispatcher();
            entityRenderDispatcher.setRenderDebugHitBoxes(!entityRenderDispatcher.isRenderDebugHitBoxes());
        }
        event.setCancelled(handled);
    }
    
    @Subscribe
    @Deprecated
    public void onRegisterKeybinding(final RegisterKeybindingEvent event) {
        final String name = event.getName();
        if (name.equals("of.key.zoom") || name.equals("Zoom")) {
            event.setReplacement(() -> ButtonWidget.text("LabyMod Zoom", () -> this.coreSettingRegistry().findSetting((CharSequence)"ingame").flatMap(s -> s.findSetting((CharSequence)"zoom")).ifPresent((Consumer<? super Object>)this::showSetting)));
        }
    }
    
    @Subscribe
    public void onConfigurationSave(final ConfigurationSaveEvent event) {
        if (this.labyConfig != null) {
            this.labyConfig.save();
        }
    }
    
    @Override
    public void refresh() {
        Laby.fireEvent(new LabyModRefreshEvent(Phase.PRE));
        final Notification notification = Notification.builder().type(Notification.Type.SYSTEM).title(Component.text("LabyMod")).text(Component.translatable("labymod.activity.playerCustomization.refreshLabyMod.notification", new Component[0])).build();
        this.notificationController().push(notification);
        LabyMod.LOGGER.debug("Refreshing user service...", new Object[0]);
        final DefaultGameUserService gameUserService = (DefaultGameUserService)references().gameUserService();
        gameUserService.unload();
        gameUserService.prepareAsynchronously();
        LabyMod.LOGGER.debug("Refreshing textures...", new Object[0]);
        this.renderPipeline.resources().textureRepository().invalidateRemoteTextures(url -> url.startsWith("https://dl.labymod.net/textures/") || url.startsWith("https://dl.labymod.net/cosmetics/"));
        LabyMod.LOGGER.debug("Refreshing labymod.net service...", new Object[0]);
        references().labyModNetService().reload();
        Laby.fireEvent(new LabyModRefreshEvent(Phase.POST));
    }
    
    @Override
    public AsyncAccountManager getAccountManager() {
        return this.accountManager;
    }
    
    @Override
    public LabyModLoader labyModLoader() {
        return DefaultLabyModLoader.getInstance();
    }
    
    @Override
    public EventBus eventBus() {
        return Laby.references().eventBus();
    }
    
    @Override
    public Minecraft minecraft() {
        return this.minecraft;
    }
    
    @Override
    public MappingService mappingService() {
        return MappingService.instance();
    }
    
    @Override
    public ModLoaderRegistry modLoaderRegistry() {
        return ModLoaderRegistry.instance();
    }
    
    @Override
    public AddonService addonService() {
        return DefaultAddonService.getInstance();
    }
    
    @Override
    public ChatProvider chatProvider() {
        return this.chatProvider;
    }
    
    @Override
    public ScreenService screenService() {
        return this.screenService;
    }
    
    @Override
    public IngameActivityOverlay ingameOverlay() {
        return this.ingameOverlay;
    }
    
    @Override
    public ThemeService themeService() {
        return this.themeService;
    }
    
    @Override
    public TaskExecutor taskExecutor() {
        return this.taskExecutor;
    }
    
    @Override
    public WidgetRegistry widgetRegistry() {
        return this.widgetRegistry;
    }
    
    public DefaultAnnouncementService getAnnouncementService() {
        return this.announcementService;
    }
    
    @Override
    public Internationalization internationalization() {
        return this.internationalization;
    }
    
    public SubtitleService getSubtitleService() {
        return this.subtitleService;
    }
    
    public BadgeService getBadgeService() {
        return this.badgeService;
    }
    
    @Override
    public DefaultLabyConnect labyConnect() {
        return this.labyConnect;
    }
    
    @Override
    public OverlayRegistry activityOverlayRegistry() {
        return this.overlayRegistry;
    }
    
    @Override
    public HudWidgetRegistry hudWidgetRegistry() {
        return this.hudWidgetRegistry;
    }
    
    @Override
    public NavigationRegistry navigationService() {
        return this.navigationRegistry;
    }
    
    public ShopItemLayer getShopItemLayer() {
        return this.shopItemLayer;
    }
    
    @Override
    public TagRegistry tagRegistry() {
        return this.tagRegistry;
    }
    
    @Override
    public LabyConfig config() {
        return this.labyConfig.get();
    }
    
    public ConfigProvider<LabyConfig> getLabyConfig() {
        return this.labyConfig;
    }
    
    @Override
    public WidgetConfigAccessor widgetConfig() {
        return this.widgetConfig.get();
    }
    
    @Override
    public boolean saveWidgetConfig() {
        return this.widgetConfig.save();
    }
    
    @Override
    public ServerController serverController() {
        return this.serverController;
    }
    
    @Override
    public PermissionRegistry permissionRegistry() {
        return this.permissionRegistry;
    }
    
    public FlintController getFlintController() {
        return this.flintController;
    }
    
    @Override
    public ScreenOverlayHandler screenOverlayHandler() {
        return this.screenOverlayHandler;
    }
    
    @ApiStatus.Internal
    public void setMinecraft(final Minecraft minecraft) {
        this.minecraft = minecraft;
    }
    
    @ApiStatus.Internal
    public void setOperatingSystemAccessorFactory(final OperatingSystemAccessorFactory osAccessorFactory) {
        this.operatingSystemAccessorFactory = osAccessorFactory;
    }
    
    @Override
    public OperatingSystemAccessor operatingSystemAccessor() {
        return this.operatingSystemAccessor;
    }
    
    @ApiStatus.Internal
    public void setScreenService(final ScreenService screenService) {
        this.screenService = screenService;
    }
    
    @Subscribe
    public void onPerformanceSettingInitialize(final SettingInitializeEvent event) {
        final Setting setting = event.setting();
        if (setting.getPath().equals("settings.ingame.performance")) {
            final boolean fluxInstalled = DefaultAddonService.getInstance().getAddon("flux").isPresent();
            if (!fluxInstalled) {
                final Widget[] widgets = setting.asElement().getWidgets();
                if (widgets.length > 0) {
                    final Widget widget = widgets[0];
                    if (widget instanceof final ButtonWidget buttonWidget) {
                        buttonWidget.setHoverComponent(Component.translatable("labymod.ui.settings.performance.installFlux", NamedTextColor.RED));
                    }
                }
            }
            setting.asElement().setHandler(new SettingHandler(this) {
                @Override
                public void created(final Setting setting) {
                }
                
                @Override
                public void initialized(final Setting setting) {
                }
                
                @Override
                public boolean isEnabled(final Setting setting) {
                    return fluxInstalled;
                }
            });
        }
    }
    
    public String createTitle(final String minecraftVersion, final String extra) {
        final StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("Minecraft").append(" ").append(minecraftVersion).append(" | ").append("LabyMod").append(" ").append(BuildData.getVersion());
        final LabyConfig config = this.labyConfig.get();
        if (config != null && !config.other().window().cleanWindowTitle().get()) {
            titleBuilder.append(extra);
        }
        return titleBuilder.toString();
    }
    
    @Override
    public String getNamespace(final Object target) {
        final Class<?> targetClass = (target instanceof Class) ? ((Class)target) : target.getClass();
        return this.getNamespace(targetClass);
    }
    
    @Override
    public String getNamespace(final Class<?> targetClass) {
        final ClassLoader loader = targetClass.getClassLoader();
        return this.addonService().getAddon(loader).map((Function<? super LoadedAddon, ?>)LoadedAddon::info).map((Function<? super Object, ? extends String>)InstalledAddonInfo::getNamespace).orElse("labymod");
    }
    
    @Override
    public String getDisplayName(final Class<?> targetClass) {
        final ClassLoader loader = targetClass.getClassLoader();
        return this.addonService().getAddon(loader).map((Function<? super LoadedAddon, ?>)LoadedAddon::info).map((Function<? super Object, ? extends String>)InstalledAddonInfo::getDisplayName).orElse("LabyMod");
    }
    
    @Override
    public AbstractSettingRegistry coreSettingRegistry() {
        return this.coreSettingRegistry;
    }
    
    @Override
    public UUID getUniqueId() {
        final Session session = this.minecraft.sessionAccessor().getSession();
        if (!session.hasUniqueId()) {
            return UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.getName()).getBytes());
        }
        return session.getUniqueId();
    }
    
    @Override
    public String getName() {
        return this.minecraft.sessionAccessor().getSession().getUsername();
    }
    
    @Override
    public RenderPipeline renderPipeline() {
        return this.renderPipeline;
    }
    
    @Override
    public GFXRenderPipeline gfxRenderPipeline() {
        return this.gfxRenderPipeline;
    }
    
    @Override
    public StyleHelper styleHelper() {
        return this.styleHelper;
    }
    
    @Override
    public String getVersion() {
        return BuildData.getVersion();
    }
    
    @Override
    public String getBranchName() {
        return BuildData.branchName();
    }
    
    @Override
    public CommandService commandService() {
        return this.commandService;
    }
    
    @Override
    public InteractionMenuRegistry interactionMenuRegistry() {
        return this.interactionMenuRegistry;
    }
    
    public OldAnimationRegistry getOldAnimationRegistry() {
        return this.oldAnimationRegistry;
    }
    
    @Override
    public boolean isFullyInitialized() {
        return this.fullyInitialized;
    }
    
    @Override
    public LabyNetController labyNetController() {
        return this.labyNetController;
    }
    
    @Override
    public LssInjector lssInjector() {
        return this.lssInjector;
    }
    
    @Override
    public NotificationController notificationController() {
        return Laby.references().notificationController();
    }
    
    @Override
    public ThirdPartyService thirdPartyService() {
        return this.thirdPartyService;
    }
    
    @Override
    public void showSetting(final Setting setting) {
        final NavigationRegistry service = this.navigationService();
        final ScreenNavigationElement element = ((Registry<ScreenNavigationElement>)service).getById("labymod");
        final LabyModActivity labyModActivity = (LabyModActivity)element.getScreen();
        final SettingsActivity settingsActivity = (SettingsActivity)labyModActivity.switchTab("settings");
        if (settingsActivity != null) {
            if (!setting.isInitialized()) {
                setting.initialize();
            }
            settingsActivity.setSelectedSetting(setting);
        }
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(labyModActivity);
    }
    
    @Override
    public long startupTime() {
        return this.startupTime;
    }
    
    public static DefaultReferenceStorage references() {
        return (DefaultReferenceStorage)Laby.references();
    }
    
    private void registerDefaultNametags() {
        this.tagRegistry.register("labymod_role", PositionType.ABOVE_NAME, new GroupTextTag());
        this.tagRegistry.register("labymod_icon_role", PositionType.LEFT_TO_NAME, new GroupIconTag(this));
        this.tagRegistry.register("language_flag", PositionType.RIGHT_TO_NAME, new LanguageFlagTag(this));
    }
    
    public static String getClientBrand() {
        return "labymod";
    }
    
    static {
        LOGGER = Logging.create(LabyMod.class);
    }
}
