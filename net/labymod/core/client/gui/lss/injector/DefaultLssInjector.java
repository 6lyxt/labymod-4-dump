// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.injector;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.screen.widget.StyledWidget;
import java.util.Collections;
import net.labymod.api.util.collection.Lists;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.Iterator;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.injector.LssInjector;

@Singleton
@Implements(LssInjector.class)
public class DefaultLssInjector implements LssInjector
{
    private final LabyAPI labyAPI;
    private final Set<Injector> injectors;
    
    @Inject
    public DefaultLssInjector(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.injectors = new HashSet<Injector>();
    }
    
    @Override
    public void registerStyleSheet(@NotNull final Object instance, @NotNull final String fileName, @NotNull final String targetFilePath) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Objects.requireNonNull(targetFilePath, "Target file path cannot be null");
        final String namespace = this.labyAPI.getNamespace(instance);
        if (namespace.equals("labymod")) {
            throw new UnsupportedOperationException("Invalid instance, please specify the main instance of your addon!");
        }
        this.addStyleSheetInjector(instance, namespace, fileName, targetFilePath);
    }
    
    @Override
    public void registerWidget(@NotNull final Object instance, @NotNull final String fileName, @NotNull final String selectorTypeName) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Objects.requireNonNull(selectorTypeName, "Selector type name cannot be null");
        final String namespace = this.labyAPI.getNamespace(instance);
        if (namespace.equals("labymod")) {
            throw new UnsupportedOperationException("Invalid instance, please specify the main instance of your addon!");
        }
        this.addWidgetInjector(instance, namespace, fileName, selectorTypeName);
    }
    
    @Override
    public boolean unregister(@NotNull final Object instance, @NotNull final String fileName) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Injector matchedInjector = null;
        for (final Injector injector : this.injectors) {
            if (injector.holder == instance && injector.fileName().equals(fileName)) {
                matchedInjector = injector;
                break;
            }
        }
        return Objects.nonNull(matchedInjector) && this.injectors.remove(matchedInjector);
    }
    
    @NotNull
    @Override
    public List<StyleSheet> getMatchedStyleSheetInjectors(@NotNull final StyleSheet styleSheet) {
        Objects.requireNonNull(styleSheet, "Style sheet cannot be null");
        List<StyleSheet> list = null;
        for (final Injector injector : this.injectors) {
            if (!injector.matches(styleSheet)) {
                continue;
            }
            if (Objects.isNull(list)) {
                list = (List<StyleSheet>)Lists.newArrayList();
            }
            list.add(injector.styleSheet());
        }
        return Objects.isNull(list) ? Collections.emptyList() : list;
    }
    
    @NotNull
    @Override
    public List<StyleSheet> getMatchedWidgetInjectors(@NotNull final StyledWidget styledWidget) {
        Objects.requireNonNull(styledWidget, "Styled widget cannot be null");
        List<StyleSheet> list = null;
        for (final Injector injector : this.injectors) {
            if (!injector.matches(styledWidget)) {
                continue;
            }
            if (list == null) {
                list = (List<StyleSheet>)Lists.newArrayList();
            }
            list.add(injector.styleSheet());
        }
        return (list == null) ? Collections.emptyList() : list;
    }
    
    public void addStyleSheetInjector(final Object holder, final String namespace, final String fileName, final String targetFilePath) {
        this.injectors.add(new Injector(this.labyAPI, holder, namespace, fileName, targetFilePath, null));
    }
    
    public void addWidgetInjector(final Object holder, final String namespace, final String fileName, final String typeName) {
        this.injectors.add(new Injector(this.labyAPI, holder, namespace, fileName, null, typeName));
    }
    
    public static class Injector
    {
        private static final StyleSheetLoader STYLE_SHEET_LOADER;
        private final ThemeService themeService;
        private final Object holder;
        private final String namespace;
        private final String fileName;
        private final String targetIdentifier;
        private final String targetFilePath;
        private ThemeFile currentThemeFile;
        private Theme currentTheme;
        private StyleSheet styleSheet;
        
        private Injector(final LabyAPI labyAPI, final Object holder, final String namespace, final String fileName, final String targetFilePath, final String typeName) {
            this.holder = holder;
            this.fileName = fileName;
            this.namespace = namespace;
            this.themeService = labyAPI.themeService();
            if (targetFilePath == null) {
                this.targetIdentifier = typeName;
                this.targetFilePath = null;
            }
            else if (targetFilePath.contains(":")) {
                final String[] targetFile = targetFilePath.split(":");
                this.targetIdentifier = targetFile[0];
                this.targetFilePath = "lss/" + targetFile[1];
            }
            else {
                this.targetIdentifier = "labymod";
                this.targetFilePath = "lss/" + targetFilePath;
            }
        }
        
        public String fileName() {
            return this.fileName;
        }
        
        public String targetIdentifier() {
            return this.targetIdentifier;
        }
        
        public String targetFilePath() {
            return this.targetFilePath;
        }
        
        public boolean isStyleSheetInjector() {
            return this.targetFilePath == null;
        }
        
        public StyleSheet styleSheet() {
            if (this.styleSheet == null || this.themeService.currentTheme() != this.currentTheme) {
                this.styleSheet = this.loadStyleSheet();
            }
            return this.styleSheet;
        }
        
        public boolean matches(final StyleSheet styleSheet) {
            if (!this.isStyleSheetInjector()) {
                return false;
            }
            final ThemeFile file = styleSheet.file();
            return file.getNamespace().equals(this.targetIdentifier) && file.getPath().equals(this.targetFilePath);
        }
        
        public boolean matches(final StyledWidget styledWidget) {
            return !this.isStyleSheetInjector() && styledWidget.getTypeName().equals(this.targetIdentifier);
        }
        
        private StyleSheet loadStyleSheet() {
            this.currentTheme = this.themeService.currentTheme();
            return this.loadStyleSheet(this.currentTheme);
        }
        
        private StyleSheet loadStyleSheet(final Theme theme) {
            ThemeFile themeFile;
            if (this.currentThemeFile == null) {
                themeFile = ThemeFile.create(this.themeService.currentTheme(), this.namespace, "lss/" + this.fileName);
            }
            else {
                themeFile = this.currentThemeFile.forTheme(theme);
            }
            this.currentThemeFile = themeFile;
            final StyleSheet styleSheet = Injector.STYLE_SHEET_LOADER.load(themeFile);
            if (styleSheet == null && theme instanceof ExtendingTheme) {
                return this.loadStyleSheet(((ExtendingTheme)theme).parentTheme());
            }
            return styleSheet;
        }
        
        static {
            STYLE_SHEET_LOADER = Laby.references().styleSheetLoader();
        }
    }
}
