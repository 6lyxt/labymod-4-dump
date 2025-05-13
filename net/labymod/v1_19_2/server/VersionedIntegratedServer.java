// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.server;

import net.labymod.v1_19_2.client.gui.screen.CompletableShareToLanScreen;
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
        if (!efu.I().L()) {
            return false;
        }
        final fnm server = efu.I().N();
        return server != null && server.o();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!efu.I().L()) {
            return null;
        }
        final fnm server = efu.I().N();
        if (server == null) {
            return null;
        }
        cgu gameType = server.aY();
        if (gameType == null) {
            gameType = cgu.a;
        }
        final boolean allowCheats = server.ac().v();
        final int port = server.L();
        final drq.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aW().g();
        final String levelId = ((LevelStorageAccessor)storageSource).getLevelId();
        return new LocalWorld(g, levelId, switch (gameType) {
            default -> throw new MatchException(null, null);
            case b -> GameMode.CREATIVE;
            case a -> GameMode.SURVIVAL;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        }, allowCheats, port, server.y().orElse(null));
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final elm previousScreen = efu.I().z;
        final eln screen = new eln(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            efu.I().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        efu.I().a((elm)screen);
    }
}
