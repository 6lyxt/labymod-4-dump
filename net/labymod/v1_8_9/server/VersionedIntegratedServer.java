// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.server;

import net.labymod.v1_8_9.client.gui.CompletableGuiShareToLan;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.server.LocalWorld;
import net.labymod.v1_8_9.network.VersionedNetworkSystem;
import javax.inject.Inject;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.server.AbstractIntegratedServer;

@Singleton
@Implements(IntegratedServer.class)
public class VersionedIntegratedServer extends AbstractIntegratedServer
{
    @Inject
    public VersionedIntegratedServer() {
    }
    
    @Override
    public boolean isLanWorldOpened() {
        if (!ave.A().E()) {
            return false;
        }
        final bpo server = ave.A().G();
        return server != null && server.v() && ((VersionedNetworkSystem)server.aq()).findAnyPort() != -1;
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!ave.A().E()) {
            return null;
        }
        final bpo server = ave.A().G();
        if (server == null || !server.v()) {
            return null;
        }
        adp.a gameType = server.m();
        if (gameType == null) {
            gameType = adp.a.a;
        }
        final boolean allowCheats = ((VersionedServerConfigurationManager)server.ap()).isCommandsAllowedForAll();
        final int port = ((VersionedNetworkSystem)server.aq()).findAnyPort();
        GameMode gameMode = null;
        switch (gameType) {
            case a:
            case b: {
                gameMode = GameMode.SURVIVAL;
                break;
            }
            case c: {
                gameMode = GameMode.CREATIVE;
                break;
            }
            case d: {
                gameMode = GameMode.ADVENTURE;
                break;
            }
            case e: {
                gameMode = GameMode.SPECTATOR;
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(gameType));
            }
        }
        return new LocalWorld(server.V(), server.U(), gameMode, allowCheats, port, null);
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final axu previousScreen = ave.A().m;
        final axw screen = new axw(previousScreen);
        ((CompletableGuiShareToLan)screen).setFinishHandler(success -> {
            ave.A().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        ave.A().a((axu)screen);
    }
}
