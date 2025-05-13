// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.linux.credentials;

public class GKResult
{
    public static final int OK = 0;
    private final GKLib gklib;
    private final int code;
    
    public GKResult(final GKLib gklib, final int code) {
        this.gklib = gklib;
        this.code = code;
    }
    
    public <T> void error() throws RuntimeException {
        throw new RuntimeException(this.gklib.gnome_keyring_result_to_message(this.code));
    }
    
    public boolean success() {
        return this.code == 0;
    }
}
