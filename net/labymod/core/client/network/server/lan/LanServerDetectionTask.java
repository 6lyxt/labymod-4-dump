// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.lan;

import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.server.LocalWorld;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Laby;
import java.nio.charset.StandardCharsets;
import java.net.DatagramPacket;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.client.network.server.ConnectableServerData;
import java.util.Map;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import java.net.MulticastSocket;

public class LanServerDetectionTask implements Runnable
{
    private static final long TIMEOUT;
    private MulticastSocket socket;
    private LanServerCallback callback;
    private final Map<ConnectableServerData, Long> servers;
    private final byte[] buffer;
    
    public LanServerDetectionTask() {
        this.servers = new ConcurrentHashMap<ConnectableServerData, Long>();
        this.buffer = new byte[1024];
    }
    
    @Override
    public void run() {
        final DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        try {
            this.socket.receive(packet);
            final String serverInfoString = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            final String motd = this.parseMotd(serverInfoString);
            final String hostAddress = packet.getAddress().getHostAddress();
            final String rawPort = this.parsePort(serverInfoString);
            int port;
            try {
                port = Integer.parseInt(rawPort);
            }
            catch (final NumberFormatException ignored) {
                return;
            }
            final LocalWorld localServer = Laby.references().integratedServer().getLocalWorld();
            if (localServer != null && localServer.port() == port) {
                final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
                if (clientPlayer != null && motd.contains(clientPlayer.getName())) {
                    return;
                }
            }
            final ConnectableServerData serverInfo = ConnectableServerData.builder().name(motd).address(hostAddress + ":" + port).lan(true).build();
            final boolean registered = this.servers.containsKey(serverInfo);
            this.servers.put(serverInfo, TimeUtil.getMillis() + LanServerDetectionTask.TIMEOUT);
            if (!registered) {
                this.callback.onServerAdd(serverInfo);
            }
        }
        catch (final Exception ex) {}
    }
    
    public void handleRemoval() {
        try {
            this.servers.entrySet().removeIf(entry -> {
                if (entry.getValue() <= TimeUtil.getMillis()) {
                    this.callback.onServerRemove(entry.getKey());
                    return true;
                }
                else {
                    return false;
                }
            });
        }
        catch (final Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    
    private String parseMotd(final String s) {
        final int motdStart = s.indexOf("[MOTD]");
        final int motdEnd = s.indexOf("[/MOTD]");
        return s.substring(motdStart + "[MOTD]".length(), motdEnd);
    }
    
    private String parsePort(final String s) {
        final int portStart = s.indexOf("[AD]");
        final int portEnd = s.indexOf("[/AD]");
        return s.substring(portStart + "[AD]".length(), portEnd);
    }
    
    public void setSocket(final MulticastSocket socket) {
        this.socket = socket;
    }
    
    public void setCallback(@NotNull final LanServerCallback callback) {
        this.callback = callback;
    }
    
    public void reset() {
        this.servers.clear();
    }
    
    static {
        TIMEOUT = TimeUnit.SECONDS.toMillis(10L);
    }
}
