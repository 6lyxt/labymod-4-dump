// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.server;

import net.labymod.v1_20_6.client.gui.screen.CompletableShareToLanScreen;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.core.client.world.storage.accessor.LevelStorageAccessor;
import net.labymod.api.server.LocalWorld;
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
        if (!ffh.Q().T()) {
            return false;
        }
        final gtg server = ffh.Q().V();
        return server != null && server.r();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!ffh.Q().T()) {
            return null;
        }
        final gtg server = ffh.Q().V();
        if (server == null) {
            return null;
        }
        dbx gameType = server.bf();
        if (gameType == null) {
            gameType = dbx.a;
        }
        final boolean allowCommands = server.ah().v();
        final int port = server.R();
        final epy.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String e = server.bb().e();
        final String levelId = ((LevelStorageAccessor)storageSource).getLevelId();
        return new LocalWorld(e, levelId, switch (gameType) {
            default -> throw new MatchException(null, null);
            case b -> GameMode.CREATIVE;
            case a -> GameMode.SURVIVAL;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        }, allowCommands, port, server.B().orElse(null));
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final fnf previousScreen = ffh.Q().y;
        final fng screen = new fng(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            ffh.Q().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        ffh.Q().a((fnf)screen);
    }
}
