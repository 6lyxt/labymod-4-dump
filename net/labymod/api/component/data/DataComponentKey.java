// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.component.data;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Function;

record DataComponentKey(String name) {
    private static final Function<String, DataComponentKey> KEYS;
    
    public static DataComponentKey simple(final String name) {
        return DataComponentKey.KEYS.apply(name);
    }
    
    public static DataComponentKey fromId(final String path) {
        return fromId("minecraft", path);
    }
    
    public static DataComponentKey fromId(final String namespace, final String path) {
        return fromId(ResourceLocation.create(namespace, path));
    }
    
    public static DataComponentKey fromId(final ResourceLocation id) {
        return DataComponentKey.KEYS.apply(id.toString());
    }
    
    static {
        KEYS = Laby.references().functionMemoizeStorage().memoize(DataComponentKey::new);
    }
}
