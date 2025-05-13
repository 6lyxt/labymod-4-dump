// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.server;

import net.labymod.v1_19_3.client.gui.screen.CompletableShareToLanScreen;
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
        if (!ejf.N().Q()) {
            return false;
        }
        final fse server = ejf.N().S();
        return server != null && server.p();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!ejf.N().Q()) {
            return null;
        }
        final fse server = ejf.N().S();
        if (server == null) {
            return null;
        }
        cjt gameType = server.aY();
        if (gameType == null) {
            gameType = cjt.a;
        }
        final boolean allowCheats = server.ab().v();
        final int port = server.L();
        final dve.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aV().g();
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
        final epb previousScreen = ejf.N().z;
        final epc screen = new epc(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            ejf.N().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        ejf.N().a((epb)screen);
    }
}
