// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.server;

import net.labymod.v1_20_2.client.gui.screen.CompletableShareToLanScreen;
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
        if (!eqv.O().R()) {
            return false;
        }
        final gdd server = eqv.O().T();
        return server != null && server.p();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!eqv.O().R()) {
            return null;
        }
        final gdd server = eqv.O().T();
        if (server == null) {
            return null;
        }
        cps gameType = server.aW();
        if (gameType == null) {
            gameType = cps.a;
        }
        final boolean allowCheats = server.ac().v();
        final int port = server.M();
        final ecg.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aT().g();
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
        final eyk previousScreen = eqv.O().y;
        final eyl screen = new eyl(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            eqv.O().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        eqv.O().a((eyk)screen);
    }
}
