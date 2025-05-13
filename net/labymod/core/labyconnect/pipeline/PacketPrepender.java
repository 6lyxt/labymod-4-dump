// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.pipeline;

import net.labymod.core.labyconnect.protocol.PacketBuffer;
import io.netty.buffer.Unpooled;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketPrepender extends ByteToMessageDecoder
{
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf buffer, final List<Object> objects) {
        buffer.markReaderIndex();
        final byte[] a = new byte[3];
        for (int i = 0; i < a.length; ++i) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }
            a[i] = buffer.readByte();
            if (a[i] >= 0) {
                final PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(a));
                try {
                    final int varInt = buf.readVarIntFromBuffer();
                    if (buffer.readableBytes() < varInt) {
                        buffer.resetReaderIndex();
                        return;
                    }
                    objects.add(buffer.readBytes(varInt));
                }
                finally {
                    buf.getBuffer().release();
                }
                return;
            }
        }
        throw new RuntimeException("length wider than 21-bit");
    }
}
