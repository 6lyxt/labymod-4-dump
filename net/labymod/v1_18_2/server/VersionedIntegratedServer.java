// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.server;

import net.labymod.v1_18_2.client.gui.screen.CompletableShareToLanScreen;
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
        if (!dyr.D().G()) {
            return false;
        }
        final fec server = dyr.D().I();
        return server != null && server.o();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!dyr.D().G()) {
            return null;
        }
        final fec server = dyr.D().I();
        if (server == null) {
            return null;
        }
        cas gameType = server.aW();
        if (gameType == null) {
            gameType = cas.a;
        }
        final boolean allowCheats = server.ac().v();
        final int port = server.M();
        final dkp.a storageSource = ((MinecraftServerAccessor)server).getStorageSource();
        final String g = server.aT().g();
        final String levelId = ((LevelStorageAccessor)storageSource).getLevelId();
        return new LocalWorld(g, levelId, switch (gameType) {
            default -> throw new MatchException(null, null);
            case b -> GameMode.CREATIVE;
            case a -> GameMode.SURVIVAL;
            case c -> GameMode.ADVENTURE;
            case d -> GameMode.SPECTATOR;
        }, allowCheats, port, server.z().orElse(null));
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final edw previousScreen = dyr.D().y;
        final edx screen = new edx(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            dyr.D().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        dyr.D().a((edw)screen);
    }
}
