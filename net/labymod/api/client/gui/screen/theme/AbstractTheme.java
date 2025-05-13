// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.font.text.TextRenderer;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import java.util.Map;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.LabyAPI;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.logging.Logging;

public abstract class AbstractTheme implements Theme
{
    private static final Logging LOGGER;
    private static final AtomicInteger THEME_ID_COUNTER;
    protected final int internalId;
    protected final String id;
    protected final Class<? extends ThemeConfig> configClass;
    protected final LabyAPI labyAPI;
    protected final ConfigLoader configLoader;
    protected final Map<String, ThemeRenderer<?>> widgetRenderers;
    protected final Map<MinecraftWidgetType, String> typeBindings;
    protected final List<ThemeEventListener> eventListeners;
    protected BackgroundRenderer backgroundRenderer;
    protected HoverBackgroundEffect hoverBackgroundRenderer;
    protected final ThemeTextRendererProvider textRendererProvider;
    protected final ResourceLocationFactory resourceLocationFactory;
    protected final Map<BasicThemeFile, ThemeFile> resourceCache;
    protected final Metadata metadata;
    private final ThemeConfigHandler configHandler;
    private ThemeConfig themeConfig;
    private boolean resourcesLoaded;
    protected String displayName;
    
    public AbstractTheme(final String id) {
        this(id, ThemeConfig.class);
    }
    
    public AbstractTheme(final String id, final Class<? extends ThemeConfig> configClass) {
        this.metadata = Metadata.create();
        this.internalId = AbstractTheme.THEME_ID_COUNTER.getAndIncrement();
        this.id = id;
        this.configClass = configClass;
        this.labyAPI = Laby.labyAPI();
        this.configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
        this.widgetRenderers = new HashMap<String, ThemeRenderer<?>>();
        this.typeBindings = new HashMap<MinecraftWidgetType, String>();
        this.eventListeners = new ArrayList<ThemeEventListener>();
        this.resourceLocationFactory = Laby.references().resourceLocationFactory();
        this.textRendererProvider = Laby.references().textRendererProvider().create(this);
        this.resourceCache = new HashMap<BasicThemeFile, ThemeFile>();
        this.configHandler = new ThemeConfigHandler(this);
    }
    
    @Override
    public void onPreLoad() {
        this.resourcesLoaded = true;
    }
    
    @Override
    public void onPostLoad() {
        this.resourceCache.clear();
        this.textRendererProvider.load();
    }
    
    @Override
    public void onUnload() {
        for (final ThemeEventListener eventListener : this.eventListeners) {
            this.labyAPI.eventBus().unregisterListener(eventListener);
        }
        this.widgetRenderers.clear();
        this.typeBindings.clear();
        this.eventListeners.clear();
        this.backgroundRenderer = null;
        this.hoverBackgroundRenderer = null;
    }
    
    @NotNull
    @Override
    public ThemeFile file(final String namespace, final String path) {
        final BasicThemeFile key = new BasicThemeFile(this, namespace, path);
        ThemeFile result = this.resourceCache.get(key);
        if (result == null) {
            this.resourceCache.put(key, result = this.createResource(key));
        }
        return result;
    }
    
    @NotNull
    @Override
    public TextRenderer textRenderer() {
        return this.textRendererProvider.textRenderer();
    }
    
    @Nullable
    @Override
    public ThemeTextRendererProvider themeTextRendererProvider() {
        return this.textRendererProvider;
    }
    
    @Nullable
    @Override
    public <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(final String rendererName) {
        return (ThemeRenderer)this.widgetRenderers.get(rendererName);
    }
    
    @Override
    public ThemeRenderer<?> getWidgetRendererByType(final MinecraftWidgetType type) {
        final String rendererName = this.typeBindings.get(type);
        if (rendererName == null) {
            return null;
        }
        return this.getWidgetRendererByName(rendererName);
    }
    
    @Override
    public boolean isResourcesLoaded() {
        return this.resourcesLoaded;
    }
    
    @Override
    public <T extends Widget> void registerWidgetRenderer(final ThemeRenderer<T> renderer) {
        renderer.setTheme(this);
        String name = renderer.getName();
        final String namespace = this.labyAPI.getNamespace(renderer);
        if (!"labymod".equals(namespace)) {
            name = namespace + "/" + name;
        }
        this.widgetRenderers.put(name, renderer);
    }
    
    public void registerBackgroundRenderer(final BackgroundRenderer renderer) {
        this.backgroundRenderer = renderer;
    }
    
    protected void registerHoverBackgroundRenderer(final HoverBackgroundEffect renderer) {
        this.hoverBackgroundRenderer = renderer;
    }
    
    @Override
    public void registerEventListener(final ThemeEventListener listener) {
        this.eventListeners.add(listener);
        this.labyAPI.eventBus().registerListener(listener);
    }
    
    @Override
    public void bindType(final MinecraftWidgetType type, final String rendererName) {
        this.typeBindings.put(type, rendererName);
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public int getInternalId() {
        return this.internalId;
    }
    
    @Override
    public Class<? extends ThemeConfig> getConfigClass() {
        return this.configClass;
    }
    
    @Nullable
    @Override
    public ThemeConfig getOrLoadThemeConfig() {
        return this.configHandler.getOrLoad();
    }
    
    @Override
    public void saveThemeConfig() {
        this.configHandler.save();
    }
    
    @Override
    public String getDisplayName() {
        return (this.displayName == null) ? this.getId() : this.displayName;
    }
    
    protected void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    @Deprecated
    @Nullable
    @Override
    public BackgroundRenderer getBackgroundRenderer() {
        return this.backgroundRenderer;
    }
    
    @Deprecated
    @Nullable
    @Override
    public HoverBackgroundEffect getHoverBackgroundRenderer() {
        return this.hoverBackgroundRenderer;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final AbstractTheme that = (AbstractTheme)object;
        return this.internalId == that.internalId && Objects.equals(this.id, that.id);
    }
    
    @Override
    public int hashCode() {
        int result = this.internalId;
        result = 31 * result + ((this.id != null) ? this.id.hashCode() : 0);
        return result;
    }
    
    static {
        LOGGER = Logging.getLogger();
        THEME_ID_COUNTER = new AtomicInteger(0);
    }
}
