// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util;

import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

public final class ResourcesUtil
{
    private ResourcesUtil() {
    }
    
    public static boolean hasResource(final ResourceLocation location) {
        final ReferenceStorage ref = Laby.references();
        return ref.textureRepository().hasResource(location);
    }
}
