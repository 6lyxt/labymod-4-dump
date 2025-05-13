// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.pack.util;

import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;
import net.labymod.api.Laby;
import java.util.List;

public final class EventResourcePackRepositoryCaller
{
    private EventResourcePackRepositoryCaller() {
    }
    
    public static <T> void onRebuildSelected(final List<T> selected) {
        final ResourcePackRepository repository = Laby.references().resourcePackRepository();
        if (!(repository instanceof AbstractResourcePackRepository)) {
            return;
        }
        ((AbstractResourcePackRepository)repository).onReload(selected);
    }
}
