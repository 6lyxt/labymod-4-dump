// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod;

import net.labymod.core.configuration.labymod.main.DefaultLabyConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.ConfigProvider;

public class LabyConfigProvider extends ConfigProvider<LabyConfig>
{
    public static final LabyConfigProvider INSTANCE;
    
    private LabyConfigProvider() {
    }
    
    @Override
    protected Class<? extends ConfigAccessor> getType() {
        return DefaultLabyConfig.class;
    }
    
    static {
        INSTANCE = new LabyConfigProvider();
    }
}
