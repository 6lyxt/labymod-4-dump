// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.Laby;
import net.labymod.api.event.client.resources.transform.RegisterResourceTransformerEvent;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourcesReloadWatcher;

@Singleton
@Implements(ResourcesReloadWatcher.class)
public class DefaultResourcesReloadWatcher implements ResourcesReloadWatcher
{
    private final ResourceTransformerRegistry transformerRegistry;
    private final List<Runnable> tasks;
    private boolean initialized;
    
    @Inject
    public DefaultResourcesReloadWatcher(final ResourceTransformerRegistry transformerRegistry) {
        this.transformerRegistry = transformerRegistry;
        this.tasks = new ArrayList<Runnable>();
    }
    
    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
    
    @Override
    public void addInitializeListener(final Runnable task, final boolean skipCollection) {
        if (skipCollection && this.initialized) {
            task.run();
            return;
        }
        this.tasks.add(task);
        if (this.initialized) {
            task.run();
        }
    }
    
    @Subscribe(-127)
    public void executeTasks(final ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        IdeUtil.setResourcesLoaded(true);
        this.initialized = true;
        this.tasks.forEach(Runnable::run);
        this.tasks.clear();
    }
    
    @Subscribe
    public void registerResourceTransformerOnStart(final GameInitializeEvent event) {
        if (event.getLifecycle() == GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION) {
            return;
        }
        ((DefaultResourceTransformerRegistry)this.transformerRegistry).clear();
        Laby.fireEvent(new RegisterResourceTransformerEvent(this.transformerRegistry));
    }
    
    @Subscribe
    public void registerResourceTransformerOnReload(final ResourceReloadEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        ((DefaultResourceTransformerRegistry)this.transformerRegistry).clear();
        Laby.fireEvent(new RegisterResourceTransformerEvent(this.transformerRegistry));
    }
}
