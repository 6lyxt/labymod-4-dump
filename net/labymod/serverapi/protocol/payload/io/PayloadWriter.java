// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.io;

import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import java.nio.BufferOverflowException;
import net.labymod.serverapi.protocol.payload.exception.PayloadWriterException;
import java.nio.ByteBuffer;

@Deprecated(forRemoval = true, since = "4.2.24")
public class PayloadWriter
{
    private ByteBuffer buffer;
    
    public PayloadWriter() {
        this(ByteBuffer.allocate(255));
    }
    
    public PayloadWriter(final int capacity) {
        this(ByteBuffer.allocate(capacity));
    }
    
    public PayloadWriter(final ByteBuffer buffer) {
        this.buffer = buffer;
    }
    
    public void writeVarInt(int value) {
        try {
            this.ensureSize(5);
            while ((value & 0xFFFFFF80) != 0x0) {
                this.buffer.put((byte)((value & 0x7F) | 0x80));
                value >>>= 7;
            }
            this.buffer.put((byte)value);
        }
        catch (final BufferOverflowException exception) {
            throw new PayloadWriterException("The buffer overflowed while writing.", exception);
        }
    }
    
    public void writeBytes(final byte[] bytes) {
        if (bytes.length == 0) {
            return;
        }
        this.ensureSize(bytes.length);
        this.buffer.put(bytes);
    }
    
    public void writeUuid(@NotNull final UUID value) {
        this.writeString(value.toString());
    }
    
    public void writeString(@NotNull final String value) {
        this.writeSizedString(value, 32767);
    }
    
    public void writeSizedString(@NotNull final String value, final int maximumLength) {
        final byte[] valueData = value.getBytes(StandardCharsets.UTF_8);
        if (valueData.length > maximumLength) {
            throw new IllegalStateException(String.format("String too big (was %s bytes encoded, maximum length %s)", valueData.length, maximumLength));
        }
        this.writeVarInt(valueData.length);
        this.writeBytes(valueData);
    }
    
    public void writeInt(final int value) {
        this.ensureSize(4);
        this.buffer.putInt(value);
    }
    
    public void writeBoolean(final boolean value) {
        this.ensureSize(1);
        this.buffer.put((byte)(value ? 1 : 0));
    }
    
    public void writeShort(final short value) {
        this.ensureSize(2);
        this.buffer.putShort(value);
    }
    
    public void writeLong(final long value) {
        this.ensureSize(8);
        this.buffer.putLong(value);
    }
    
    public void writeFloat(final float value) {
        this.ensureSize(4);
        this.buffer.putFloat(value);
    }
    
    public byte[] toByteArray() {
        this.bufferFlip();
        final byte[] buffer = new byte[this.buffer.remaining()];
        this.buffer.get(buffer);
        return buffer;
    }
    
    private void ensureSize(final int length) {
        final int position = this.buffer.position();
        if (position + length >= this.buffer.limit()) {
            final int newLength = (position + length) * 4;
            final ByteBuffer copy = this.buffer.isDirect() ? ByteBuffer.allocateDirect(newLength) : ByteBuffer.allocate(newLength);
            this.bufferFlip();
            copy.put(this.buffer);
            this.buffer = copy;
        }
    }
    
    private void bufferFlip() {
        this.buffer.flip();
    }
}
