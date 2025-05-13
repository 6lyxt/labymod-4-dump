// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.io;

import java.nio.charset.StandardCharsets;
import java.nio.BufferUnderflowException;
import net.labymod.serverapi.protocol.payload.exception.PayloadReaderException;
import java.nio.ByteBuffer;

@Deprecated(forRemoval = true, since = "4.2.24")
public class PayloadReader
{
    private final ByteBuffer buffer;
    
    public PayloadReader() {
        this(ByteBuffer.allocate(255));
    }
    
    public PayloadReader(final int capacity) {
        this(ByteBuffer.allocate(capacity));
    }
    
    public PayloadReader(final byte[] payload) {
        this(ByteBuffer.wrap(payload));
    }
    
    public PayloadReader(final ByteBuffer buffer) {
        this.buffer = buffer;
    }
    
    public int readVarInt() {
        try {
            int result = 0;
            int bitOffset = 0;
            byte currentByte;
            do {
                currentByte = this.buffer.get();
                result |= (currentByte & 0x7F) << bitOffset++ * 7;
                if (bitOffset > 5) {
                    throw new PayloadReaderException("VarInt is too big");
                }
            } while ((currentByte & 0x80) == 0x80);
            return result;
        }
        catch (final BufferUnderflowException exception) {
            throw new PayloadReaderException("The current position of the buffer is smaller than the limit.", exception);
        }
    }
    
    public String readString() {
        return this.readSizedString(32767);
    }
    
    public String readSizedString(final int maximumLength) {
        final int length = this.readVarInt();
        if (length > maximumLength * 4) {
            throw new PayloadReaderException(String.format("The received encoded string buffer length is longer than maximum allowed (%s > %s)", length, maximumLength * 4));
        }
        if (length < 0) {
            throw new PayloadReaderException("The received encoded string buffer length is less than zero.");
        }
        final byte[] data = new byte[length];
        try {
            this.buffer.get(data);
        }
        catch (final BufferUnderflowException exception) {
            throw new PayloadReaderException(String.format("Could not read %s, %s remaining.", length, this.buffer.remaining()));
        }
        return new String(data, StandardCharsets.UTF_8);
    }
    
    public boolean readBoolean() {
        return this.buffer.get() == 1;
    }
    
    public int readInt() {
        return this.buffer.getInt();
    }
    
    public long readLong() {
        return this.buffer.getLong();
    }
    
    public byte[] readBytes(final int length) {
        final byte[] data = new byte[length];
        this.buffer.get(data);
        return data;
    }
    
    public short readShort() {
        return this.buffer.getShort();
    }
    
    public float readFloat() {
        return this.buffer.getFloat();
    }
}
