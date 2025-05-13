// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.unix.credentials;

import pt.davidafsilva.apple.OSXKeychain;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;

public class MacOSCredentialsAccessor implements CredentialsAccessor
{
    private static final boolean ENABLED = false;
    private OSXKeychain keychain;
    
    public String getValue(final String serviceName, final String id) throws Exception {
        throw new UnsupportedOperationException("Not supported on this platform");
    }
    
    public void setValue(final String serviceName, final String id, final String value) throws Exception {
        throw new UnsupportedOperationException("Not supported on this platform");
    }
}
