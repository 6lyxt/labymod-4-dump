// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.server;

import net.labymod.v1_17_1.client.gui.screen.CompletableShareToLanScreen;
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
        if (!dvp.C().F()) {
            return false;
        }
        final faq server = dvp.C().H();
        return server != null && server.o();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!dvp.C().F()) {
            return null;
        }
        final faq server = dvp.C().H();
        if (server == null) {
            return null;
        }
        bwn gameType = server.aY();
        if (gameType == null) {
            gameType = bwn.a;
        }
        final boolean allowCheats = server.ad().u();
        final int port = server.M();
        final dib.a storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aV().g();
        final String levelId = ((LevelStorageAccessor)storageSource).getLevelId();
        return new LocalWorld(g, levelId, switch (gameType) {
            default -> throw new MatchException(null, null);
            case b -> GameMode.CREATIVE;
            case a -> GameMode.SURVIVAL;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        }, allowCheats, port, server.A().orElse(null));
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final eaq previousScreen = dvp.C().y;
        final ear screen = new ear(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            dvp.C().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        dvp.C().a((eaq)screen);
    }
}
