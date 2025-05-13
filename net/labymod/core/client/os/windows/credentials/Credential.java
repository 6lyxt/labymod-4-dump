// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.credentials;

import java.util.Arrays;
import java.util.List;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Credential extends Structure
{
    public static int CRED_PERSIST_LOCAL_MACHINE;
    public int Flags;
    public int Type;
    public Pointer TargetName;
    public Pointer Comment;
    public Pointer LastWritten;
    public int CredentialBlobSize;
    public Pointer CredentialBlob;
    public int Persist;
    public int AttributeCount;
    public Pointer Attributes;
    public Pointer TargetAlias;
    public Pointer UserName;
    
    protected List getFieldOrder() {
        return Arrays.asList("Flags", "Type", "TargetName", "Comment", "LastWritten", "CredentialBlobSize", "CredentialBlob", "Persist", "AttributeCount", "Attributes", "TargetAlias", "UserName");
    }
    
    public Credential(final Pointer p) {
        super(p);
    }
    
    public Credential() {
    }
    
    static {
        Credential.CRED_PERSIST_LOCAL_MACHINE = 2;
    }
}
