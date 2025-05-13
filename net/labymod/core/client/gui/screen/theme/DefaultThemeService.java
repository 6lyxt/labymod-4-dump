// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.AdvancedSelectionWidget;
import java.util.function.Function;
import java.util.Comparator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Locale;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.theme.ThemeConfigAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.client.gui.screen.theme.ThemeLoadEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.event.client.gui.screen.theme.ThemeUnregisterEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.theme.ThemeRegisterEvent;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import javax.inject.Inject;
import java.util.HashSet;
import net.labymod.api.client.gui.screen.theme.Theme;
import java.util.Set;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.theme.ThemeService;

@Singleton
@Implements(ThemeService.class)
public class DefaultThemeService implements ThemeService
{
    private static final float THUMBNAIL_ASPECT_RATIO = 1.7777778f;
    private final LabyAPI labyAPI;
    private final ResourcesReloadWatcher resourcesReloadWatcher;
    private final LinkMetaLoader linkMetaLoader;
    private final StyleSheetLoader styleSheetLoader;
    private final Set<Theme> themes;
    private Theme currentTheme;
    private boolean initialized;
    
    @Inject
    public DefaultThemeService(final LabyAPI labyAPI, final ResourcesReloadWatcher resourcesReloadWatcher, final LinkMetaLoader linkMetaLoader, final StyleSheetLoader styleSheetLoader) {
        this.initialized = false;
        this.labyAPI = labyAPI;
        this.resourcesReloadWatcher = resourcesReloadWatcher;
        this.linkMetaLoader = linkMetaLoader;
        this.styleSheetLoader = styleSheetLoader;
        this.themes = new HashSet<Theme>();
        labyAPI.eventBus().registerListener(this);
    }
    
    private void registerInternalThemes() {
        this.registerTheme(new VanillaTheme());
        this.registerTheme(new FancyTheme());
    }
    
    @Override
    public void initialize() {
        this.registerInternalThemes();
        this.currentTheme = this.getThemeByName("vanilla");
        this.changeTheme(this.labyAPI.config().appearance().theme().get(), true);
        this.initialized = true;
    }
    
    @Override
    public void registerTheme(final Theme theme) {
        final ThemeRegisterEvent event = new ThemeRegisterEvent(theme);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.themes.add(theme);
        theme.getOrLoadThemeConfig();
        this.updateSettingDropdown();
    }
    
    @Override
    public void unregisterTheme(final Theme theme) {
        final ThemeUnregisterEvent event = new ThemeUnregisterEvent(theme);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.themes.remove(theme);
        theme.saveThemeConfig();
        this.updateSettingDropdown();
    }
    
    private void updateSettingDropdown() {
        final AbstractSettingRegistry registry = this.labyAPI.coreSettingRegistry();
        registry.findSetting((CharSequence)"appearance.theme").ifPresent(setting -> this.updateSettingDropdown(setting.asElement()));
    }
    
    @Override
    public Theme changeTheme(final String name) {
        return this.changeTheme(name, false);
    }
    
    public Theme changeTheme(final String name, final boolean force) {
        final Theme previousTheme = this.currentTheme;
        Theme newTheme = this.getThemeByName(name);
        if (newTheme == null) {
            throw new IllegalArgumentException("Theme not found: " + name);
        }
        if (previousTheme.equals(newTheme) && !force) {
            return this.currentTheme;
        }
        newTheme = Laby.fireEvent(new ThemeChangeEvent(this.currentTheme, newTheme, Phase.PRE)).newTheme();
        this.changeThemeInternal(previousTheme, newTheme, force);
        Laby.fireEvent(new ThemeChangeEvent(previousTheme, newTheme, Phase.POST));
        return newTheme;
    }
    
    private void changeThemeInternal(final Theme previousTheme, final Theme newTheme, final boolean force) {
        previousTheme.onUnload();
        this.currentTheme = newTheme;
        this.updateSettingDropdown();
        if (!force && newTheme.isResourcesLoaded()) {
            this.labyAPI.minecraft().reloadResources();
        }
        newTheme.onPreLoad();
        Laby.fireEvent(new ThemeLoadEvent(ThemeLoadEvent.Phase.PRE, newTheme));
        this.resourcesReloadWatcher.addOrExecuteInitializeListener(() -> this.themeLoaded(newTheme));
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
        this.reloadActivities();
    }
    
    public void reloadActivities() {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final ScreenWrapper screen = window.currentScreen();
        if (screen != null) {
            this.renewScreen(window, screen);
        }
        if (this.labyAPI.ingameOverlay() != null) {
            this.labyAPI.ingameOverlay().replaceActivities(activity -> {
                final Activity newActivity = this.renewActivity(activity);
                return (newActivity instanceof IngameOverlayActivity) ? newActivity : activity;
            });
        }
        final ScreenOverlayHandler screenOverlayHandler = this.labyAPI.screenOverlayHandler();
        if (screenOverlayHandler != null) {
            for (final ScreenOverlay overlay : screenOverlayHandler.overlays()) {
                overlay.reloadOverlayStyles();
            }
        }
    }
    
