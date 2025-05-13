// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.pipeline;

import net.labymod.core.labyconnect.protocol.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labyconnect.protocol.Packet;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    private static final Logging LOGGER;
    private final DefaultLabyConnect labyConnect;
    
    public PacketEncoder(final DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Packet packet, final ByteBuf byteBuf) {
        final int id = this.labyConnect.getPacketRegistry().getPacketId(packet);
        if (id != 62 && id != 63) {
            PacketEncoder.LOGGER.debug("[LABYCONNECT] [OUT] " + id + " " + packet.getClass().getSimpleName(), new Object[0]);
        }
        final PacketBuffer buffer = new PacketBuffer(byteBuf);
        buffer.writeVarIntToBuffer(id);
        packet.write(buffer);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
