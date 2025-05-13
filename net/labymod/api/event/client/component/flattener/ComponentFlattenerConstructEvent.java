// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.component.flattener;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.event.Event;

@Deprecated(forRemoval = true, since = "4.1.3")
record ComponentFlattenerConstructEvent(@Nullable ComponentFlattener.Builder builder, String identifier) implements Event {
    @Nullable
    public ComponentFlattener.Builder builder() {
        return this.builder;
    }
}
