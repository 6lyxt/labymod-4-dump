// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.configuration.exception.ConfigurationLoadException;
import java.io.IOException;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.Constants;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.util.logging.Logging;

public class ThemeConfigHandler
{
    private static final Logging LOGGER;
    private final ConfigLoader configLoader;
    private final Class<? extends ThemeConfig> configClass;
    private final String id;
    private boolean loaded;
    @Nullable
    private ThemeConfig config;
    
    public ThemeConfigHandler(final Theme theme) {
        this.configClass = theme.getConfigClass();
        this.id = theme.getId();
        this.configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
    }
    
    @Nullable
    public ThemeConfig getOrLoad() {
        if (this.loaded) {
            return this.config;
        }
        if (this.config == null) {
            this.setThemeVariable();
            try {
                this.config = this.configLoader.load(this.configClass);
            }
            catch (final ConfigurationLoadException exception) {
                try {
                    ThemeConfigHandler.LOGGER.error("Failed to load theme config for theme {}", this.id, exception);
                    this.configLoader.invalidate(this.configClass);
                    return this.getOrLoad();
                }
                catch (final IOException ioException) {
                    ThemeConfigHandler.LOGGER.error("Failed to invalidate theme config for theme {}", this.id, ioException);
                    this.config = null;
                }
            }
        }
        this.loaded = true;
        return this.config;
    }
    
    public void save() {
        if (this.config == null) {
            return;
        }
        this.setThemeVariable();
        this.configLoader.save(this.config);
    }
    
    private void setThemeVariable() {
        this.configLoader.setVariable("$THEME", this.id);
    }
    
    @Override
    public String toString() {
        return "ThemeConfigHandler[id=" + this.id + ", configClass=" + this.getConfigClassName();
    }
    
    private String getConfigClassName() {
        if (this.configClass == null) {
            return "Do not have a config class";
        }
        return this.configClass.getName();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
