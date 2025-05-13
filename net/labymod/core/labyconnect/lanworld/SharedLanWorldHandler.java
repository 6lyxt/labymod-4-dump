// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.lanworld;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ActionBar;
import net.labymod.labypeer.client.tunnel.PeerTunnel;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.labypeer.client.tunnel.handler.TunnelHandler;

public class SharedLanWorldHandler implements TunnelHandler
{
    private final ChatExecutor chatExecutor;
    private final SharedLanWorldService service;
    private final PeerTunnel tunnel;
    private final String username;
    private ActionBar actionBar;
    
    public SharedLanWorldHandler(final SharedLanWorldService service, final PeerTunnel tunnel, final String username) {
        this.chatExecutor = Laby.labyAPI().minecraft().chatExecutor();
        this.service = service;
        this.tunnel = tunnel;
        this.username = username;
    }
    
    public void onTunnelOpen() {
    }
    
    public void onTunnelClose() {
        this.service.getOpenTunnels().values().remove(this.tunnel);
    }
    
    public void onConnectionLost() {
        this.execute(() -> this.actionBar = this.chatExecutor.displayActionBarContinuous(Component.translatable("labymod.lanworld.reconnect.connectionLost", NamedTextColor.RED, Component.text(this.username))));
    }
    
    public void onReconnectFailed(final int tryIndex, final int upcomingTries) {
        if (upcomingTries != 0) {
            return;
        }
        this.execute(() -> {
            if (this.actionBar != null) {
                this.actionBar.remove();
                this.actionBar = null;
            }
            final Component message = Component.translatable("labymod.lanworld.reconnect.failed", NamedTextColor.RED, Component.text(this.username));
            if (this.service.isHost()) {
                this.chatExecutor.displayActionBar(message);
            }
            else {
                Laby.labyAPI().minecraft().clientPacketListener().simulateKick(message);
            }
        });
    }
    
    public void onReconnectSuccess(final int tryIndex) {
        this.execute(() -> {
            if (this.actionBar != null) {
                this.actionBar.remove();
                this.actionBar = null;
            }
            this.chatExecutor.displayActionBar(Component.translatable("labymod.lanworld.reconnect.success", NamedTextColor.GREEN, Component.text(this.username)));
        });
    }
    
    private void execute(final Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }
}
