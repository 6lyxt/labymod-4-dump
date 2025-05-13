// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final ajv resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final ajv delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static ajv unwrap(final ajv resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
