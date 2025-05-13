// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.CopyOption;
import net.labymod.api.Constants;
import java.util.Iterator;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.ConfigAccessor;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Path;
import net.labymod.api.configuration.loader.ConfigLoader;

public abstract class AbstractConfigLoader implements ConfigLoader
{
    protected final Path directory;
    protected final Map<String, String> variables;
    
    public AbstractConfigLoader(final Path directory) {
        this.variables = new HashMap<String, String>();
        this.directory = directory;
    }
    
    public Path getPath(final Class<? extends ConfigAccessor> clazz) {
        final String namespace = Laby.labyAPI().getNamespace(clazz);
        return this.directory.resolve(namespace).resolve(String.format(Locale.ROOT, "%s.%s", this.replaceVariables(this.getRelativePath(clazz)), this.getFileExtension()));
    }
    
    @Override
    public void setVariable(final String key, final Object value) {
        this.variables.put(key, value.toString());
    }
    
    protected String replaceVariables(String configPath) {
        for (final Map.Entry<String, String> entry : this.variables.entrySet()) {
            configPath = configPath.replace(entry.getKey(), entry.getValue());
        }
        return configPath;
    }
    
    public static Path defaultDirectory() {
        return Constants.Files.CONFIGS;
    }
    
    @Override
    public void invalidate(final Class<? extends ConfigAccessor> type) throws IOException {
        final Path path = this.getPath(type);
        final Path invalidPath = path.getParent().resolve(path.getFileName().toString() + ".invalid");
        Files.copy(path, invalidPath, StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(path);
    }
}
