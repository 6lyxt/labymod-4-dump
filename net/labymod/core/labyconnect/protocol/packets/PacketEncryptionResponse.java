// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import net.labymod.core.labyconnect.util.CryptManager;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.labymod.core.labyconnect.protocol.Packet;

public class PacketEncryptionResponse extends Packet
{
    private byte[] sharedSecret;
    private byte[] verifyToken;
    private byte[] pin;
    
    public PacketEncryptionResponse(final SecretKey key, final PublicKey publicKey, final byte[] hash) {
        this.pin = new byte[0];
        this.sharedSecret = CryptManager.encryptData(publicKey, key.getEncoded());
        this.verifyToken = CryptManager.encryptData(publicKey, hash);
    }
    
    public PacketEncryptionResponse(final SecretKey key, final PublicKey publicKey, final byte[] hash, final String pin) {
        this(key, publicKey, hash);
        this.pin = CryptManager.encryptData(publicKey, pin.getBytes(StandardCharsets.UTF_8));
    }
    
    public PacketEncryptionResponse() {
        this.pin = new byte[0];
    }
    
    public byte[] getSharedSecret() {
        return this.sharedSecret;
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    @Override
    public void read(final PacketBuffer buf) {
        this.sharedSecret = buf.readByteArray();
        this.verifyToken = buf.readByteArray();
    }
    
    @Override
    public void write(final PacketBuffer buf) {
        buf.writeByteArray(new byte[] { 42 });
        buf.writeByteArray(this.sharedSecret);
        buf.writeByteArray(this.verifyToken);
        buf.writeByteArray(this.pin);
    }
    
    @Override
    public void handle(final PacketHandler packetHandler) {
    }
}
