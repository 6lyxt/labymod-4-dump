// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.user.GameUserService;
import java.util.Optional;
import net.labymod.api.util.TextFormat;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.client.os.OperatingSystemAccessor;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.client.gui.lss.injector.LssInjector;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.gui.lss.style.StyleHelper;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.render.RenderPipeline;
import java.util.UUID;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.util.concurrent.task.TaskExecutor;
import net.labymod.api.configuration.labymod.WidgetConfigAccessor;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.navigation.NavigationRegistry;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.accountmanager.AsyncAccountManager;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.addon.AddonService;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LabyAPI
{
    public static final String DEFAULT_NAMESPACE = "labymod";
    public static final String FLUX_NAMESPACE = "flux";
    
    MappingService mappingService();
    
    ModLoaderRegistry modLoaderRegistry();
    
    AddonService addonService();
    
    ChatProvider chatProvider();
    
    EventBus eventBus();
    
    Minecraft minecraft();
    
    AsyncAccountManager getAccountManager();
    
    LabyModLoader labyModLoader();
    
    ScreenService screenService();
    
    IngameActivityOverlay ingameOverlay();
    
    NavigationRegistry navigationService();
    
    ThemeService themeService();
    
    WidgetRegistry widgetRegistry();
    
    @Deprecated
    default ScreenWindowManagement screenWindowManagement() {
        return Laby.references().screenWindowManagement();
    }
    
    ScreenOverlayHandler screenOverlayHandler();
    
    WidgetConfigAccessor widgetConfig();
    
    boolean saveWidgetConfig();
    
    TaskExecutor taskExecutor();
    
    Internationalization internationalization();
    
    TagRegistry tagRegistry();
    
    ServerController serverController();
    
    PermissionRegistry permissionRegistry();
    
    LabyConfig config();
    
    LabyConnect labyConnect();
    
    OverlayRegistry activityOverlayRegistry();
    
    HudWidgetRegistry hudWidgetRegistry();
    
    String getNamespace(final Object p0);
    
    String getNamespace(final Class<?> p0);
    
    default String getDisplayName(final Object target) {
        final Class<?> targetClass = (target instanceof Class) ? ((Class)target) : target.getClass();
        return this.getDisplayName(targetClass);
    }
    
    String getDisplayName(final Class<?> p0);
    
    AbstractSettingRegistry coreSettingRegistry();
    
    UUID getUniqueId();
    
    String getName();
    
    @Deprecated
    RenderPipeline renderPipeline();
    
    GFXRenderPipeline gfxRenderPipeline();
    
    StyleHelper styleHelper();
    
    String getVersion();
    
    String getBranchName();
    
    CommandService commandService();
    
    InteractionMenuRegistry interactionMenuRegistry();
    
    boolean isFullyInitialized();
    
    LabyNetController labyNetController();
    
    LssInjector lssInjector();
    
    NotificationController notificationController();
    
    OperatingSystemAccessor operatingSystemAccessor();
    
    ThirdPartyService thirdPartyService();
    
    void showSetting(final Setting p0);
    
    long startupTime();
    
    void refresh();
    
    default String getNiceNamespace(final String namespace) {
        if (namespace.equals("labymod")) {
            return "LabyMod";
        }
        final Optional<LoadedAddon> addon = this.addonService().getAddon(namespace);
        if (addon.isPresent()) {
            return addon.get().info().getDisplayName();
        }
        return TextFormat.SNAKE_CASE.toUpperCamelCase(namespace);
    }
    
    @Deprecated
    default GameUserService gameUserService() {
        return Laby.references().gameUserService();
    }
}
