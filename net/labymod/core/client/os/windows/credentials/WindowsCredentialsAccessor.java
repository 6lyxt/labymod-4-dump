// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.os.windows.credentials;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import java.nio.charset.StandardCharsets;
import net.labymod.accountmanager.storage.credentials.windows.CredentialType;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;

public class WindowsCredentialsAccessor implements CredentialsAccessor
{
    public String getValue(final String serviceName, final String id) {
        final String key = serviceName + "|" + id;
        final IntByReference reference = new IntByReference();
        final PointerByReference pointerByReference = new PointerByReference();
        final boolean result = Advapi32_Credentials.INSTANCE.CredEnumerateW(null, 0, reference, pointerByReference);
        if (!result) {
            throw new RuntimeException("Failed to retrieve credential entries. Error code: " + Native.getLastError());
        }
        final Pointer[] pointers = pointerByReference.getValue().getPointerArray(0L, reference.getValue());
        for (int i = 0; i < reference.getValue(); ++i) {
            try {
                final Credential credential = new Credential(pointers[i]);
                credential.read();
                if (CredentialType.fromCode(credential.Type) == CredentialType.CRED_TYPE_GENERIC) {
                    final String address = credential.TargetName.getWideString(0L);
                    if (address != null && address.equals(key) && credential.CredentialBlobSize > 0) {
                        final byte[] bytes = credential.CredentialBlob.getByteArray(0L, credential.CredentialBlobSize);
                        return new String(bytes, StandardCharsets.UTF_8);
                    }
                }
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public void setValue(final String serviceName, final String id, final String value) throws Exception {
        final String key = serviceName + "|" + id;
        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        final Credential credential = new Credential();
        credential.Flags = 0;
        credential.Type = CredentialType.CRED_TYPE_GENERIC.getCode();
        (credential.TargetName = (Pointer)new Memory(Native.WCHAR_SIZE * (long)(key.length() + 1))).setWideString(0L, key);
        credential.CredentialBlobSize = bytes.length;
        (credential.CredentialBlob = (Pointer)new Memory((long)credential.CredentialBlobSize)).write(0L, bytes, 0, credential.CredentialBlobSize);
        credential.Persist = Credential.CRED_PERSIST_LOCAL_MACHINE;
        final boolean result = Advapi32_Credentials.INSTANCE.CredWriteW(credential, 0);
        if (!result) {
            throw new RuntimeException("Failed to create credential entry. Error code: " + Native.getLastError());
        }
    }
}
