// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.badge;

import java.util.Iterator;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import java.util.HashMap;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.client.entity.player.badge.PositionType;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;

@Singleton
@Implements(BadgeRegistry.class)
public class DefaultBadgeRegistry implements BadgeRegistry
{
    private final Map<PositionType, DefaultRegistry<BadgeRenderer>> positionRegistries;
    
    public DefaultBadgeRegistry() {
        this.positionRegistries = new HashMap<PositionType, DefaultRegistry<BadgeRenderer>>();
    }
    
    @Override
    public int render(final Stack stack, final PositionType type, final float x, final float y, final NetworkPlayerInfo player) {
        final DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            return type.renderer().render(stack, x, y, player, positionRenderer.getElements());
        }
        return 0;
    }
    
    @Override
    public int getWidth(final PositionType type, final NetworkPlayerInfo player, final boolean fromCache) {
        final DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            return type.renderer().getWidth(player, positionRenderer.getElements(), fromCache);
        }
        return 0;
    }
    
    @Override
    public void beginRender(final Stack stack, final PositionType type) {
        final DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            for (final KeyValue<BadgeRenderer> element : positionRenderer.getElements()) {
                element.getValue().beginRender(stack);
            }
        }
    }
    
    @Override
    public void endRender(final Stack stack, final PositionType type) {
        final DefaultRegistry<BadgeRenderer> positionRenderer = this.positionRegistries.get(type);
        if (positionRenderer != null) {
            for (final KeyValue<BadgeRenderer> element : positionRenderer.getElements()) {
                element.getValue().endRender(stack);
            }
        }
    }
    
    @Override
    public void register(final String id, final PositionType type, final BadgeRenderer tag) {
        this.getRegistry(type).register(id, tag);
    }
    
    @Override
    public void registerAfter(final String afterId, final String id, final PositionType type, final BadgeRenderer tag) {
        this.getRegistry(type).registerAfter(afterId, id, tag);
    }
    
    @Override
    public void registerBefore(final String beforeId, final String id, final PositionType type, final BadgeRenderer tag) {
        this.getRegistry(type).registerBefore(beforeId, id, tag);
    }
    
    @Override
    public void unregister(final String id) {
        for (final Map.Entry<PositionType, DefaultRegistry<BadgeRenderer>> entry : this.positionRegistries.entrySet()) {
            entry.getValue().unregister(id);
        }
    }
    
    private DefaultRegistry<BadgeRenderer> getRegistry(final PositionType type) {
        DefaultRegistry<BadgeRenderer> registry = this.positionRegistries.get(type);
        if (registry == null) {
            this.positionRegistries.put(type, registry = new DefaultRegistry<BadgeRenderer>());
        }
        return registry;
    }
}
