// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import java.util.UUID;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface DynamicChatFilterService
{
    List<DynamicChatFilter> getAllFilters();
    
    List<DynamicChatFilter> getServerFilters();
    
    void addFilter(final DynamicChatFilter p0);
    
    void removeFilter(final UUID p0);
    
    void removeFilter(final DynamicChatFilter p0);
    
    void addServerFilter(final DynamicChatFilter p0);
}
