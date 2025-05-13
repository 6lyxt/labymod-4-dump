// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.resources.pack;

public final class PackUtil
{
    public static boolean isModifiedPackResources(final atb resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof final WrappedPackResources wrappedPackResources) {
            final atb delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }
    
    public static atb unwrap(final atb resources) {
        if (resources instanceof final WrappedPackResources wrapped) {
            return wrapped.delegate();
        }
        return resources;
    }
}
