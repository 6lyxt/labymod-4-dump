// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final asq resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final asq delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static asq unwrap(final asq resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
