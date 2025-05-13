// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.pipeline;

import net.labymod.core.labyconnect.protocol.Packet;
import java.io.IOException;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.api.util.logging.Logging;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder
{
    private static final Logging LOGGER;
    private final DefaultLabyConnect labyConnect;
    
    public PacketDecoder(final DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> objects) throws Exception {
        if (byteBuf.readableBytes() < 1) {
            return;
        }
        final PacketBuffer buffer = new PacketBuffer(byteBuf);
        final int id = buffer.readVarIntFromBuffer();
        final Packet packet = this.labyConnect.getPacketRegistry().getPacket(id);
        if (id != 62 && id != 63) {
            PacketDecoder.LOGGER.debug("[LABYCONNECT] [IN] " + id + " " + packet.getClass().getSimpleName(), new Object[0]);
        }
        packet.read(buffer);
        if (byteBuf.readableBytes() > 0) {
            throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + byteBuf.readableBytes() + " bytes extra whilst reading packet " + String.valueOf(packet));
        }
        objects.add(packet);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
