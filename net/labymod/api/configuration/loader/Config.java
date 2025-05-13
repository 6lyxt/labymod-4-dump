// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader;

import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.configuration.settings.creator.SettingCreator;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.configuration.settings.Setting;
import java.util.Map;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class Config implements ConfigAccessor
{
    @Exclude
    private final ConfigProperty<Integer> configVersion;
    @Exclude
    private Map<String, String> configMeta;
    
    public Config() {
        this.configVersion = new ConfigProperty<Integer>(this.getConfigVersion());
    }
    
    public void reset() {
        for (final Setting setting : this.toSettings()) {
            setting.initialize();
            setting.reset();
        }
    }
    
    public ConfigProperty<Integer> usedConfigVersion() {
        return this.configVersion;
    }
    
    public int getConfigVersion() {
        return 1;
    }
    
    @Override
    public List<Setting> toSettings() {
        return this.toSettings(null);
    }
    
    @Override
    public List<Setting> toSettings(final Setting parent) {
        return this.toSettings(parent, null);
    }
    
    @Override
    public RootSettingRegistry asRegistry(final String id) {
        final String namespace = Laby.labyAPI().getNamespace(this);
        final RootSettingRegistry registry = RootSettingRegistry.custom(namespace, id);
        registry.addSettings(this.toSettings());
        registry.initialize();
        return registry;
    }
    
    @NotNull
    public List<Setting> toSettings(@Nullable final Setting parent, final SpriteTexture texture) {
        return new SettingCreator(Laby.labyAPI(), this).createSettings(parent, texture);
    }
    
    public boolean hasConfigMeta(final String key) {
        return this.configMeta != null && this.configMeta.containsKey(key);
    }
    
    public Map<String, String> configMeta() {
        if (this.configMeta == null) {
            this.configMeta = new HashMap<String, String>();
        }
        return this.configMeta;
    }
}
