// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.cache;

import net.labymod.api.client.gui.lss.style.function.Element;
import org.jetbrains.annotations.NotNull;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;

public class SimpleWidgetModifierCache implements WidgetModifierCache
{
    private final Object2ObjectMap<String, Forwarder> forwarderCache;
    private final Object2ObjectMap<CacheKey, PostProcessor> postProcessorCache;
    
    public SimpleWidgetModifierCache() {
        this.forwarderCache = (Object2ObjectMap<String, Forwarder>)new Object2ObjectOpenHashMap();
        this.postProcessorCache = (Object2ObjectMap<CacheKey, PostProcessor>)new Object2ObjectOpenHashMap();
    }
    
    @Override
    public void addForwarder(@NotNull final String key, final Forwarder forwarder) {
        this.forwarderCache.put((Object)key, (Object)forwarder);
    }
    
    @Override
    public Forwarder findForwarder(@NotNull final String key, final Forwarder def) {
        return (Forwarder)this.forwarderCache.getOrDefault((Object)key, (Object)def);
    }
    
    @Override
    public void addPostProcessor(final CacheKey key, final PostProcessor processor) {
        this.postProcessorCache.put((Object)key, (Object)processor);
    }
    
    @Override
    public PostProcessor findPostProcessor(@NotNull final CacheKey key, final PostProcessor def) {
        return (PostProcessor)this.postProcessorCache.getOrDefault((Object)key, (Object)def);
    }
    
    @Override
    public CacheKey createKey(final String key, final Element element, final Class<?> type) {
        return new CacheKey(key, element, type);
    }
    
    @Override
    public void clearForwarders() {
        this.forwarderCache.clear();
    }
    
    @Override
    public void clearPostProcessors() {
        this.postProcessorCache.clear();
    }
}
