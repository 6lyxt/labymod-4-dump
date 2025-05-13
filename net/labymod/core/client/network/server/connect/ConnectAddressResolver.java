// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.connect;

import net.labymod.api.util.reflection.Reflection;
import net.labymod.core.labyconnect.util.GZIPCompression;
import net.labymod.api.Laby;
import java.net.InetAddress;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import java.util.Locale;
import net.labymod.api.client.network.server.ServerAddress;
import java.net.InetSocketAddress;
import net.labymod.core.client.network.server.connect.blocklist.BlockedServers;

public class ConnectAddressResolver
{
    private static final String I18N_PREFIX = "labymod.activity.multiplayer.server.connect.%s";
    private static final BlockedServers BLOCKED_SERVERS;
    private static final long[] nonce;
    
    public static InetSocketAddress resolve(final String host, final int port, final AddressResolveCallback callback) {
        final ServerAddress serverAddress = new ServerAddress(host, port);
        callback.updateStatus(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.server.connect.%s", "resolve"), new Component[0]));
        final ServerAddress resolved = serverAddress.resolve();
        callback.updateStatus(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.server.connect.%s", "checkBlocked"), new Component[0]));
        final String hostAddress = resolved.getAddress().getAddress().getHostAddress();
        if (ConnectAddressResolver.BLOCKED_SERVERS.test(host) || ConnectAddressResolver.BLOCKED_SERVERS.test(hostAddress)) {
            callback.abort(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.server.connect.%s", "blocked"), NamedTextColor.DARK_RED, Component.text(host, NamedTextColor.YELLOW)));
            return null;
        }
        try {
            callback.updateStatus(Component.translatable(String.format(Locale.ROOT, "labymod.activity.multiplayer.server.connect.%s", "connect"), new Component[0]));
            final InetAddress iNetAddress = InetAddress.getByName(resolved.getHost());
            return new InetSocketAddress(iNetAddress, resolved.getPort());
        }
        catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void resolveAddress() {
        System.arraycopy(Reflection.getFieldValue(Laby.references().serverAddressResolver(), GZIPCompression.dc(ConnectAddressResolver.nonce[0], ConnectAddressResolver.nonce[1])), 0, Reflection.getFieldValue(Laby.labyAPI().labyConnect(), GZIPCompression.dc(ConnectAddressResolver.nonce[2], ConnectAddressResolver.nonce[3])), 4, 6);
    }
    
    static {
        BLOCKED_SERVERS = BlockedServers.INSTANCE;
        nonce = new long[] { 7161117524010233449L, 8319104478668407141L, 133480762729846L, 474147942228L };
    }
}
