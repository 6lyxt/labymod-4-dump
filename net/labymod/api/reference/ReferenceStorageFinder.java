// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference;

import java.util.Iterator;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.reference.util.ReferenceUtil;

public final class ReferenceStorageFinder
{
    private static final System.Logger LOGGER;
    
    public static ReferenceStorageAccessor find(final ClassLoader loader) {
        ReferenceUtil.setThrowableConsumer(throwable -> ReferenceStorageFinder.LOGGER.log(System.Logger.Level.ERROR, "An error occurred while creating a reference", throwable));
        final CustomServiceLoader<ReferenceStorageAccessor> accessors = CustomServiceLoader.load(ReferenceStorageAccessor.class, loader, CustomServiceLoader.ServiceType.ADVANCED);
        ReferenceStorageAccessor finalStorage = null;
        for (final ReferenceStorageAccessor accessor : accessors) {
            if (accessor.getClass().getClassLoader() != loader) {
                continue;
            }
            if (accessor.isVersion()) {
                finalStorage = accessor;
                break;
            }
            finalStorage = accessor;
        }
        if (finalStorage == null) {
            throw new IllegalStateException("No ReferenceStorage could be found in the '" + String.valueOf(loader) + "' classloader.");
        }
        return finalStorage;
    }
    
    static {
        LOGGER = System.getLogger("ReferenceStorageFinder");
    }
}
