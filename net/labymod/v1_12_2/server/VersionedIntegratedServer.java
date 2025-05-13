// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.server;

import net.labymod.v1_12_2.client.gui.CompletableGuiShareToLan;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.server.LocalWorld;
import net.labymod.v1_12_2.network.VersionedNetworkSystem;
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
        if (!bib.z().D()) {
            return false;
        }
        final chd server = bib.z().F();
        return server != null && server.w() && ((VersionedNetworkSystem)server.an()).findAnyPort() != -1;
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!bib.z().D()) {
            return null;
        }
        final chd server = bib.z().F();
        if (server == null || !server.w()) {
            return null;
        }
        ams gameType = server.n();
        if (gameType == null) {
            gameType = ams.a;
        }
        final boolean allowCheats = ((VersionedServerConfigurationManager)server.am()).isCommandsAllowedForAll();
        final int port = ((VersionedNetworkSystem)server.an()).findAnyPort();
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
        return new LocalWorld(server.T(), server.S(), gameMode, allowCheats, port, null);
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final blk previousScreen = bib.z().m;
        final bll screen = new bll(previousScreen);
        ((CompletableGuiShareToLan)screen).setFinishHandler(success -> {
            bib.z().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        bib.z().a((blk)screen);
    }
}
