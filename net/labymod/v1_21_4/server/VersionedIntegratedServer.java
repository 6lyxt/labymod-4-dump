// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.server;

import net.labymod.v1_21_4.client.gui.screen.CompletableShareToLanScreen;
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
        if (!flk.Q().T()) {
            return false;
        }
        final hje server = flk.Q().V();
        return server != null && server.r();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!flk.Q().T()) {
            return null;
        }
        final hje server = flk.Q().V();
        if (server == null) {
            return null;
        }
        dgg gameType = server.bd();
        if (gameType == null) {
            gameType = dgg.a;
        }
        final boolean allowCommands = server.ag().v();
        final int port = server.S();
        final evg.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String e = server.aZ().e();
        final String levelId = ((LevelStorageAccessor)storageSource).getLevelId();
        return new LocalWorld(e, levelId, switch (gameType) {
            default -> throw new MatchException(null, null);
            case b -> GameMode.CREATIVE;
            case a -> GameMode.SURVIVAL;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        }, allowCommands, port, server.C().orElse(null));
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final fum previousScreen = flk.Q().z;
        final fun screen = new fun(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            flk.Q().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        flk.Q().a((fum)screen);
    }
}
