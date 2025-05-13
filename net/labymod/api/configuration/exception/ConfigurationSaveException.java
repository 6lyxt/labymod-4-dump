// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.exception;

import net.labymod.api.configuration.loader.ConfigAccessor;

public class ConfigurationSaveException extends ConfigurationException
{
    public ConfigurationSaveException(final ConfigAccessor configAccessor, final Throwable cause) {
        super("Could not save the Configuration \"" + configAccessor.getClass().getSimpleName(), cause);
    }
}
