// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.pack;

import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import net.labymod.api.addon.LoadedAddon;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.pack.ResourcePack;

@Singleton
@Implements(ResourcePack.Factory.class)
public class DefaultResourcePackFactory implements ResourcePack.Factory
{
    @Inject
    public DefaultResourcePackFactory() {
    }
    
    @Override
    public ResourcePack create(final String name) {
        return new DefaultResourcePack(name);
    }
    
    @Override
    public ResourcePack create(final String name, final LoadedAddon addon) {
        return new DefaultResourcePack(name, addon);
    }
    
    @Override
    public ResourcePack createClientPack(final String name, final String... namespaces) {
        return new DefaultResourcePack(name, new HashSet<String>(Arrays.asList(namespaces)), new HashSet<String>());
    }
    
    @Override
    public ResourcePack createDataPack(final String name, final String... namespaces) {
        return new DefaultResourcePack(name, new HashSet<String>(), new HashSet<String>(Arrays.asList(namespaces)));
    }
    
    @Override
    public ResourcePack createClientAndDataPack(final String name, final Set<String> clientNamespaces, final Set<String> dataNamespaces) {
        return new DefaultResourcePack(name, clientNamespaces, dataNamespaces);
    }
}
