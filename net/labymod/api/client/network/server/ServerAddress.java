// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import net.labymod.api.Laby;
import java.util.Locale;
import java.net.InetSocketAddress;

public class ServerAddress
{
    private static final ServerAddressResolver RESOLVER;
    private final String host;
    private final int port;
    private final boolean resolved;
    private InetSocketAddress address;
    
    public ServerAddress(final String host, final int port, final boolean resolved) {
        this.host = (host.endsWith(".") ? host.substring(0, host.length() - 1) : host);
        this.port = port;
        this.resolved = resolved;
    }
    
    public ServerAddress(final String host, final int port) {
        this(host, port, false);
    }
    
    public static ServerAddress parse(final String raw) {
        if (raw == null) {
            return null;
        }
        final String[] parts = raw.split(":");
        if (parts.length == 0) {
            return new ServerAddress(raw, 25565);
        }
        if (parts.length == 1) {
            return new ServerAddress(parts[0], 25565);
        }
        String host = null;
        String rawPort = null;
        if (parts.length > 2) {
            final int start = raw.indexOf("[");
            if (start == 0) {
                final int end = raw.indexOf("]");
                if (end > 0) {
                    host = raw.substring(0, end + 1);
                    if (raw.length() > end + 2) {
                        rawPort = raw.substring(end + 2);
                    }
                }
            }
            else {
                host = raw;
            }
        }
        else {
            host = parts[0];
            rawPort = parts[1];
        }
        int port = 25565;
        if (rawPort != null) {
            try {
                port = Math.min(65565, Math.max(1, Integer.parseInt(rawPort)));
            }
            catch (final Exception ex) {}
        }
        if (host == null) {
            host = raw;
        }
        return new ServerAddress(host, port);
    }
    
    public static ServerAddress resolve(final String raw) {
        return ServerAddress.RESOLVER.resolve(parse(raw));
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public ServerAddress resolve() {
        if (this.resolved) {
            return this;
        }
        return ServerAddress.RESOLVER.resolve(this);
    }
    
    public InetSocketAddress getAddress() {
        if (this.address == null) {
            this.address = new InetSocketAddress(this.host, this.port);
        }
        return this.address;
    }
    
    public boolean matches(final ServerAddress serverAddress) {
        return this.matches(serverAddress, false);
    }
    
    public boolean matches(final ServerAddress serverAddress, final boolean ignoreSubDomains) {
        return serverAddress != null && this.matches(serverAddress.getHost(), serverAddress.getPort(), ignoreSubDomains);
    }
    
    public boolean matches(final String address, final int port, final boolean ignoreSubDomains) {
        if (address == null) {
            return false;
        }
        String thisAddress = this.host.toLowerCase(Locale.ROOT);
        String thatAddress = address.toLowerCase(Locale.ROOT);
        if (ignoreSubDomains) {
            final String[] thisSplit = thisAddress.split("\\.");
            if (thisSplit.length > 2) {
                thisAddress = thisSplit[thisSplit.length - 2] + "." + thisSplit[thisSplit.length - 1];
            }
            final String[] thatSplit = thatAddress.split("\\.");
            if (thatSplit.length > 2) {
                thatAddress = thatSplit[thatSplit.length - 2] + "." + thatSplit[thatSplit.length - 1];
            }
        }
        thisAddress = thisAddress + ":" + this.port;
        thatAddress = thatAddress + ":" + port;
        return thisAddress.equals(thatAddress);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ServerAddress address = (ServerAddress)o;
        return this.port == address.port && this.host.equalsIgnoreCase(address.host);
    }
    
    @Override
    public int hashCode() {
        int result = (this.host != null) ? this.host.hashCode() : 0;
        result = 31 * result + this.port;
        return result;
    }
    
    @Override
    public String toString() {
        if (this.port == 25565) {
            return this.host;
        }
        return this.host + ":" + this.port;
    }
    
    static {
        RESOLVER = Laby.references().serverAddressResolver();
    }
}
