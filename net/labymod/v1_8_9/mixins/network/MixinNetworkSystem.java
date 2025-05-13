// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.network;

import java.net.SocketAddress;
import java.util.Iterator;
import java.net.InetSocketAddress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import io.netty.channel.ChannelFuture;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.network.VersionedNetworkSystem;

@Mixin({ ll.class })
public class MixinNetworkSystem implements VersionedNetworkSystem
{
    @Shadow
    @Final
    private List<ChannelFuture> g;
    
    @Override
    public int findAnyPort() {
        for (final ChannelFuture endpoint : this.g) {
            final SocketAddress address = endpoint.channel().localAddress();
            if (address instanceof final InetSocketAddress inetSocketAddress) {
                return inetSocketAddress.getPort();
            }
        }
        return -1;
    }
}
