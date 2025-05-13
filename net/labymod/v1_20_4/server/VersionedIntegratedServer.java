// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.server;

import net.labymod.v1_20_4.client.gui.screen.CompletableShareToLanScreen;
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
        if (!evi.O().R()) {
            return false;
        }
        final gir server = evi.O().T();
        return server != null && server.p();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!evi.O().R()) {
            return null;
        }
        final gir server = evi.O().T();
        if (server == null) {
            return null;
        }
        ctm gameType = server.bb();
        if (gameType == null) {
            gameType = ctm.a;
        }
        final boolean allowCheats = server.ae().v();
        final int port = server.O();
        final egm.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aY().g();
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
        final fdb previousScreen = evi.O().y;
        final fdc screen = new fdc(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            evi.O().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        evi.O().a((fdb)screen);
    }
}
