// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final aua resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final aua delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static aua unwrap(final aua resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
