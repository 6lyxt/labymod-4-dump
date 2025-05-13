// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fzq>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, gez.class);
        this.register(NamedScreen.MULTIPLAYER, gci.class);
        this.register(NamedScreen.EDIT_SERVER, fyy.class);
        this.register(NamedScreen.MAIN_MENU, fzs.class);
        this.register(NamedScreen.CHAT_INPUT, fym.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, fzd.class);
        this.register(NamedScreen.INGAME_MENU, fzl.class);
        this.register(NamedScreen.INVENTORY, gbg.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, gav.class);
        this.register(NamedScreen.CONNECTING, fyp.class);
        this.register(NamedScreen.DISCONNECTED, fyx.class);
        this.register(NamedScreen.CREDITS, fys.class);
        this.register(NamedScreen.REALMS, fll.class);
        this.register(NamedScreen.CREATE_WORLD, ger.class);
        this.register(NamedScreen.LEVEL_LOADING, fze.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fzo.class);
        this.register(NamedScreen.PACK_CONFIRM, gll.b.class);
        this.register(NamedScreen.PROGRESS, fzn.class);
        this.register(NamedScreen.GENERIC_MESSAGE, fzb.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, fzr.class);
        this.register(NamedScreen.STATISTICS, fzu.class);
        this.register(NamedScreen.ADVANCEMENTS, gaa.class);
        this.register(NamedScreen.CONFIRM, fyo.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, gek.class);
        this.register(NamedScreen.EDIT_BOOK, gam.class);
        this.register(NamedScreen.OPTIONS, gcv.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, gcx.class);
        this.register(NamedScreen.VIDEO_SETTINGS, gda.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, gcs.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, gdi.class);
        this.register(NamedScreen.AUDIO_SETTINGS, gcy.class);
        this.register(NamedScreen.CONTROL_SETTINGS, gdb.class);
        this.register(NamedScreen.CHAT_SETTINGS, gcq.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, gcp.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, gdd.class);
        this.register(NamedScreen.MOUSE_SETTINGS, gct.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new gez((fzq)new fzs(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new gci((fzq)new fzs(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fzq prevScreen = fqq.Q().z;
            if (prevScreen instanceof fzb) {
                prevScreen = null;
            }
            return new gcv(prevScreen, fqq.Q().n);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new fym(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fzl(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fzq prevScreen2 = fqq.Q().z;
            final gmd data = new gmd(hly.a("selectServer.defaultName", new Object[0]), "", gmd.c.c);
            return new fyw(prevScreen2, join -> {
                if (join) {
                    fyp.a(prevScreen2, fqq.Q(), gng.a(data.b), data, false, (gmh)null);
                }
                else {
                    fqq.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fzq, fzq>)fys::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fzq, fzq>)fll::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new gcs(fqq.Q().z, fqq.Q().n, fqq.Q().ah()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new gcp(fqq.Q().z, fqq.Q().n));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fzq, fzq>)fzr::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fzq, fzq> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(fqq.Q().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof gaf;
    }
}
