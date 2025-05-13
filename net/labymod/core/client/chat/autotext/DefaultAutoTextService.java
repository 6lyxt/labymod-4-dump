// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.autotext;

import java.util.function.Predicate;
import net.labymod.core.configuration.labymod.DefaultAutoTextConfig;
import java.util.Collection;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.client.chat.ChatProvider;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.autotext.AutoTextService;

@Singleton
@Implements(AutoTextService.class)
public class DefaultAutoTextService implements AutoTextService
{
    private final List<AutoTextEntry> entries;
    private final ChatProvider chatProvider;
    
    @Inject
    public DefaultAutoTextService(final ChatProvider chatProvider) {
        this.chatProvider = chatProvider;
        this.entries = new ArrayList<AutoTextEntry>();
    }
    
    public void initialize() {
        this.entries.addAll(this.chatProvider.autoTextConfigAccessor().getEntries());
    }
    
    @Override
    public void addEntry(final AutoTextEntry entry) {
        this.entries.add(entry);
        ((DefaultAutoTextConfig)this.chatProvider.autoTextConfigAccessor()).addEntry(entry);
        DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
    }
    
    @Override
    public boolean removeEntry(final AutoTextEntry entry) {
        if (this.entries.remove(entry) && ((DefaultAutoTextConfig)this.chatProvider.autoTextConfigAccessor()).removeEntry(entry)) {
            DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeEntry(final Predicate<AutoTextEntry> predicate) {
        if (this.entries.removeIf(predicate) && ((DefaultAutoTextConfig)this.chatProvider.autoTextConfigAccessor()).removeEntry(predicate)) {
            DefaultAutoTextConfig.AutoTextConfigProvider.INSTANCE.save();
            return true;
        }
        return false;
    }
    
    @Override
    public List<AutoTextEntry> getEntries() {
        return this.entries;
    }
}
