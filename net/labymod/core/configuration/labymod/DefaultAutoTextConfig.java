// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import java.util.function.Predicate;
import java.util.ArrayList;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import java.util.List;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.configuration.loader.Config;

@ConfigName("autotext")
public class DefaultAutoTextConfig extends Config implements AutoTextConfigAccessor
{
    private List<AutoTextEntry> entries;
    
    public DefaultAutoTextConfig() {
        this.entries = new ArrayList<AutoTextEntry>();
    }
    
    public void addEntry(final AutoTextEntry entry) {
        this.entries.add(entry);
    }
    
    public boolean removeEntry(final AutoTextEntry entry) {
        return this.entries.remove(entry);
    }
    
    public boolean removeEntry(final Predicate<AutoTextEntry> filter) {
        return this.entries.removeIf(filter);
    }
    
    @Override
    public List<AutoTextEntry> getEntries() {
        return this.entries;
    }
    
    public static class AutoTextConfigProvider extends ConfigProvider<AutoTextConfigAccessor>
    {
        public static final AutoTextConfigProvider INSTANCE;
        
        private AutoTextConfigProvider() {
        }
        
        @Override
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultAutoTextConfig.class;
        }
        
        static {
            INSTANCE = new AutoTextConfigProvider();
        }
    }
}
