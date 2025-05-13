// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.cache;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;

public interface WidgetModifierCache
{
    void addForwarder(final String p0, final Forwarder p1);
    
    @Nullable
    default Forwarder findForwarder(final String key) {
        return this.findForwarder(key, null);
    }
    
    Forwarder findForwarder(@NotNull final String p0, final Forwarder p1);
    
    void addPostProcessor(final CacheKey p0, final PostProcessor p1);
    
    @Nullable
    default PostProcessor findPostProcessor(@NotNull final CacheKey key) {
        return this.findPostProcessor(key, null);
    }
    
    PostProcessor findPostProcessor(@NotNull final CacheKey p0, final PostProcessor p1);
    
    CacheKey createKey(final String p0, final Element p1, final Class<?> p2);
    
    void clearForwarders();
    
    void clearPostProcessors();
}
