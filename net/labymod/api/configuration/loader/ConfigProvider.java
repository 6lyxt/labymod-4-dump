// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader;

import net.labymod.api.Laby;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import java.io.IOException;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.util.logging.Logging;

public abstract class ConfigProvider<T extends ConfigAccessor>
{
    private static final Logging LOGGER;
    private T config;
    private ConfigLoader loader;
    
    protected abstract Class<? extends ConfigAccessor> getType();
    
    public T load(final ConfigLoader loader) {
        try {
            return this.load(loader, 0);
        }
        catch (final ConfigurationLoadException exception) {
            this.captureException(exception);
            ConfigProvider.LOGGER.error("Configuration {} could not be loaded.", this.getClassName(), exception);
            return null;
        }
    }
    
    public T safeLoad(final ConfigLoader loader) throws ConfigurationLoadException {
        return this.load(loader, 0);
    }
    
    private T load(final ConfigLoader loader, int tries) throws ConfigurationLoadException {
        if (tries > 3) {
            throw new ConfigurationLoadException(this.getType(), null);
        }
        this.loader = loader;
        T loaded;
        try {
            loaded = loader.load(this.getType());
        }
        catch (final ConfigurationLoadException exception) {
            try {
                loader.invalidate(this.getType());
                this.captureException(exception);
                ConfigProvider.LOGGER.error("Configuration {} could not be loaded", this.getClassName(), exception);
            }
            catch (final IOException ioException) {
                throw exception;
            }
            loaded = this.load(loader, ++tries);
        }
        return this.config = loaded;
    }
    
    public T loadJson() {
        return this.load(JsonConfigLoader.createDefault());
    }
    
    public boolean save() {
        try {
            this.loader.save(this.config);
            return true;
        }
        catch (final Exception exception) {
            this.captureException(exception);
            ConfigProvider.LOGGER.error("Configuration {} could not be saved.", this.getClassName(), exception);
            return false;
        }
    }
    
    public boolean safeSave() throws ConfigurationSaveException {
        this.loader.save(this.config);
        return true;
    }
    
    public Object serialize() {
        try {
            return this.loader.serialize(this.config);
        }
        catch (final Exception exception) {
            ConfigProvider.LOGGER.error("Configuration {} could not be serialized.", this.getClassName(), exception);
            return null;
        }
    }
    
    public T get() {
        if (this.config == null) {
            ConfigProvider.LOGGER.error(this.getType().getSimpleName() + " is null. Loader is " + ((this.loader == null) ? "null" : "present"), new Object[0]);
        }
        return this.config;
    }
    
    public ConfigLoader getLoader() {
        return this.loader;
    }
    
    private String getClassName() {
        final Class<? extends ConfigAccessor> type = this.getType();
        if (type == null) {
            return "<Unknown>";
        }
        return type.getName();
    }
    
    private void captureException(final Throwable throwable) {
        Laby.references().sentryService().capture(throwable);
    }
    
    static {
        LOGGER = Logging.create(ConfigProvider.class);
    }
}
