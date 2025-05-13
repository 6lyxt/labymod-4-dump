// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.exception;

import net.labymod.api.configuration.loader.ConfigAccessor;

public class ConfigurationLoadException extends ConfigurationException
{
    public ConfigurationLoadException(final Class<? extends ConfigAccessor> clazz, final Throwable cause) {
        super("Could not load the Configuration \"" + clazz.getSimpleName(), cause);
    }
}
