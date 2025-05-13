// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.event.client.gui.screen.VanillaWidgetReplacementEvent;
import net.labymod.api.util.TextFormat;
import java.util.Locale;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.client.gui.screen.ScreenUpdateVanillaWidgetEvent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import javax.inject.Inject;
import net.labymod.api.Laby;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class WidgetConverterRegistry
{
    private final Set<Class<? extends WidgetConverterInitializer>> initializers;
    private final Map<AbstractWidgetConverter<?, ?>, List<Class<?>>> widgetConverters;
    private final Map<Class<?>, AbstractWidgetConverter<?, ?>> classLookup;
    private final List<StyleSheet> styleSheets;
    private final List<ExclusionStrategy> exclusionStrategies;
    private final LabyAPI labyAPI;
    private final StyleSheetLoader styleSheetLoader;
    private final ThemeRendererParser themeRendererParser;
    private final Set<WidgetWatcher<?>> widgetWatchers;
    private Class<?> currentScreen;
    private boolean screenExcluded;
    
    @Inject
    public WidgetConverterRegistry() {
        this.initializers = new HashSet<Class<? extends WidgetConverterInitializer>>();
        this.widgetWatchers = new HashSet<WidgetWatcher<?>>();
        this.widgetConverters = new HashMap<AbstractWidgetConverter<?, ?>, List<Class<?>>>();
        this.classLookup = new HashMap<Class<?>, AbstractWidgetConverter<?, ?>>();
        this.styleSheets = new ArrayList<StyleSheet>();
        this.exclusionStrategies = new ArrayList<ExclusionStrategy>();
        this.labyAPI = Laby.labyAPI();
        this.labyAPI.eventBus().registerListener(this);
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        this.themeRendererParser = Laby.references().themeRendererParser();
    }
    
    public void initializeStyle() {
        this.styleSheets.clear();
        final String namespace = Laby.labyAPI().getNamespace(this);
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final ThemeFile file = ThemeFile.create(theme, namespace, "lss/minecraft-widget.lss");
        final StyleSheet styleSheet = this.styleSheetLoader.load(file);
        if (styleSheet != null) {
            this.styleSheets.add(styleSheet);
        }
        this.invalidateWatchers();
    }
    
    public void sync(final Object source, final AbstractWidget<?> destination) {
        if (this.screenExcluded) {
            return;
        }
        final Class<?> sourceClass = source.getClass();
        if (this.isExcluded(sourceClass)) {
            return;
        }
        final AbstractWidgetConverter converter = this.getConverter(sourceClass);
        if (converter == null) {
            return;
        }
        converter.update(source, destination);
        Laby.fireEvent(new ScreenUpdateVanillaWidgetEvent(destination));
    }
    
    @Nullable
    public AbstractWidget convertWidget(final Object source, final AbstractWidgetConverter converter) {
        return this.convertWidget(source, converter, null);
    }
    
    @Nullable
    public AbstractWidget convertWidget(final Object source, final AbstractWidgetConverter converter, @Nullable final Runnable widgetReplacementListener) {
        if (this.screenExcluded) {
            return null;
        }
        final Class<?> sourceClass = source.getClass();
        if (this.isExcluded(sourceClass)) {
            return null;
        }
        AbstractWidget convertedWidget = converter.convert(source);
        final String currentScreenName = this.labyAPI.screenService().getScreenNameByClass(this.currentScreen);
        if (currentScreenName != null) {
            final String screenId = String.format(Locale.ROOT, "%s-screen", currentScreenName.toLowerCase(Locale.ENGLISH).replace('_', '-'));
            convertedWidget.addId(screenId);
        }
        final List<String> widgetIds = converter.findWidgetIds(source);
        if (!widgetIds.isEmpty()) {
            for (String widgetId : widgetIds) {
                convertedWidget.addId(widgetId.toLowerCase(Locale.ROOT).replace('.', '-') + "-widget");
            }
        }
        final String[] segments = sourceClass.getTypeName().split("\\.");
        final String className = segments[segments.length - 1];
        for (final String part : className.split("\\$")) {
            try {
                Integer.parseInt(part);
            }
            catch (final NumberFormatException ex) {
                convertedWidget.addId(TextFormat.CAMEL_CASE.toDashCase(part));
            }
        }
        final VanillaWidgetReplacementEvent event = Laby.fireEvent(new VanillaWidgetReplacementEvent(convertedWidget));
        if (event.isCancelled()) {
            final AbstractWidget<?> replacementWidget = event.getReplacement().get();
            final List<CharSequence> ids = convertedWidget.getIds();
            for (final CharSequence id : ids) {
                replacementWidget.addId(id);
            }
            convertedWidget = replacementWidget;
            if (widgetReplacementListener != null) {
                widgetReplacementListener.run();
            }
            final Bounds bounds = replacementWidget.bounds();
            if (bounds.hasSize()) {
                this.updateMinecraftWidgetBounds(source, replacementWidget);
            }
        }
        this.updateThemeRenderer(convertedWidget, converter);
        this.initializeWidget(convertedWidget, converter);
        return convertedWidget;
    }
    
    private void updateMinecraftWidgetBounds(final Object source, final AbstractWidget<?> replacementWidget) {
        final MinecraftWidgetBounds minecraftBounds = MinecraftWidgetBounds.self(source);
        if (minecraftBounds == null) {
            return;
        }
        final ReasonableMutableRectangle outerBounds = replacementWidget.bounds().rectangle(BoundsType.OUTER);
        minecraftBounds.setBoundsX(MathHelper.ceil(outerBounds.getX()));
        minecraftBounds.setBoundsY(MathHelper.ceil(outerBounds.getY()));
        minecraftBounds.setBoundsWidth(MathHelper.ceil(outerBounds.getWidth()));
        minecraftBounds.setBoundsHeight(MathHelper.ceil(outerBounds.getHeight()));
    }
    
    @Nullable
    public AbstractWidgetConverter getConverter(final Class<?> cls) {
        AbstractWidgetConverter<?, ?> converter = this.classLookup.get(cls);
        if (converter != null) {
            return converter;
        }
        for (Class<?> clazz = Reflection.getNoneAnonymousClass(cls); clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (final Map.Entry<AbstractWidgetConverter<?, ?>, List<Class<?>>> entry : this.widgetConverters.entrySet()) {
                for (final Class<?> widgetClass : entry.getValue()) {
                    if (widgetClass == clazz) {
                        converter = entry.getKey();
                        this.classLookup.putIfAbsent(cls, converter);
                        return converter;
                    }
                }
            }
        }
        return null;
    }
    
    public void initializeWidget(final AbstractWidget<?> widget, final AbstractWidgetConverter<?, ?> converter) {
        widget.initialize(converter);
        widget.postInitialize();
        for (final StyleSheet styleSheet : this.styleSheets) {
            widget.applyStyleSheet(styleSheet);
        }
        final LinkMetaList meta = Laby.references().linkMetaLoader().getMeta(widget.getClass());
        if (meta != null) {
            for (final LinkReference link : meta.getLinks()) {
                widget.applyStyleSheet(link.loadStyleSheet());
            }
        }
    }
    
    public Optional<AbstractWidgetConverter> findConverter(final Class<?> cls) {
        return (Optional<AbstractWidgetConverter>)Optional.ofNullable(this.getConverter(cls));
    }
    
    public Optional<AbstractWidgetConverter> findConverter(final String name) {
        for (final AbstractWidgetConverter converter : this.widgetConverters.keySet()) {
            if (Objects.equals(name, converter.getName())) {
                return (Optional<AbstractWidgetConverter>)Optional.of(converter);
            }
        }
        return (Optional<AbstractWidgetConverter>)Optional.empty();
    }
    
    public Optional<AbstractWidgetConverter> findConverter(final Enum<?> enumType) {
        return this.findConverter(enumType.toString());
    }
    
    public void exclude(final ExclusionStrategy strategy) {
        this.exclusionStrategies.add(strategy);
    }
    
    public void exclude(final Class<?> cls) {
        this.exclude(ExclusionStrategy.screen(cls));
    }
    
    public void exclude(final Class<?>... classes) {
        for (final Class<?> cls : classes) {
            this.exclude(cls);
        }
    }
    
    public boolean isExcluded(final Class<?> cls) {
        for (final ExclusionStrategy strategy : this.exclusionStrategies) {
            if (strategy.shouldExclude(cls)) {
                return true;
            }
        }
        return false;
    }
    
    public void register(final AbstractWidgetConverter<?, ?> converter, final Class<?>... classes) {
        final List<Class<?>> classList = this.widgetConverters.computeIfAbsent(converter, list -> new ArrayList(classes.length));
        for (final Class<?> cls : classes) {
            if (!classList.contains(cls)) {
                classList.add(cls);
            }
        }
    }
    
    public void registerIfPresent(final String name, final Class<?>... classes) {
        this.findConverter(converter -> Objects.equals(name, converter.getName()), list -> Collections.addAll(list, (Class[])classes), () -> new IllegalStateException("No WidgetConverter with the name \"" + name + "\" could be found."));
    }
    
    public void registerIfPresent(final Class<? extends AbstractWidgetConverter<?, ?>> converterClass, final Class<?>... classes) {
        this.findConverter(converter -> converter.getClass() == converterClass, list -> Collections.addAll(list, (Class[])classes), () -> new IllegalStateException(converterClass.getName() + " could not be found."));
    }
    
    public <W extends AbstractWidget<?>> void registerWatcher(final WidgetWatcher watcher) {
        this.widgetWatchers.add(watcher);
    }
    
    private void updateThemeRenderer(final AbstractWidget<?> widget, final AbstractWidgetConverter<?, ?> converter) {
        final ThemeRenderer<Widget> renderer = this.themeRendererParser.parse(converter.getName());
        if (renderer != null) {
            widget.renderer().set(renderer);
        }
    }
    
    private void findConverter(final Function<AbstractWidgetConverter<?, ?>, Boolean> searchFunction, final Consumer<List<Class<?>>> listConsumer, final Supplier<? extends RuntimeException> throwableSupplier) {
        boolean found = false;
        for (final Map.Entry<AbstractWidgetConverter<?, ?>, List<Class<?>>> entry : this.widgetConverters.entrySet()) {
            final AbstractWidgetConverter<?, ?> converter = entry.getKey();
            if (searchFunction.apply(converter)) {
                listConsumer.accept(entry.getValue());
                found = true;
                break;
            }
        }
        if (found) {
            return;
        }
        throw (RuntimeException)throwableSupplier.get();
    }
    
    @Subscribe
    public void onThemeUpdate(final ThemeUpdateEvent event) {
        this.invalidateWatchers();
    }
    
    @Subscribe
    public void onVersionedScreenInit(final VersionedScreenInitEvent event) {
        final Object screen = event.getVersionedScreen();
        final Object currentScreen = this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen();
        Label_0058: {
            if (screen != currentScreen) {
                if (currentScreen instanceof final LabyScreenAccessor accessor) {
                    if (accessor.screen().mostInnerScreen() == screen) {
                        break Label_0058;
                    }
                }
                return;
            }
        }
        final Class<?> currentScreenClass = screen.getClass();
        this.screenExcluded = this.isExcluded(currentScreenClass);
        this.currentScreen = currentScreenClass;
        if (!(screen instanceof LabyScreenAccessor)) {
            this.invalidateWatchers();
        }
    }
    
    @Subscribe
    public void onServiceLoad(final ServiceLoadEvent event) {
        final CustomServiceLoader<WidgetConverterInitializer> converters = event.load(WidgetConverterInitializer.class, CustomServiceLoader.ServiceType.ADVANCED);
        for (final WidgetConverterInitializer converter : converters) {
            if (this.initializers.add(converter.getClass())) {
                converter.initialize(this);
            }
        }
    }
    
    private void invalidateWatchers() {
        for (final WidgetWatcher<?> widgetWatcher : this.widgetWatchers) {
            widgetWatcher.invalidate();
        }
        this.widgetWatchers.clear();
    }
}
