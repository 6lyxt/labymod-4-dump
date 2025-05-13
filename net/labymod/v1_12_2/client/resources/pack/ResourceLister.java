// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.pack;

import java.util.function.Consumer;

public interface ResourceLister
{
    default void listResources(final cer pack, final String namespace, final String baseDirectory, final Consumer<nf> locationConsumer) {
        if (pack instanceof final ResourceLister resourceLister) {
            resourceLister.listResources(namespace, baseDirectory, locationConsumer);
        }
    }
    
    void listResources(final String p0, final String p1, final Consumer<nf> p2);
}
