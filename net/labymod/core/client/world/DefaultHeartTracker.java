// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world;

import java.util.HashMap;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerSwitchEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.draw.EntityHeartRenderer;
import java.util.UUID;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class DefaultHeartTracker
{
    public static final Map<UUID, EntityHeartRenderer> ENTITY_HEART_RENDERER;
    private final LabyAPI labyAPI;
    private final Object2FloatMap<UUID> entityHealth;
    
    @Inject
    public DefaultHeartTracker() {
        this.labyAPI = Laby.labyAPI();
        this.entityHealth = (Object2FloatMap<UUID>)new Object2FloatOpenHashMap();
    }
    
    @Subscribe
    public void onPostGameTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        for (final Entity entity : this.labyAPI.minecraft().clientWorld().getEntities()) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            final LivingEntity livingEntity = (LivingEntity)entity;
            final UUID uniqueId = livingEntity.getUniqueId();
            final float health = livingEntity.getHealth();
            float previousHealth;
            if (this.entityHealth.containsKey((Object)uniqueId)) {
                previousHealth = this.entityHealth.getFloat((Object)uniqueId);
            }
            else {
                previousHealth = health;
                this.entityHealth.put((Object)uniqueId, previousHealth);
            }
            if (previousHealth == health) {
                continue;
            }
            this.entityHealth.put((Object)uniqueId, health);
            final EntityHeartRenderer heartRenderer = DefaultHeartTracker.ENTITY_HEART_RENDERER.get(uniqueId);
            if (heartRenderer == null) {
                continue;
            }
            heartRenderer.updateHealth(health);
        }
    }
    
    @Subscribe
    public void onEntityDestruct(final EntityDestructEvent event) {
        ThreadSafe.executeOnRenderThread(() -> {
            final Entity entity = event.entity();
            this.entityHealth.removeFloat((Object)entity.getUniqueId());
            DefaultHeartTracker.ENTITY_HEART_RENDERER.remove(entity.getUniqueId());
        });
    }
    
    @Subscribe
    public void onSubServerSwitch(final SubServerSwitchEvent event) {
        this.invalidate();
    }
    
    @Subscribe
    public void onServerSwitch(final ServerSwitchEvent event) {
        this.invalidate();
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        this.invalidate();
    }
    
    @Subscribe
    public void onWorldLeave(final WorldLeaveEvent event) {
        this.invalidate();
    }
    
    private void invalidate() {
        this.entityHealth.clear();
        DefaultHeartTracker.ENTITY_HEART_RENDERER.clear();
    }
    
    static {
        ENTITY_HEART_RENDERER = new HashMap<UUID, EntityHeartRenderer>();
    }
}
