// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.cache;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public class NOPWidgetModifierCache implements WidgetModifierCache
{
    @Override
    public void addForwarder(final String key, final Forwarder forwarder) {
    }
    
    @Override
    public Forwarder findForwarder(@NotNull final String key, final Forwarder def) {
        return null;
    }
    
    @Override
    public void addPostProcessor(final CacheKey key, final PostProcessor processor) {
    }
    
    @Override
    public PostProcessor findPostProcessor(@NotNull final CacheKey key, final PostProcessor def) {
        return null;
    }
    
    @Override
    public CacheKey createKey(final String key, final Element element, final Class<?> type) {
        return null;
    }
    
    @Override
    public void clearForwarders() {
    }
    
    @Override
    public void clearPostProcessors() {
    }
}
