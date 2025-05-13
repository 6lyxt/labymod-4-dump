// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.model;

import java.util.Collections;
import net.labymod.api.labynet.models.NameHistory;
import java.util.List;
import java.util.UUID;

public class NameHistoryCache
{
    private final String name;
    private final UUID uuid;
    private final List<NameHistory> nameChanges;
    
    public NameHistoryCache(final String name, final UUID uuid, final List<NameHistory> nameChanges) {
        this.name = name;
        this.uuid = uuid;
        this.nameChanges = Collections.unmodifiableList((List<? extends NameHistory>)nameChanges);
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public List<NameHistory> getNameChanges() {
        return this.nameChanges;
    }
}
