// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import java.util.Iterator;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EntityRenderTracker
{
    @Inject
    public EntityRenderTracker() {
    }
    
    @Subscribe
    public void onEntityRender(final EntityRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        event.entity().setRendered(true);
    }
    
    @Subscribe
    public void onEntityRenderPass(final EntityRenderPassEvent event) {
        if (event.getPhase() != EntityRenderPassEvent.Phase.BEFORE) {
            return;
        }
        final ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        for (final Entity entity : world.getEntities()) {
            entity.setRendered(false);
        }
    }
}
