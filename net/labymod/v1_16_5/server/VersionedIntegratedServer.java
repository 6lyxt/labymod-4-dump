// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.server;

import net.labymod.v1_16_5.client.gui.screen.CompletableShareToLanScreen;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import net.labymod.api.client.entity.player.GameMode;
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
        if (!djz.C().F()) {
            return false;
        }
        final eng server = djz.C().H();
        return server != null && server.n();
    }
    
    @Nullable
    @Override
    public LocalWorld getLocalWorld() {
        if (!djz.C().F()) {
            return null;
        }
        final eng server = djz.C().H();
        if (server == null) {
            return null;
        }
        bru gameType = server.s();
        if (gameType == null) {
            gameType = bru.b;
        }
        final boolean allowCheats = server.ae().u();
        final int port = server.M();
        GameMode gameMode = GameMode.UNKNOWN;
        switch (gameType) {
            case c: {
                gameMode = GameMode.CREATIVE;
                break;
            }
            case b: {
                gameMode = GameMode.SURVIVAL;
                break;
            }
            case d: {
                gameMode = GameMode.ADVENTURE;
                break;
            }
            case e: {
                gameMode = GameMode.SPECTATOR;
                break;
            }
        }
        final File screenshotFile = server.A();
        return new LocalWorld(server.aX().g(), screenshotFile.getParentFile().getName(), gameMode, allowCheats, port, screenshotFile.toPath());
    }
    
    @Override
    public void requestLanWorld(final Consumer<LocalWorld> consumer) {
        final LocalWorld opened = this.getLocalWorld();
        if (opened != null && opened.isOpen()) {
            consumer.accept(opened);
            return;
        }
        final dot previousScreen = djz.C().y;
        final dou screen = new dou(previousScreen);
        ((CompletableShareToLanScreen)screen).setFinishHandler(success -> {
            djz.C().a(previousScreen);
            consumer.accept(((boolean)success) ? this.getLocalWorld() : null);
            return;
        });
        djz.C().a((dot)screen);
    }
}
