// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.filter;

import java.util.UUID;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.Objects;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.chat.filter.DynamicChatFilter;
import java.util.List;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.filter.DynamicChatFilterService;

@Singleton
@Implements(DynamicChatFilterService.class)
public class DefaultDynamicChatFilterService implements DynamicChatFilterService
{
    private final LabyAPI labyAPI;
    private final List<DynamicChatFilter> allFilters;
    private final List<DynamicChatFilter> serverFilters;
    
    @Inject
    public DefaultDynamicChatFilterService(final LabyAPI labyAPI, final EventBus eventBus) {
        this.allFilters = new ArrayList<DynamicChatFilter>();
        this.serverFilters = new ArrayList<DynamicChatFilter>();
        this.labyAPI = labyAPI;
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void clearCustomFilters(final NetworkDisconnectEvent event) {
        for (final DynamicChatFilter filter : this.serverFilters) {
            this.allFilters.remove(filter);
        }
        final List<DynamicChatFilter> serverFilters = this.serverFilters;
        Objects.requireNonNull(serverFilters);
        this.removeTabs((Predicate<DynamicChatFilter>)serverFilters::contains);
        this.serverFilters.clear();
    }
    
    private void removeTabs(final Predicate<DynamicChatFilter> predicate) {
    }
    
    @Override
    public List<DynamicChatFilter> getAllFilters() {
        return this.allFilters;
    }
    
    @Override
    public List<DynamicChatFilter> getServerFilters() {
        return this.serverFilters;
    }
    
    @Override
    public void addFilter(final DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.removeFilter(filter.identifier());
            this.allFilters.add(filter);
        });
    }
    
    @Override
    public void removeFilter(final UUID identifier) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            final Predicate<? super Object> predicate = filter -> filter.identifier().equals(identifier);
            this.allFilters.removeIf(predicate);
            this.serverFilters.removeIf(predicate);
            this.removeTabs(f -> f.identifier().equals(identifier));
        });
    }
    
    @Override
    public void removeFilter(final DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.allFilters.remove(filter);
            this.serverFilters.remove(filter);
            this.removeTabs(f -> f.identifier().equals(filter.identifier()));
        });
    }
    
    @Override
    public void addServerFilter(final DynamicChatFilter filter) {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (!(!this.labyAPI.serverController().isConnected())) {
                this.allFilters.add(filter);
                this.serverFilters.add(filter);
            }
        });
    }
}
