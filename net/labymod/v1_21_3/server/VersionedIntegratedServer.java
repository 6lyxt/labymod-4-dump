// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.server;

import net.labymod.v1_21_3.client.gui.screen.CompletableShareToLanScreen;
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
        if (!fmg.Q().T()) {
            return false;
        }
        final hfw server = fmg.Q().V();
        return server != null && server.r();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!fmg.Q().T()) {
            return null;
        }
        final hfw server = fmg.Q().V();
        if (server == null) {
            return null;
        }
        dhf gameType = server.bd();
        if (gameType == null) {
            gameType = dhf.a;
        }
        final boolean allowCommands = server.ag().v();
        final int port = server.S();
        final ewd.c storageSource = ((MinecraftServerAccessor)server).getStorageSource();
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
        final fty previousScreen = fmg.Q().z;
        final ftz screen = new ftz(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            fmg.Q().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        fmg.Q().a((fty)screen);
    }
}