    private void themeLoaded(final Theme theme) {
        theme.onPostLoad();
        Laby.fireEvent(new ThemeLoadEvent(ThemeLoadEvent.Phase.POST, theme));
        Laby.references().widgetConverterRegistry().initializeStyle();
        this.linkMetaLoader.loadStyleSheets();
    }
    
    private void renewScreen(final Window window, final ScreenWrapper screen) {
        screen.initialize(window);
        if (screen.isActivity()) {
            final Activity activity = screen.asActivity();
            final Activity newActivity = this.renewActivity(activity);
            if (newActivity != null) {
                activity.redirectScreen(newActivity);
            }
        }
    }
    
    @Deprecated
    private Activity renewActivity(final Activity activity) {
        Object previousScreen = activity.getPreviousScreen();
        if (previousScreen instanceof Activity && previousScreen != activity) {
            previousScreen = this.renewActivity((Activity)previousScreen);
        }
        activity.setPreviousScreen(previousScreen);
        activity.reloadMarkup();
        return activity;
    }
    
    @Override
    public void reload() {
        this.reload(false);
    }
    
    public void reload(final boolean debugReload) {
        final Theme currentTheme = this.currentTheme();
        this.reload(currentTheme.getId(), debugReload);
    }
    
    public void reload(final String themeName, final boolean debugReload) {
        this.styleSheetLoader.invalidate();
        for (final Theme theme : this.themes.toArray(new Theme[0])) {
            this.unregisterTheme(theme);
        }
        this.registerInternalThemes();
        this.changeTheme(themeName, debugReload);
    }
    
    @NotNull
    @Override
    public Theme currentTheme() {
        return this.currentTheme;
    }
    
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
    @Override
    public Theme getThemeByName(final String name) {
        for (final Theme theme : this.themes) {
            if (theme.getId().equalsIgnoreCase(name)) {
                return theme;
            }
        }
        return null;
    }
    
    @Nullable
    @Override
    public <T extends ThemeConfigAccessor> T getThemeConfig(final Theme theme, final Class<T> configClass) {
        if (!configClass.isAssignableFrom(theme.getConfigClass())) {
            throw new IllegalArgumentException("Invalid theme config class supplied");
        }
        final ThemeConfig themeConfig = theme.getOrLoadThemeConfig();
        if (themeConfig == null || !configClass.isAssignableFrom(themeConfig.getClass())) {
            return null;
        }
        return (T)themeConfig;
    }
    
    @Subscribe
    public void onConfigurationSave(final ConfigurationSaveEvent event) {
        this.themes.forEach(Theme::saveThemeConfig);
    }
    
    @Subscribe
    public void onSettingInitialize(final SettingInitializeEvent event) {
        final Setting setting = event.setting();
        if (setting.getPath().equals("settings.appearance.theme")) {
            this.updateSettingDropdown(setting.asElement());
        }
    }
    
    private void updateSettingDropdown(final SettingElement setting) {
        setting.getElements().clear();
        if (this.currentTheme != null) {
            final ThemeConfig themeConfig = this.currentTheme.getOrLoadThemeConfig();
            if (themeConfig != null) {
                setting.addSettings(themeConfig);
                for (final Setting value : setting.values()) {
                    if (value.isElement()) {
                        value.asElement().setCustomTranslation(String.format(Locale.ROOT, "%s.theme.%s.%s", this.currentTheme.getNamespace(), this.currentTheme.getId(), value.getTranslationId()));
                    }
                }
            }
        }
        final List<Theme> themes = new ArrayList<Theme>(this.themes);
        themes.sort(Comparator.comparing((Function<? super Theme, ? extends Comparable>)Theme::getId));
        for (final Widget w : setting.getWidgets()) {
            if (w instanceof AdvancedSelectionWidget) {
                final AdvancedSelectionWidget<String> widget = (AdvancedSelectionWidget<String>)w;
                widget.setActionListener("theme", () -> {
                    this.changeTheme(widget.getSelected());
                    this.reload();
                    return;
                });
                widget.clear();
                for (final Theme theme : themes) {
                    final Icon thumbnail = Icon.texture(theme.resource(theme.getNamespace(), "textures/thumbnail.png")).aspectRatio(1.7777778f);
                    widget.add(theme.getId(), thumbnail);
                }
                widget.setStringParser(id -> {
                    final Theme theme2 = this.getThemeByName(id);
                    return Component.text((theme2 == null) ? id : theme2.getDisplayName());
                });
            }
        }
    }
}
