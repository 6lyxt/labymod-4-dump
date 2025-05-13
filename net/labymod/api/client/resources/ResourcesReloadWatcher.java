// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourcesReloadWatcher
{
    boolean isInitialized();
    
    default void addOrExecuteInitializeListener(final Runnable task) {
        if (this.isInitialized()) {
            task.run();
        }
        else {
            this.addInitializeListener(task);
        }
    }
    
    default void addInitializeListener(final Runnable task) {
        this.addInitializeListener(task, false);
    }
    
    void addInitializeListener(final Runnable p0, final boolean p1);
}
