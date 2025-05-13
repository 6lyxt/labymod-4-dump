// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.modloader;

import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Referenceable
public class ModLoaderRegistry extends DefaultRegistry<ModLoader>
{
    public static ModLoaderRegistry instance() {
        return Laby.references().modLoaderRegistry();
    }
}
