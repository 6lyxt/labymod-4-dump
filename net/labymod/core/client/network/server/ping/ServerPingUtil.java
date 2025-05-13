// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.ping;

import java.io.OutputStream;
import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerPingUtil
{
    public static void io(final boolean value, final String message) throws IOException {
        if (value) {
            throw new IOException(message);
        }
    }
    
    public static void writeLegacyString(final DataOutputStream out, final String value) throws IOException {
        out.writeShort(value.length());
        out.write(value.getBytes(StandardCharsets.UTF_16BE));
    }
    
    public static String readLegacyString(final DataInputStream input) throws IOException {
        final int length = input.readUnsignedShort() << 1;
        final byte[] encodedBuffer = input.readNBytes(length);
        return new String(encodedBuffer, StandardCharsets.UTF_16BE);
    }
    
    public static int readVarInt(final DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            final int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((k & 0x80) != 0x80) {
                return i;
            }
        }
    }
    
    public static void writeVarInt(final OutputStream out, int value) throws IOException {
        while ((value & 0xFFFFFF80) != 0x0) {
            out.write((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        out.write(value);
    }
}
