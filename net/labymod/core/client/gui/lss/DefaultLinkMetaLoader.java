// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.function.Consumer;
import java.util.Objects;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.theme.Theme;
import java.util.Collections;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.meta.LinkMeta;
import java.util.List;
import java.util.function.BiConsumer;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.client.gui.lss.meta.BulkLinkMeta;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import java.util.Map;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;

@Singleton
@Implements(LinkMetaLoader.class)
public class DefaultLinkMetaLoader implements LinkMetaLoader
{
    private final LabyAPI labyAPI;
    private final StyleSheetLoader styleSheetLoader;
    private final ResourcesReloadWatcher resourcesReloadWatcher;
    private final Map<Class<?>, LinkMetaList> linkMetas;
    
    @Inject
    public DefaultLinkMetaLoader(final LabyAPI labyAPI, final EventBus eventBus, final ResourcesReloadWatcher resourcesReloadWatcher) {
        this.linkMetas = new HashMap<Class<?>, LinkMetaList>();
        this.labyAPI = labyAPI;
        this.resourcesReloadWatcher = resourcesReloadWatcher;
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void loadMetas(final ServiceLoadEvent event) {
        final CustomServiceLoader<BulkLinkMeta> loader = event.load(BulkLinkMeta.class, CustomServiceLoader.ServiceType.CROSS_VERSION);
        for (final BulkLinkMeta meta : loader) {
            meta.getLinks().forEach(this::handleLinks);
        }
    }
    
    private void handleLinks(final Class<?> clazz, final List<LinkMeta> links) {
        final LinkMetaList linkMetaList = new LinkMetaList();
        this.linkMetas.put(clazz, linkMetaList);
        for (final LinkMeta link : links) {
            linkMetaList.getLinks().add(new LinkReference(this.labyAPI.getNamespace(link.getDefiner()), link.getPath(), link.getPriority()));
        }
        Collections.sort(linkMetaList.getLinks());
        if (this.labyAPI.isFullyInitialized()) {
            this.resourcesReloadWatcher.addOrExecuteInitializeListener(() -> this.loadStyleSheets(linkMetaList));
        }
    }
    
    private void loadStyleSheets(final LinkMetaList meta) {
        final Theme theme = this.labyAPI.themeService().currentTheme();
        for (final LinkReference link : meta.getLinks()) {
            this.styleSheetLoader.load(link.toThemeFile(theme).normalize());
        }
    }
    
    @Override
    public void loadStyleSheets() {
        for (final LinkMetaList meta : this.linkMetas.values()) {
            this.loadStyleSheets(meta);
        }
    }
    
    @Override
    public void injectMeta(final Activity activity) {
        final LinkMetaList linkMetaList = this.getMeta(activity.getClass());
        if (linkMetaList == null) {
            throw new IllegalStateException("Missing @AutoActivity annotation on activity " + activity.getClass().getName() + " or loading ActivityMeta too early");
        }
        final Class<? extends Activity> class1 = activity.getClass();
        Objects.requireNonNull(activity);
        this.loadMeta(class1, activity::addStyle);
    }
    
    @Override
    public void loadMeta(final Class<?> clazz, final Consumer<StyleSheet> consumer) {
        final LinkMetaList linkMetaList = this.getMeta(clazz);
        if (linkMetaList == null) {
            return;
        }
        for (final LinkReference link : linkMetaList.getLinks()) {
            final StyleSheet styleSheet = link.loadStyleSheet();
            if (styleSheet != null) {
                consumer.accept(styleSheet);
            }
        }
    }
    
    @Override
    public LinkMetaList getMeta(final Class<?> clazz) {
        return this.linkMetas.get(clazz);
    }
}
