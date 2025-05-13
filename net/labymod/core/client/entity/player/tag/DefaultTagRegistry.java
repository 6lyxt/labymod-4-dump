// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.tag;

import java.util.Comparator;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.entity.player.tag.PositionType;
import java.util.Iterator;
import net.labymod.api.client.render.vertex.shard.RenderShards;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.collection.Lists;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.tag.TagRegistry;

@Singleton
@Implements(TagRegistry.class)
public class DefaultTagRegistry implements TagRegistry
{
    private final List<NameTagRegistry> positionRegistries;
    
    public DefaultTagRegistry() {
        this.positionRegistries = (List<NameTagRegistry>)Lists.newArrayList();
    }
    
    @Override
    public void render(final Stack stack, final Entity entity, final float usernameWidth) {
        this.render(stack, entity, usernameWidth, TagType.CUSTOM);
    }
    
    @Override
    public void render(final Stack stack, final Entity entity, final float usernameWidth, final TagType tagType) {
        for (final NameTagRegistry registry : this.positionRegistries) {
            final boolean legacy = PlatformEnvironment.isAncientOpenGL();
            if (legacy) {
                RenderShards.LEGACY_DEPTH_TEST.setupShared();
            }
            registry.getPositionType().renderer().render(stack, entity, registry.getElements(), usernameWidth, tagType);
            if (legacy) {
                RenderShards.LEGACY_DEPTH_TEST.finishShared();
            }
        }
    }
    
    @Override
    public void register(final String id, final PositionType type, final TagRenderer tag) {
        this.getRegistry(type).register(id, tag);
    }
    
    @Override
    public void registerAfter(final String afterId, final String id, final PositionType type, final TagRenderer tag) {
        this.getRegistry(type).registerAfter(afterId, id, tag);
    }
    
    @Override
    public void registerBefore(final String beforeId, final String id, final PositionType type, final TagRenderer tag) {
        this.getRegistry(type).registerBefore(beforeId, id, tag);
    }
    
    @Override
    public void unregister(final String id) {
        for (final NameTagRegistry registry : this.positionRegistries) {
            registry.unregister(id);
        }
    }
    
    private NameTagRegistry getRegistry(final PositionType type) {
        NameTagRegistry registry = null;
        for (final NameTagRegistry positionRegistry : this.positionRegistries) {
            if (positionRegistry.getPositionType().equals(type)) {
                registry = positionRegistry;
                break;
            }
        }
        if (registry == null) {
            this.positionRegistries.add(registry = new NameTagRegistry(type));
            this.positionRegistries.sort(Comparator.comparingInt(value -> value.getPositionType().getPriority()));
        }
        return registry;
    }
}
