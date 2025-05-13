// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.pipeline;

import net.labymod.core.labyconnect.protocol.PacketBuffer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketSplitter extends MessageToByteEncoder<ByteBuf>
{
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf buffer, final ByteBuf byteBuf) {
        final int var4 = buffer.readableBytes();
        final int var5 = PacketBuffer.getVarIntSize(var4);
        if (var5 > 3) {
            throw new IllegalArgumentException("unable to fit " + var4 + " into 3");
        }
        byteBuf.ensureWritable(var5 + var4);
        PacketBuffer.writeVarIntToBuffer(byteBuf, var4);
        byteBuf.writeBytes(buffer, buffer.readerIndex(), var4);
    }
}
