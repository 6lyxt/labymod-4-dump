// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server;

import java.util.function.Function;
import org.xbill.DNS.logger.LogUtil;
import net.labymod.core.client.network.dns.DNSLogger;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.Lookup;
import net.labymod.core.client.network.server.connect.blocklist.BlockedServers;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.util.HardwareUtils;
import net.labymod.api.Laby;
import java.util.HashMap;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.Map;
import net.labymod.api.client.network.server.ServerAddressResolver;

public abstract class DefaultAbstractServerAddressResolver implements ServerAddressResolver
{
    private static final String SRV_PREFIX = "_minecraft._tcp.";
    private static final long CACHE_PERIOD = 300000L;
    private final Map<ServerAddress, ResolvedAddress> resolvedAddresses;
    private byte[] interfaceAddress;
    
    protected DefaultAbstractServerAddressResolver() {
        this.resolvedAddresses = new HashMap<ServerAddress, ResolvedAddress>();
        Laby.labyAPI().eventBus().registerListener(this);
        try {
            this.interfaceAddress = HardwareUtils.getMainNetworkInterfaceAddress();
        }
        catch (final Throwable e) {
            e.printStackTrace();
        }
        if (this.interfaceAddress == null) {
            this.interfaceAddress = new byte[6];
        }
    }
    
    @Override
    public ServerAddress resolve(final ServerAddress address) {
        final ResolvedAddress resolvedAddress = this.resolvedAddresses.get(address);
        if (resolvedAddress != null) {
            if (resolvedAddress.prioritized && resolvedAddress.isExpired()) {
                Task.builder(() -> this.resolve(address, resolvedAddress, false)).build().execute();
            }
            resolvedAddress.lastResolvedAt = TimeUtil.getMillis();
            return resolvedAddress.address;
        }
        final ServerAddress resolve = this.resolve(address, null, false);
        return (resolve == null) ? address : resolve;
    }
    
    @Override
    public void register(final ServerAddress address) {
        final ResolvedAddress resolvedAddress = this.resolvedAddresses.get(address);
        if (resolvedAddress != null) {
            resolvedAddress.prioritized = true;
            return;
        }
        this.resolve(address, null, true);
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (event.phase() == Phase.PRE || this.resolvedAddresses.isEmpty()) {
            return;
        }
        final long millis = TimeUtil.getMillis();
        CollectionHelper.removeIf(this.resolvedAddresses.values(), resolvedAddress -> !resolvedAddress.prioritized && resolvedAddress.isExpired(millis));
    }
    
    private boolean isIPv4(final String input) {
        final int length = input.length();
        int octet = 0;
        int octetCount = 0;
        for (final char c : input.toCharArray()) {
            if (c == '.') {
                if (octet < 0 || ++octetCount > 3) {
                    return false;
                }
                octet = 0;
            }
            else {
                if (c < '0' || c > '9') {
                    return false;
                }
                octet = octet * 10 + (c - '0');
                if (octet > 255) {
                    return false;
                }
            }
        }
        return octet >= 0 && octetCount == 3;
    }
    
    public ServerAddress resolveInternal(final ServerAddress address) {
        return (address.getPort() == 25565) ? getServerAddress(address.getHost()) : address;
    }
    
    private ServerAddress resolve(final ServerAddress address, final ResolvedAddress resolved, final boolean prioritize) {
        if (resolved == null && this.isIPv4(address.getHost())) {
            return null;
        }
        final ServerAddress serverAddress = this.resolveInternal(address);
        if (serverAddress == null) {
            return null;
        }
        if (resolved != null) {
            return resolved.address = serverAddress;
        }
        final ResolvedAddress value = new ResolvedAddress(serverAddress);
        value.prioritized = prioritize;
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            if (this.resolvedAddresses.containsKey(address)) {
                return;
            }
            else {
                this.resolvedAddresses.put(address, value);
                return;
            }
        });
        return serverAddress;
    }
    
    public byte[] getInterfaceAddress() {
        return this.interfaceAddress;
    }
    
    public static ServerAddress getServerAddress(final String address) {
        try {
            if (!BlockedServers.INSTANCE.test("_minecraft._tcp." + address)) {
                final ServerAddress resolved = resolveSrv(address);
                if (resolved != null) {
                    return resolved;
                }
            }
        }
        catch (final Exception ex) {}
        return new ServerAddress(address, 25565);
    }
    
    public static ServerAddress resolveSrv(final String host) {
        try {
            final Record[] records = new Lookup("_minecraft._tcp." + host, 33).run();
            if (records != null && records.length > 0) {
                final SRVRecord record = (SRVRecord)records[0];
                return new ServerAddress(record.getTarget().toString(), record.getPort());
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    static {
        LogUtil.setLoggerFactory((Function)DNSLogger::new);
    }
    
    private static class ResolvedAddress
    {
        private ServerAddress address;
        private long lastResolvedAt;
        private boolean prioritized;
        
        private ResolvedAddress(final ServerAddress address) {
            this.address = address;
            this.lastResolvedAt = TimeUtil.getMillis();
        }
        
        private boolean isExpired() {
            return this.isExpired(TimeUtil.getMillis());
        }
        
        private boolean isExpired(final long millis) {
            return millis - this.lastResolvedAt > 300000L;
        }
    }
}
