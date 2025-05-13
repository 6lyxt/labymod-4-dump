// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.server;

import net.labymod.v1_20_1.client.gui.screen.CompletableShareToLanScreen;
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
        if (!enn.N().Q()) {
            return false;
        }
        final fyp server = enn.N().S();
        return server != null && server.p();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!enn.N().Q()) {
            return null;
        }
        final fyp server = enn.N().S();
        if (server == null) {
            return null;
        }
        cmj gameType = server.aX();
        if (gameType == null) {
            gameType = cmj.a;
        }
        final boolean allowCheats = server.ac().v();
        final int port = server.M();
        final dyy.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aU().g();
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
        final euq previousScreen = enn.N().z;
        final eur screen = new eur(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            enn.N().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        enn.N().a((euq)screen);
    }
}
