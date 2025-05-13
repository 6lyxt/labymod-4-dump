// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final aug resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final aug delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static aug unwrap(final aug resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
