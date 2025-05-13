// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss;

import net.labymod.core.client.gui.lss.style.reader.StyleReader;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.resources.PathResourceLocation;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import java.util.Map;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.StyleSheetLoader;

@Singleton
@Implements(StyleSheetLoader.class)
public class DefaultStyleSheetLoader implements StyleSheetLoader
{
    private final Logging logger;
    private final Map<ThemeFile, StyleSheet> cache;
    private final List<ThemeFile> modifiedStyleSheets;
    
    @Inject
    public DefaultStyleSheetLoader() {
        this.cache = new HashMap<ThemeFile, StyleSheet>();
        this.modifiedStyleSheets = new ArrayList<ThemeFile>();
        this.logger = Logging.create("StyleSheet Loader");
    }
    
    @Override
    public void invalidate() {
        this.cache.clear();
    }
    
    public void onTick() {
        for (final ThemeFile file : this.cache.keySet()) {
            final ResourceLocation resource = file.toResourceLocation();
            if (!(resource instanceof PathResourceLocation)) {
                continue;
            }
            final PathResourceLocation resourceLocation = (PathResourceLocation)resource;
            if (!resourceLocation.isModified()) {
                continue;
            }
            this.modifiedStyleSheets.add(file);
        }
        final boolean hasModifiedStyleSheets = !this.modifiedStyleSheets.isEmpty();
        for (final ThemeFile modifiedStyleSheet : this.modifiedStyleSheets) {
            final StyleSheet styleSheet = this.load(modifiedStyleSheet, true);
            if (styleSheet != null) {
                this.logger.info("Stylesheet {} has been reloaded", modifiedStyleSheet.toResourceLocation());
            }
        }
        this.modifiedStyleSheets.clear();
        if (hasModifiedStyleSheets) {
            ((DefaultThemeService)Laby.references().themeService()).reloadActivities();
        }
    }
    
    @Override
    public StyleSheet load(final ThemeFile file) {
        return this.load(file, false);
    }
    
    private StyleSheet load(final ThemeFile file, final boolean reload) {
        if (!reload) {
            final StyleSheet cached = this.cache.get(file);
            if (cached != null) {
                return cached;
            }
        }
        try (final StyleReader styleReader = new StyleReader(file)) {
            final StyleSheet styleSheet = styleReader.readStyleSheet();
            if (styleSheet != null) {
                this.cache.put(file, styleSheet);
            }
            return styleSheet;
        }
        catch (final Exception exception) {
            this.logger.error("Can't load lss file of {} theme: {} ({}: {})", file.theme().getId(), file.getName(), exception.getClass().getName(), exception.getMessage());
            return null;
        }
    }
}
