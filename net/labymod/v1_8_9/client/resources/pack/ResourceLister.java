// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.pack;

import java.util.function.Consumer;

public interface ResourceLister
{
    default void listResources(final bnk pack, final String namespace, final String baseDirectory, final Consumer<jy> locationConsumer) {
        if (pack instanceof final ResourceLister resourceLister) {
            resourceLister.listResources(namespace, baseDirectory, locationConsumer);
        }
    }
    
    void listResources(final String p0, final String p1, final Consumer<jy> p2);
}
