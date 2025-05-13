// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final ais resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final ais delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static ais unwrap(final ais resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
