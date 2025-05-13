// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect;

import io.netty.channel.Channel;
import net.labymod.core.labyconnect.pipeline.PacketEncoder;
import net.labymod.core.labyconnect.pipeline.PacketSplitter;
import net.labymod.core.labyconnect.pipeline.PacketDecoder;
import net.labymod.core.labyconnect.pipeline.PacketPrepender;
import io.netty.channel.ChannelHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelInitializer;

public class LabyConnectChannelHandler extends ChannelInitializer<NioSocketChannel>
{
    private final DefaultLabyConnect labyConnect;
    private final PacketHandler handler;
    private NioSocketChannel channel;
    
    public LabyConnectChannelHandler(final DefaultLabyConnect labyConnect, final PacketHandler handler) {
        this.labyConnect = labyConnect;
        this.handler = handler;
    }
    
    protected void initChannel(final NioSocketChannel channel) {
        this.channel = channel;
        channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30L, TimeUnit.SECONDS)).addLast("splitter", (ChannelHandler)new PacketPrepender()).addLast("decoder", (ChannelHandler)new PacketDecoder(this.labyConnect)).addLast("prepender", (ChannelHandler)new PacketSplitter()).addLast("encoder", (ChannelHandler)new PacketEncoder(this.labyConnect)).addLast(new ChannelHandler[] { (ChannelHandler)this.handler });
    }
    
    public NioSocketChannel getChannel() {
        return this.channel;
    }
}
